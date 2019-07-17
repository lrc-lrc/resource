package cn.tedu.storeexe.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

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
	
	/**
	 * 	3. 修改订单状态
	 * @param oid 订单id
	 * @param status 状态值
	 * @param username 修改执行人
	 * @param modifiedTime 修改时间
	 * @return 受影响的行数
	 */
	Integer updateStatus(@Param("oid")Integer oid, 
						 @Param("status")Integer status, 
						 @Param("username")String username, 
						 @Param("modifiedTime")Date modifiedTime);
	
	/** 4. 根据oid查询订单信息数据 */
	Order findByOid(Integer oid);
	
}












