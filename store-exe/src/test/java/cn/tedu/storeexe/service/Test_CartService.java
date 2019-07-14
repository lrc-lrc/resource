package cn.tedu.storeexe.service;


import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.storeexe.service.ex.AccessDeniedException;
import cn.tedu.storeexe.service.ex.CartNotFoundException;
import cn.tedu.storeexe.service.ex.ServiceException;
import cn.tedu.storeexe.service.ex.UpdateException;
import cn.tedu.storeexe.vo.CartVO;


@RunWith(SpringRunner.class)
@SpringBootTest
public class Test_CartService {

	@Autowired
	private ICartService  iCartService;

	@Test
	public void addToCart() {
		try {
			Integer uid = 1;
			Integer pid = 10000017;
			Integer num = 120;
			String username = "root";
			iCartService.addToCart(uid, pid, num, username);
			System.err.println("ok.");
		} catch (Exception e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	/** 2. 获取购物车列表 */
	@Test
	public void getByUid() {
		try {
			Integer uid = 1;
			List<CartVO> list = iCartService.getByUid(uid);
			for(CartVO item : list) {
				System.err.println(item);
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}

	/**
	 * 	3. 将购物车中的商品的数量增加1
	 * @param cid 购物车数据id
	 * @param uid 用户id
	 * @param username 用户名
	 * @throws CartNotFoundException 购物车数据不存在
	 * @throws AccessDeniedException 访问的是他人的数据
	 * @throws UpdateException 更新数据异常
	 */
	@Test
	public void addNum() {
		try {
			Integer cid = 1;
			Integer uid = 1;
			String username = "土豪";
			iCartService.addNum(cid, uid, username);
			System.err.println("ok.");
		} catch (Exception e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
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
	@Test
	public void reduceNum() {
		try {
			Integer cid = 1;
			Integer uid = 1;
			String username = "超级管理员";
			iCartService.reduceNum(cid, uid, username);
			System.err.println("ok.");
		} catch (Exception e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	/** 5. 删除购物车数据*/
	@Test
	public void deleteCart() {
		try {
			Integer cid = 2;
			Integer uid = 1;
			iCartService.deleteCart(cid, uid);
			System.err.println("ok.");
		} catch (Exception e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	/** 6. 获取购物车数据若干个id值匹配购物车数据的详情 */
	@Test
	public void getByCids(){		
		try {
			Integer[] cids = {5, 6, 7};
			Integer uid = 1;
			List<CartVO> list = iCartService.getByCids(cids, uid);
			for(CartVO item : list) {
				System.err.println(item);
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	/**7. 删除多条购物车数据 */
	@Test
	public void deleteByCids() {
	    try {
	        Integer[] cids = {13,15,23,25,27,29,31};
	        Integer uid = 1;
	        iCartService.delete(cids, uid);
	        System.err.println("OK.");
	    } catch (ServiceException e) {
	        System.err.println(e.getClass().getName());
	        System.err.println(e.getMessage());
	    }
	}
}





















