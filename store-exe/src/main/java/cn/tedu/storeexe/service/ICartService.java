package cn.tedu.storeexe.service;

import java.util.List;

import cn.tedu.storeexe.service.ex.AccessDeniedException;
import cn.tedu.storeexe.service.ex.CartNotFoundException;
import cn.tedu.storeexe.service.ex.UpdateException;
import cn.tedu.storeexe.vo.CartVO;

/**
 * 	购物车的业务层接口
 * @author Administrator
 *
 */
public interface ICartService {
	
	/** 1. 将商品添加到购物车 */
	void addToCart(Integer uid, Integer pid, Integer num, String username);
	
	/** 2. 获取购物车列表 */
	List<CartVO> getByUid(Integer uid);
	
	/**
	 * 	3. 将购物车中的商品的数量增加1
	 * @param cid 购物车数据id
	 * @param uid 用户id
	 * @param username 用户名
	 * @throws CartNotFoundException 购物车数据不存在
	 * @throws AccessDeniedException 访问的是他人的数据
	 * @throws UpdateException 更新数据异常
	 */
	void addNum(Integer cid, Integer uid, String username) throws CartNotFoundException, AccessDeniedException, UpdateException;
	

	/**
	 * 	4. 将购物车中的商品的数量减少1
	 * @param cid 购物车数据id
	 * @param uid 用户id
	 * @param username 用户名
	 * @throws CartNotFoundException 购物车数据不存在
	 * @throws AccessDeniedException 访问的是他人的数据
	 * @throws UpdateException 更新数据异常
	 */
	void reduceNum(Integer cid, Integer uid, String username) throws CartNotFoundException, AccessDeniedException, UpdateException;
	
	/** 5. 删除购物车数据 */
	void deleteCart(Integer cid, Integer uid);
	
	/** 6. 获取购物车数据若干个id值匹配购物车数据的详情 */
	List<CartVO> getByCids(Integer[] cids, Integer uid);
	
	/** 7.删除多条购物车数据 */
	void delete(Integer[] cids, Integer uid);
}











