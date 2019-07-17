package cn.tedu.storeexe.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.storeexe.entity.Order;
import cn.tedu.storeexe.service.ex.ServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test_OrderService {
	
	@Autowired
	IOrderService service;
	
	@Test
	public void create() {
		try {
			Integer aid = 7;
			Integer[] cids = { 5,6,7,8 };
			Integer uid = 1;
			String username = "购物狂";
			Order result = service.create(aid, cids, uid, username);
			System.err.println(result);
		} catch (Exception e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	/**2. 修改订单状态*/
	@Test
	public void changeStatus() {
		try {
			Integer oid = 1;
			Integer status = 0;
			String username = "超级管理员";
			service.changeStatus(oid, status, username);
			System.err.println("ok.");
		} catch(ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
		
	}
	
}
