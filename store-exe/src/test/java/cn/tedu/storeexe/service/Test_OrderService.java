package cn.tedu.storeexe.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.storeexe.entity.Order;

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
	
}
