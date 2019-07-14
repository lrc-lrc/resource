package cn.tedu.storeexe.mapper;

import cn.tedu.storeexe.entity.Order;
import cn.tedu.storeexe.entity.OrderItem;

/**
 * 	订单的持久层接口
 * @author Administrator
 *
 */
public interface OrderMapper {
	
	/** 1. 插入订单数据 */
	Integer insertOrder(Order order);
	
	/** 2. 插入订单商品数据 */
	Integer insertOrderItem(OrderItem orderItem);
	
}












