package cn.tedu.storeexe.service;

import cn.tedu.storeexe.entity.Order;

/**
 * 	订单的业务层
 * @author Administrator
 *
 */
public interface IOrderService {
	
	/**1. 生成订单*/
	Order create(Integer aid, Integer[] cids, Integer uid, String username);
}












