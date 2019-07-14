package cn.tedu.storeexe.mapper;


import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.storeexe.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test_UserMapper {
	
	@Autowired
	private UserMapper userMapper;
	
	@Test
	/**1. 插入用户数据*/
	public void addnew() {
		User user = new User();
		user.setUsername("spring2");
		user.setPassword("888888");
		Integer rows = userMapper.addnew(user);
		System.err.println("rows=" + rows);
	}
	
	@Test
	/**2. 根据用户名查询用户数据*/
	public void findByUsername() {
		String username = "root";
		User user = userMapper.findByUsername(username);
		System.err.println(user);
	}

	@Test
	/**3. 修改密码*/
	public void updatePassword(){
		Integer rows = userMapper.updatePassword(2, "123456", 
								"超级管理员", new Date());
		System.err.println("rows=" + rows);
	}
	
	@Test
	/**4. 根据uid查询用户数据*/
	public void findByUid() {
		Integer uid = 1;
		User user = userMapper.findByUid(uid);
		System.err.println(user);
	}
	
	@Test
	/**5. 修改信息*/
	public void updateInfo() {
		User user = new User();
		user.setUid(3);
		user.setPhone("12346456465");
		user.setEmail("123464@qq.com");
		user.setGender(0);
		user.setModifiedUser("123464@qq.com");
		user.setModifiedTime(new Date());
		Integer rows = userMapper.updateInfo(user);	
		System.err.println("rows=" + rows);
	}
	
	@Test
	/**3. 修改密码*/
	public void updateAvatar(){
		Integer rows = userMapper.updateAvatar(3, "f:/a.jap", 
								"超级管理员", new Date());
		System.err.println("rows=" + rows);
	}
	
}














