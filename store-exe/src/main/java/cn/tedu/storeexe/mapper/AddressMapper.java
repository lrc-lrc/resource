package cn.tedu.storeexe.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.storeexe.entity.Address;

public interface AddressMapper {
	
	/**1. 增加用户的收货地址数据*/
	Integer addnew(Address address);
	
	/**2. 根据uid统计收货地址数量*/
	Integer countByUid(Integer uid);
	
	/**3. 根据uid查询用户的收货地址信息*/
	List<Address> findByUid(Integer uid);
	
	/**4. 根据aid将指定的收货地址设置为默认*/
	Integer updateDefault(@Param("aid")Integer aid, @Param("username") String username, @Param("modifiedTime") Date modifiedTime);
	
	/**5. 根据uid将收货地址都修改为非默认*/
	Integer updateNonDefault(Integer uid);
	
	/**6. 根据aid查询收货地址信息*/
	Address findByAid(Integer aid);
	
	/**7. 根据aid删除用户收货地址*/
	Integer deleteByAid(Integer aid);
	
	/**8.  查询某用户最后修改的收货地址数据*/
	Address findLastModified(Integer uid);
}
















