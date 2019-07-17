package cn.tedu.storeexe.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.storeexe.entity.Product;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test_ProductMapper {

	@Autowired
	ProductMapper mapper;
	
	/**
	 * 1. 查询热销的前4名的商品列表
	 */
	@Test
	public void findHotList() {
		List<Product> list = mapper.findHotList();
		System.err.println("count=" + list.size());
		for (Product item : list) {
			System.err.println(item);
		}
	}
	
	/**
	 * 2. 查询商品详情
	 */
	@Test
	public void findById() {
		Integer id = 10000001;
		Product product = mapper.findById(id);
		System.err.println(product);
	}
	
	@Test
	public void updateNum() {
		Integer pid = 10000022;
		Integer num = 100;
		Integer rows = mapper.updateNum(pid, num);
		System.err.println("rows=" + rows);
	}
	
}









