package cn.tedu.storeexe.service;

import java.util.List;

import cn.tedu.storeexe.entity.Product;

/**
 * 处理商品数据的业务层接口
 */
public interface IProductService {
	
	/**
	 * 1. 获取热销的前4名的商品列表
	 */
	List<Product> getHotList();
	
	/**
	 * 2. 获取商品详情
	 */
	Product getById(Integer id);
	
	/** 3. 减少数量 */
	void reduceNum(Integer pid, Integer amount);
	
	/** 4. 增加数量 */
	void addNum(Integer pid, Integer amount);
	
}












