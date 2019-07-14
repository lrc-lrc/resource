package cn.tedu.storeexe.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import cn.tedu.storeexe.entity.User;

public interface UserMapper {
	
	/**1. 插入用户数据*/
	Integer addnew(User user);
	
	/**2. 根据用户名查询用户数据*/
	User findByUsername(String username);
	
	/**3. 修改密码*/
	Integer updatePassword(@Param("uid") Integer uid, @Param("password") String password, @Param("modifiedUser") String modifiedUser, @Param("modifiedTime") Date modifiedTime);
	
	/**4. 根据uid查询用户数据*/
	User findByUid(Integer uid);
	
	/**5. 修改信息*/
	Integer updateInfo(User user);
	
	/**6. 修改头像*/
	Integer updateAvatar(@Param("uid") Integer uid, @Param("avatar") String avatar, @Param("modifiedUser") String modifiedUser, @Param("modifiedTime") Date modifiedTime);

	
}
















