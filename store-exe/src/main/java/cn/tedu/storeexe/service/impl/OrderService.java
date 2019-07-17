package cn.tedu.storeexe.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.storeexe.entity.Address;
import cn.tedu.storeexe.entity.Order;
import cn.tedu.storeexe.entity.OrderItem;
import cn.tedu.storeexe.mapper.OrderMapper;
import cn.tedu.storeexe.service.IAddressService;
import cn.tedu.storeexe.service.ICartService;
import cn.tedu.storeexe.service.IOrderService;
import cn.tedu.storeexe.service.IProductService;
import cn.tedu.storeexe.service.ex.InsertException;
import cn.tedu.storeexe.service.ex.OrderNotFoundException;
import cn.tedu.storeexe.service.ex.UpdateException;
import cn.tedu.storeexe.vo.CartVO;

@Service
public class OrderService implements IOrderService {

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private ICartService cartService;
	@Autowired
	private IAddressService addressService;
	@Autowired 
	private IProductService productService;


	/**1. 生成订单*/
	@Transactional
	public Order create(Integer aid, Integer[] cids, Integer uid, String username) {
		// 创建当前时间对象
		Date now = new Date();

		// 根据参数cids，通过cartService的getByCids()查询购物车数据，得到List<CartVO>类型的对象
		List<CartVO> carts  = cartService.getByCids(cids, uid);
		System.out.println(carts);
		// 遍历以上购物车数据集合对象以计算总价
		Long totalPrice = 0L;
		for(CartVO cart : carts)  {
			totalPrice += cart.getPrice() * cart.getNum();
		}

		// 创建Order对象
		Order order = new Order();
		// 补Order对象属性：uid > 参数uid
		order.setUid(uid);
		// 根据参数aid，通过addressService的getByAid()方法查询收货地址详情
		Address address = addressService.getByAid(aid);
		// 补Order对象属性：recv_*
		order.setRecvProvince(address.getProvinceName());
		order.setRecvCity(address.getCityName());
		order.setRecvArea(address.getAreaName());
		order.setRecvAddress(address.getAddress());
		// 补Order对象属性：total_price > 以上遍历时的计算结果
		order.setTotalPrice(totalPrice);
		// 补Order对象属性：status > 0
		order.setStatus(0);
		// 补Order对象属性：order_time > 当前时间
		order.setOrderTime(now);
		// 补Order对象属性：pay_time > null
		// 补Order对象属性：日志 > 参数username，当前时间
		order.setCreatedTime(now);
		order.setCreatedUser(username);
		order.setModifiedTime(now);
		order.setModifiedUser(username);
		// 插入订单数据：insertOrder(order)
		insertOrder(order);

		// 准备订单商品数据的集合，后续可用于归还商品库存
		List<OrderItem> orderItems = new ArrayList<>();
		// 遍历购物车数据集合对象
		for(CartVO cart : carts)  {
			// -- 创建OrderItem对象
			OrderItem item = new OrderItem();
			// -- 补OrderItem对象属性：oid > order.getOid();
			item.setOid(order.getOid());
			// -- 补OrderItem对象属性：pid, title, image, price, num > 遍历对象中的pid, title, iamge ,realPrice, num
			item.setPid(cart.getPid());
			item.setTitle(cart.getTitle());
			item.setImage(cart.getImage());
			item.setPrice(cart.getRealPrice());
			item.setNum(cart.getNum());
			// -- 补OrderItem对象属性：日志 > 参数username，当前时间
			item.setCreatedUser(username);
			item.setCreatedTime(now);
			item.setModifiedUser(username);
			item.setModifiedTime(now);
			// -- 插入订单商品数据：insertOrderItem(orderItem)
			insertOrderItem(item);
			// 销库存
			productService.reduceNum(cart.getPid(), cart.getNum());
		}
		// 删除购物车中对应的数据：cartService.deleteBy...
		cartService.delete(cids, uid);

		// 开启倒计时任务(Timer/Thread)，如果用户在规定时间内未支付，则关闭订单，并归还库存
		new Thread() {
			public void run() {
				System.err.println("OrderService：计划15分钟后检查订单状态，准备关闭订单");
				try {
					Thread.sleep(15 * 60 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.err.println("OrderService：准备关闭订单……");
				close(order.getOid(), orderItems, username);
			}
		}.start();
		return order;
	}

	/**2. 修改订单状态*/
	public void changeStatus(Integer oid, Integer status, String username) {
		// 根据参数oid查询订单状态
		Order result = findByOid(oid);
		// 判断查询结果是否不存在：OrderNotFoundException
		if(result == null) {
			throw new OrderNotFoundException("修改订单状态失败！尝试访问的数据不存在！");
		}

		// 执行修改订单状态
		updateStatus(oid, status, username, new Date());
	}

	/**3. 关闭订单 */
	public void close(Integer oid, List<OrderItem> orderItems, String username) {
		// 检查订单状态是否是“未支付”
		Order result = findByOid(oid);
		// 检查查询结果是否不存在
		if (result == null) {
			throw new OrderNotFoundException(
					"关闭订单失败！尝试访问的数据不存在！");
		}

		// 检查订单当前状态
		if (result.getStatus() != Status.UNPAID) {
			return;
		}

		// 将订单状态修改为已关闭
		changeStatus(oid, Status.CLOSED, username);

		// 归还订单中所有商品的库存
		for (OrderItem orderItem : orderItems) {
			productService.addNum(orderItem.getPid(), orderItem.getNum());
		}
		System.err.println("OrderService：订单已关闭！");
	}

	/**  插入订单数据--1 */
	private void insertOrder(Order order) {
		Integer rows = orderMapper.insertOrder(order);
		if(rows != 1) {
			throw new InsertException("创建订单失败！插入订单数据时出现未知错误！");
		}
	}

	/** 插入订单商品数据--1 */
	private void insertOrderItem(OrderItem orderItem) {
		Integer rows = orderMapper.insertOrderItem(orderItem);
		if(rows != 1) {
			throw new InsertException("创建订单失败！插入订单数据时出现未知错误！");
		}
	}

	/**
	 * 修改订单状态--2
	 * @param oid 订单id
	 * @param status 状态值
	 * @param username 修改执行人
	 * @param modifiedTime 修改时间
	 * @return 受影响的行数
	 */
	private void updateStatus(Integer oid, Integer status, String username, Date modifiedTime) {
		Integer rows = orderMapper.updateStatus(oid, status, username, modifiedTime);
		if(rows != 1) {
			throw new UpdateException("修改订单状态失败！更新数据时出现未知错误！");
		}
	}

	/** 根据oid查询订单信息数据--2 */
	private Order findByOid(Integer oid) {
		Order order = orderMapper.findByOid(oid);
		return order;
	}




}
