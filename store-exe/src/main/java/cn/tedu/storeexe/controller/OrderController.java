package cn.tedu.storeexe.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.storeexe.entity.Order;
import cn.tedu.storeexe.service.IOrderService;
import cn.tedu.storeexe.util.JsonResult;

@RestController
@RequestMapping("order")
public class OrderController extends BaseController {
	
	@Autowired
	private IOrderService orderService;
	
	@RequestMapping("create")
	public JsonResult<Order> create(Integer aid, Integer[] cids, HttpSession session){
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		Order data = orderService.create(aid, cids, uid, username);
		return new JsonResult<Order>(SUCCESS, data);
		
	}
	
}












