package cn.tedu.storeexe.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.storeexe.entity.Product;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test_ProductService {

	@Autowired
	private IProductService service;

	/**
	 * 1. 获取热销的前4名的商品列表
	 */
	@Test
	public void getHotList() {
		List<Product> list = service.getHotList();
		System.err.println("count=" + list.size());
		for (Product item : list) {
			System.err.println(item);
		}
	}

	/**
	 * 2. 获取商品详情
	 */
	@Test
	public void getById() {
		try {
			Integer id = 10000001;
			Product product = service.getById(id);
			System.err.println(product);
		} catch(Exception e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void reduceNum() {
		try {
			Integer pid = 10000022;
			Integer amount = 80;
			service.reduceNum(pid, amount);
			System.err.println("OK");
		} catch (Exception e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
}










