package cn.tedu.storeexe.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.storeexe.entity.Cart;
import cn.tedu.storeexe.mapper.CartMapper;
import cn.tedu.storeexe.service.ICartService;
import cn.tedu.storeexe.service.IProductService;
import cn.tedu.storeexe.service.ex.AccessDeniedException;
import cn.tedu.storeexe.service.ex.CartNotFoundException;
import cn.tedu.storeexe.service.ex.DeleteException;
import cn.tedu.storeexe.service.ex.InsertException;
import cn.tedu.storeexe.service.ex.UpdateException;
import cn.tedu.storeexe.vo.CartVO;

@Service
public class CartServiceImpl implements ICartService {

	@Autowired
	private CartMapper cartMapper;
	@Autowired
	private IProductService productService;

	/** 1. 将商品添加到购物车 */
	@Override
	public void addToCart(Integer uid, Integer pid, Integer num, String username) {
		// 基于参数uid和pid查询数据
		Cart result = findByUidAndPid(uid, pid);
		Date now = new Date();
		// 判断查询结果是否为null
		if(result == null) {
			// 是：需要新增购物车数据
			//自行创建Cart对象
			Cart cart = new Cart();
			//	调用productService的getById()方法获取单价并封装到Cart对象
			Long price = productService.getById(pid).getPrice();
			cart.setPrice(price);
			//	将uid、pid、num参数封装到Cart对象
			cart.setUid(uid);
			cart.setPid(pid);
			cart.setNum(num);
			//	创建当前时间对象，将时间和username封装到Cart对象的日志属性
			cart.setCreatedUser(username);
			cart.setCreatedTime(now);
			cart.setModifiedUser(username);	
			cart.setModifiedTime(now);

			//	执行插入
			Insert(cart);

			return;
		} 

		// 否：需要修改欲购物的商品的数量
		//	从查询结果中获取当前数量num和数据的cid
		Integer oldNum = result.getNum();
		Integer cid = result.getCid();
		//	将以上查询结果中的当前数量num和参数增量num相加，得到新的数量
		num = num + oldNum;
		//	执行更新数量
		updateNum(cid, num, username, now);
	}

	/** 2. 获取购物车列表 */
	public List<CartVO> getByUid(Integer uid) {
		return findByUid(uid);
	}

	/** 3. 根据cid查询购物车数据 */
	public void addNum(Integer cid, Integer uid, String username) {
		// 根据参数cid查询数据
		Cart result = findByCid(cid);
		// 判断查询结果是否为null：CartNotFoundException
		if(result == null) {
			throw new CartNotFoundException("增加失败！尝试访问的购物车数据不存在！");
		}

		// 判断查询结果中的uid与参数uid是否不同：AccessDeniedException
		if(!result.getUid().equals(uid)) {
			throw new AccessDeniedException("增加失败！不允许操作他人的数据！");
		}

		// 从查询结果中获取尝试购买的原数量
		Integer oldNum = result.getNum();
		// 将数量更新为原数量+1的结果
		updateNum(cid, oldNum+1, username, new Date());
	}

	/**
	 * 	4. 将购物车中的商品的数量减少1
	 * @param cid 购物车数据id
	 * @param uid 用户id
	 * @param username 用户名
	 * @throws CartNotFoundException 购物车数据不存在
	 * @throws AccessDeniedException 访问的是他人的数据
	 * @throws UpdateException 更新数据异常
	 */
	public void reduceNum(Integer cid, Integer uid, String username)
			throws CartNotFoundException, AccessDeniedException, UpdateException {
		// 根据参数cid查询数据
		Cart result = findByCid(cid);
		// 判断查询结果是否为null：CartNotFoundException
		if(result == null) {
			throw new CartNotFoundException("减少失败！尝试访问的购物车数据不存在！");
		}

		// 判断查询结果中的uid与参数uid是否不同：AccessDeniedException
		if(result.getUid() != uid) {
			throw new AccessDeniedException("减少失败！不允许操作他人的数据！");
		}

		// 从查询结果中获取尝试购买的原数量
		Integer num = result.getNum();
		if(num == 1) {
			// 规则1：不再继续减少
			//return;

			// 规则2：进行删除
			deleteByCid(cid);
			return;
		}

		// 将数量更新为原数量-1的结果
		updateNum(cid, num-1, username, new Date());
	}

	/** 5. 删除购物车数据*/
	public void deleteCart(Integer cid, Integer uid) {
		// 根据参数cid查询数据
		Cart result = findByCid(cid);
		// 判断查询结果是否为null：CartNotFoundException
		if(result == null) {
			throw new CartNotFoundException("删除失败！尝试访问的购物车数据不存在！");
		}

		// 判断查询结果中的uid与参数uid是否不同：AccessDeniedException
		if(result.getUid() != uid) {
			throw new AccessDeniedException("删除失败！不允许操作他人的数据！");
		}

		// 将购物车中的数据删除
		deleteByCid(cid);
	}

	/** 6. 获取购物车数据若干个id值匹配购物车数据的详情 */
	public List<CartVO> getByCids(Integer[] cids, Integer uid){
		// 检查参数cids
		// 判断查询结果是否为null：CartNotFoundException
		if(cids == null) {
			return new ArrayList<>();
		}

		// 执行查询
		List<CartVO> result = findByCids(cids);

		Iterator<CartVO> it = result.iterator();
		while(it.hasNext()) {
			CartVO item = it.next();
			if(item.getUid() != uid) {
				it.remove();
			}
		}

		return result;
	}
	
	/**7. 删除多条购物车数据 */
	public void delete(Integer[] cids, Integer uid) {
		// 判断即将删除的数据是否存在，及数据归属是否正确
		// 可以调用自身的：List<CartVO> getByCids(Integer[] cids, Integer uid)，得到cid有效，且归属正确的购物车数据
		List<CartVO> carts = getByCids(cids, uid);

		// 判断以上查询结果的长度是否有效
		if (carts.size() == 0) {
			throw new CartNotFoundException(
					"删除购物车数据失败！尝试访问的数据不存在！");
		}

		// 基于以上得到的List<CartVO>得到允许执行删除的cid的数组
		Integer[] ids = new Integer[carts.size()];
		for (int i = 0; i < carts.size(); i++) {
			ids[i] = carts.get(i).getCid();
		}

		// 执行删除
		deleteByCids(ids);
	}

	/**
	 *	根据若干个购物车数据id删除数据
	 * @param cids 若干个购物车数据id
	 * @throws DeleteException 删除数据异常
	 */
	private void deleteByCids(Integer[] cids) {
		Integer rows = cartMapper.deleteByCids(cids);
		if (rows < 1) {
			throw new DeleteException(
					"清除购物车数据失败！删除数据时发生未知错误！");
		}
	}

	/** 插入购物车数据--1 */
	private void  Insert(Cart cart) {
		Integer rows = cartMapper.Insert(cart);
		if(rows != 1) {
			throw new InsertException("添加商品到购物车数据失败！发生未知错误！");
		}
	}

	/** 更新数量--1 */
	private void updateNum(Integer cid,Integer num,
			String username,Date modifiedTime) {
		Integer rows = cartMapper.updateNum(cid, num, username, modifiedTime);
		if(rows != 1) {
			throw new UpdateException("更新数据失败！发生未知错误！");
		}
	}

	/** 根据uid和pid查询购物车数据--1 */
	private Cart findByUidAndPid(Integer uid, Integer pid) {
		return cartMapper.findByUidAndPid(uid, pid);
	}

	/** 根据用户uid查询该用户的购物车列表--2 */
	private List<CartVO> findByUid(Integer uid){
		return cartMapper.findByUid(uid);
	}

	/** 根据cid查询购物车数据--3 */
	private Cart findByCid(Integer cid) {
		return cartMapper.findByCid(cid);
	}

	/** 根据cid删除购物车数据--5 */
	private void deleteByCid(Integer cid) {
		Integer rows = cartMapper.deleteByCid(cid);
		if(rows != 1) {
			throw new DeleteException("删除购物车中商品数据失败！发生未知错误！");
		}

	}

	/** 根据购物车数据若干个id值匹配购物车数据的详情--6 */
	private List<CartVO> findByCids(Integer[] cids){
		return cartMapper.findByCids(cids);
	}





}
