package cn.tedu.storeexe.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.storeexe.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test_UserService {

	@Autowired
	private IUserService iUserService;

	@Test
	/**1. 处理用户注册操作*/
	public void reg() {
		User user = new User();
		user.setUsername("service1");
		user.setPassword("888888");
		try {
			iUserService.reg(user);
			System.err.println("注册成功！");
		} catch(Exception e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}

	/**2. 处理用户登录操作*/
	@Test
	public void login() {
		try {
			String username = "root";
			String password ="1234561";
			User user = iUserService.login(username, password);
			System.err.println(user);
		} catch(Exception e) {
			System.err.println(e.getClass().getName()+":");
			System.err.println(e.getMessage());
		}
	}

	/**3. 处理修改密码操作*/
	@Test
	public void changePassword() {
		try {
			Integer uid = 1;
			String oldPassword ="123456";
			String newPassword = "1234";
			String modifiedUser = "系统管理员";
			iUserService.changePassword(uid, oldPassword, newPassword, modifiedUser);
			System.err.println("OK");
		} catch(Exception e) {
			System.err.println(e.getClass().getName()+":");
			System.err.println(e.getMessage());
		}
	}

	/**
	 * 4.修改个人信息操作
	 */
	@Test
	public void changeInfo() {
		try {
			Integer uid = 700;
			String username = "xiaomei";
			User user = new User();
			user.setPhone("123456464");
			user.setEmail("xm@qq.com");
			user.setGender(0);
			iUserService.changeInfo(uid, username, user);
			System.err.println("ok");
		} catch(Exception e) {
			System.err.println(e.getClass().getName()+":");
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * 5.通过uid获取相关个人信息
	 */
	@Test
	public void getByUid() {
		try {
			Integer uid = 3;
			User data = iUserService.getByUid(uid);
			System.err.println(data);
		} catch(Exception e) {
			System.err.println(e.getClass().getName()+":");
			System.err.println(e.getMessage());
		}
	}
	
	/**3. 处理修改密码操作*/
	@Test
	public void changeAvatar() {
		try {
			Integer uid = 1;
			String avatar ="d://a.jpg";
			String modifiedUser = "系统管理员1";
			iUserService.changeAvatar(uid, avatar, modifiedUser);
			System.err.println("OK");
		} catch(Exception e) {
			System.err.println(e.getClass().getName()+":");
			System.err.println(e.getMessage());
		}
	}
}
















