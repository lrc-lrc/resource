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
	
	/** 3. 修改订单状态 */
	@Test
	public void updateStatus() {
		Integer oid = 1;
		Integer status = 0;
		String username = "超级管理员";
		Date modifiedTime = new Date();
		Integer rows = orderMapper.updateStatus(oid, status, username, modifiedTime);      
		System.err.println("rows=" + rows);
	}
	
	/** 4. 根据oid查询订单信息数据 */
	@Test
	public void findByOid() {
		Integer oid = 2;
		Order order = orderMapper.findByOid(oid);
		System.err.println(order);
	}
	
}





















