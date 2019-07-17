package cn.tedu.storeexe.mapper;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.storeexe.entity.Cart;
import cn.tedu.storeexe.vo.CartVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test_CartMappr {
	
	@Autowired
	private CartMapper cartMapper;
	
	/** 1. 插入购物车数据 */
	@Test
	public void Insert() {
		Cart cart = new Cart();
		cart.setUid(1);
		cart.setPid(10000001);
		cart.setNum(1);
		cart.setPrice(10000L);
		Integer rows = cartMapper.Insert(cart);
		System.err.println("rows=" + rows);
	}
	
	/** 2. 更新数量 */
	@Test
	public void updateNum() {
		Integer cid = 1;
		Integer num = 2;
		String username = "超级管理员";
		Date modifiedTime = new Date();
		Integer rows = cartMapper.updateNum(cid, num, username, modifiedTime);
		System.err.println("rows=" + rows);
	}
	
	/** 3.根据uid和pid查询购物车数据 */
	@Test
	public void findByUidAndPid(){
		Integer uid = 1;
		Integer pid = 10000001;
		Cart cart = cartMapper.findByUidAndPid(uid, pid);
		System.err.println(cart);
	}
	
	/**
	 * 4. 根据用户uid查询该用户的购物车列表
	 * @param uid
	 * @return
	 */
	@Test
	public void findByUid() {
		Integer uid = 1;
		List<CartVO> list = cartMapper.findByUid(uid);
		for(CartVO item : list) {
			System.err.println(item);
		}
		
	}
	
	/** 5. 根据cid查询购物车数据 */
	@Test
	public void findByCid() {
		Integer cid = 1;
		Cart result = cartMapper.findByCid(cid);
		System.err.println(result);
	}
	
	/** 6. 根据cid删除购物车数据*/
	@Test
	public void deleteByCid() {
		Integer cid = 1;
		Integer rows = cartMapper.deleteByCid(cid);
		System.err.println("rows=" + rows);
	}
	
	@Test
	public void findByCids() {
		Integer[] cids = {5, 6, 7, 8};
		List<CartVO> list = cartMapper.findByCids(cids);
		for(CartVO item : list) {
			System.err.println(item);
		}
		
	}
	
	@Test
	public void deleteByCids() {
		Integer[] cids = {5,6};
		Integer rows = cartMapper.deleteByCids(cids);
		System.err.println("rows=" + rows);
	}
	
}















