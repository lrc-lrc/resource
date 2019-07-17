package cn.tedu.storeexe.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.storeexe.entity.Cart;
import cn.tedu.storeexe.vo.CartVO;

/**
 * 购物车持久层接口
 * @author Administrator
 *
 */
public interface CartMapper {
	
	/** 1. 插入购物车数据 */
	Integer Insert(Cart cart);
	
	/** 2. 更新数量 */
	Integer updateNum(@Param("cid") Integer cid, @Param("num") Integer num,
					   @Param("username") String username, @Param("modifiedTime") Date modifiedTime);
	
	/** 3.根据uid和pid查询购物车数据 */
	Cart findByUidAndPid(
		@Param("uid") Integer uid, 
		@Param("pid") Integer pid);
	
	/**
	 *  4. 根据用户uid查询该用户的购物车列表
	 * @param uid
	 * @return
	 */
	List<CartVO> findByUid(Integer uid);
	
	/** 5. 根据cid查询购物车数据 */
	Cart findByCid(Integer cid);
	
	/** 6. 根据cid删除购物车数据*/
	Integer deleteByCid(Integer cid);
	
	/** 7. 根据购物车数据若干个id值匹配购物车数据的详情 */
	List<CartVO> findByCids(Integer[] cids);
	
	Integer deleteByCids(Integer[] cids);

}












