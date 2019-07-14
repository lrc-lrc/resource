package cn.tedu.storeexe.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.storeexe.entity.Product;
/**
 * 处理商品数据的持久层接口
 */
public interface ProductMapper {
	
	/**
	 * 1. 查询热销的前4名的商品列表
	 */
	List<Product> findHotList();
	
	/**
	 * 2. 查询商品详情
	 */
	Product findById(Integer id);
	
	/**
	 * 更新商品的库存
	 * @param pid 商品的id
	 * @param num 新的库存量
	 * @return 受影响的行数
	 */
	Integer updateNum(
			@Param("pid") Integer pid, 
			@Param("num") Integer num);
}












