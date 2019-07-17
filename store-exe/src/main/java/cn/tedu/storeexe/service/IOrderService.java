package cn.tedu.storeexe.service;

import java.util.List;

import cn.tedu.storeexe.entity.Order;
import cn.tedu.storeexe.entity.OrderItem;

/**
 * 	订单的业务层
 * @author Administrator
 *
 */
public interface IOrderService {
	
	public static interface Status{
		int UNPAID = 0; // 未支付
		int PAID = 1; // 已支付
		int CANCELED = 2; // 已取消
		int CLOSED = 3; // 已关闭
	}
	
	/**1. 生成订单*/
	Order create(Integer aid, Integer[] cids, Integer uid, String username);
	
	/**2. 修改订单状态*/
	void changeStatus(Integer oid, Integer status, String username);
	
	/**
	 * 3. 关闭订单
	 * @param oid 需要关闭的订单id
	 * @param orderItems 需要关闭的订单中的商品列表
	 * @param username 修改执行人
	 */
	void close(Integer oid, List<OrderItem> orderItems, String username);

}












