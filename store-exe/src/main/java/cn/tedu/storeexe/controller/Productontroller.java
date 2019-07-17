package cn.tedu.storeexe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.storeexe.entity.Product;
import cn.tedu.storeexe.service.IProductService;
import cn.tedu.storeexe.util.JsonResult;

@RestController
@RequestMapping("product")
public class Productontroller extends BaseController {

	@Autowired
	private IProductService productService;

	@GetMapping("hot")
	public JsonResult<List<Product>> getHotList() {
		// 调用业务层对象执行获取热销列表
		List<Product> data = productService.getHotList();
		// 返回成功和查询结果
		return new JsonResult<List<Product>>(SUCCESS, data);
	}

	/**
	 * 2. 处理获取商品详情
	 */
	@GetMapping("{id}/details")
	public JsonResult<Product> handleGetById(@PathVariable("id")Integer id) {
		// 调用业务层对象执行查询
		Product data = productService.getById(id);
		// 返回成功和查询结果
		return new JsonResult<Product>(SUCCESS, data);
	}

}
