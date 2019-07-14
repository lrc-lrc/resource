package cn.tedu.storeexe.mapper;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.storeexe.entity.Order;
import cn.tedu.storeexe.entity.OrderItem;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test_OrderMapper {
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Test
	/** 1. 插入订单数据 */
	public void insertOrder(){
		Order order = new Order();
		order.setUid(1);
		order.setOrderTime(new Date());
		Integer rows = orderMapper.insertOrder(order);
		System.err.println(rows);
		System.err.println("oid="+order.getOid());
	}
	
	@Test
	/** 2. 插入订单商品数据 */
	public void insertOrderItem() {
		OrderItem orderItem = new OrderItem();
		orderItem.setOid(1);
		orderItem.setPid(1011212);
		Integer rows = orderMapper.insertOrderItem(orderItem);
		System.err.println(rows);
		System.err.println("id="+orderItem.getId());
	}
	
}





















