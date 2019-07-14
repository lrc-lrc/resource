package cn.tedu.storeexe.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.storeexe.entity.Product;
import cn.tedu.storeexe.mapper.ProductMapper;
import cn.tedu.storeexe.service.IProductService;
import cn.tedu.storeexe.service.ex.ProductNotFoundException;
import cn.tedu.storeexe.service.ex.ProductOutOfStockException;
import cn.tedu.storeexe.service.ex.UpdateException;

/**
 * 处理商品数据的业务逻辑实现类
 * @author Administrator
 *
 */
@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
	private ProductMapper productMapper;

	/**
	 * 1. 获取热销的前4名的商品列表
	 */
	@Override
	public List<Product> getHotList() {
		List<Product> list = findHotList();
		for (Product product : list) {
			product.setCategoryId(null);
			product.setItemType(null);
			product.setSellPoint(null);
			product.setNum(null);
			product.setStatus(null);
			product.setPriority(null);
			product.setCreatedUser(null);
			product.setCreatedTime(null);
			product.setModifiedUser(null);
			product.setModifiedTime(null);
		}
		return list;
	}

	/**
	 * 2. 获取商品详情
	 */
	@Override
	public Product getById(Integer id) {
		// 调用私有方法执行查询
		Product product = findById(id);
		// 判断查询结果是否为null：ProductNotFoundException
		if(product == null) {
			throw new ProductNotFoundException("获取商品详情失败！商品数据不存在！");
		}

		// 将查询结果中的部分属性设置为null，例如隐藏属性和日志
		product.setPriority(null);
		product.setCreatedUser(null);
		product.setCreatedTime(null);
		product.setModifiedUser(null);
		product.setModifiedTime(null);

		// 返回结果
		return product;
	}

	@Override
	public void reduceNum(Integer pid, Integer amount) {
		// 通过参数pid查询商品数据
		Product result = findById(pid);
		// 判断查询结果是否为null：ProductNotFoundException
		if (result == null) {
			throw new ProductNotFoundException(
					"更新商品库存失败！尝试访问的商品数量不存在！");
		}

		// 暂不考虑商品下架的问题

		// 判断查询结果中的num(当前库存)是否小于参数amount(将要购买或减少的库存量)：ProductOutOfStockException
		if (result.getNum() < amount) {
			throw new ProductOutOfStockException(
					"更新商品库存失败！当前商品库存已经不足！");
		}

		// 执行减少库存
		updateNum(pid, result.getNum() - amount);
	}


	/**
	 *	查询热销的前4名的商品列表--1
	 */
	private List<Product> findHotList(){
		return productMapper.findHotList();
	}

	/**
	 * 查询商品详情--2
	 */
	private Product findById(Integer id) {
		return productMapper.findById(id);
	}
	
	/**
	 *  更新商品的库存
	 * @param pid 商品的id
	 * @param num 新的库存量
	 * @throws UpdateException 更新商品数量失败
	 */
	private void updateNum(Integer pid, Integer num) {
		Integer rows = productMapper.updateNum(pid, num);
		if (rows != 1) {
			throw new UpdateException(
				"更新商品数量失败！更新数据时出现未知错误！");
		}
	}
}














