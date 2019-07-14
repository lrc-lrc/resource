package cn.tedu.storeexe.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.storeexe.service.ICartService;
import cn.tedu.storeexe.util.JsonResult;
import cn.tedu.storeexe.vo.CartVO;

/**
 * 	处理购物车的相关请求的控制层
 */
@RestController
@RequestMapping("cart")
public class CartController extends BaseController {

	@Autowired
	private ICartService iCartService;

	/** 1. 处理将商品添加到购物车请求 */
	@RequestMapping("add_to_cart")
	public JsonResult<Void> handleAddToCart(Integer pid, Integer num, HttpSession session){
		// 从session中获取uid和username
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		// 调用业务层对象的方法执行任务
		iCartService.addToCart(uid, pid, num, username);
		// 响应成功
		return new JsonResult<Void>(SUCCESS);	
	}

	/** 2. 处理获取获取购物车列表请求 */
	@RequestMapping("/")
	public JsonResult<List<CartVO>> handleAddToCart(HttpSession session){
		// 从session中获取uid和username
		Integer uid = getUidFromSession(session);
		// 调用业务层对象的方法执行任务
		List<CartVO> data = iCartService.getByUid(uid);
		// 响应成功
		return new JsonResult<List<CartVO>>(SUCCESS, data);
	}

	/**
	 * 	3. 将购物车中的商品的数量增加1
	 */
	@RequestMapping("{cid}/add_num")
	public JsonResult<Void> handleAddNum(@PathVariable("cid")Integer cid, HttpSession session){
		// 从session中获取uid和username
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		// 调用业务层对象的方法执行任务
		iCartService.addNum(cid, uid, username);
		// 响应成功
		return new JsonResult<Void>(SUCCESS);
	}

	/**
	 * 	4. 将购物车中的商品的数量减少1
	 */
	@RequestMapping("{cid}/reduce_num")
	public JsonResult<Void> handleReduceNum(@PathVariable("cid")Integer cid, HttpSession session){
		// 从session中获取uid和username
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		// 调用业务层对象的方法执行任务
		iCartService.reduceNum(cid, uid, username);
		// 响应成功
		return new JsonResult<Void>(SUCCESS);
	}

	/** 5. 处理删除购物车数据的请求*/
	@RequestMapping("{cid}/delete_cart")
	public JsonResult<Void> deleteCart(@PathVariable("cid")Integer cid, HttpSession session) {
		// 从session中获取uid和username
		Integer uid = getUidFromSession(session);
		// 调用业务层对象的方法执行任务
		iCartService.deleteCart(cid, uid);
		// 响应成功
		return new JsonResult<Void>(SUCCESS);
	}

	/** 6. 获取购物车数据若干个id值匹配购物车数据的详情 */
	@GetMapping("get_by_cids")
	public JsonResult<List<CartVO>> getByCids(Integer[] cids, HttpSession session){
		// 从session中获取uid和username
		Integer uid = getUidFromSession(session);
		// 调用业务层对象的方法执行任务
		List<CartVO> data = iCartService.getByCids(cids, uid);
		// 响应成功
		return new JsonResult<>(SUCCESS, data);
	}
}












