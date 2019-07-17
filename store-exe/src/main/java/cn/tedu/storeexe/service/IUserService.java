package cn.tedu.storeexe.service;

import cn.tedu.storeexe.entity.User;
import cn.tedu.storeexe.service.ex.PasswordNotMatchException;
import cn.tedu.storeexe.service.ex.UpdateException;
import cn.tedu.storeexe.service.ex.UserNotFoundException;

/**
 * 处理用户请求的业务逻辑
 * @author Administrator
 *
 */
public interface IUserService {
	
	/**1. 处理用户注册操作*/
	public void reg(User user);
	
	/**
	 * 2.用户登录
	 * @param 用户名
	 * @param 密码
	 * @return	用户数据
	 * @throws 用户找不到异常
	 * @throws 密码不匹配异常
	 */
	public User login(String username, String password) throws UserNotFoundException, PasswordNotMatchException;
	
	/**
	 * 3.修改密码
	 * @param uid 用户id
	 * @param oldPassword 旧密码
	 * @param newPassword 新密码
	 * @param modifiedUser 修改人
	 * @throws UserNotFoundException 用户找不到异常
	 * @throws PasswordNotMatchException 密码不匹配异常
	 * @throws UpdateException 更新异常
	 */
	public void changePassword(Integer uid, String oldPassword, String newPassword, String modifiedUser) throws UserNotFoundException, PasswordNotMatchException, UpdateException;

	/**
	 * 4.修改个人信息
	 * @param uid 用户id
	 * @param username 用户名
	 * @param user 用户
	 * @throws UserNotFoundException 用户找不到异常
	 * @throws UpdateException 修改异常
	 */
	public void changeInfo(Integer uid, String username, User user) throws UserNotFoundException, UpdateException;
 
	/**
	 * 5.通过uid获取个人信息
	 * @param uid
	 * @return
	 */
	public User getByUid(Integer uid);
	
	/**
	 * 6.修改密码
	 * @param uid 用户id
	 * @param avatar 头像
	 * @param modifiedUser 修改人
	 * @throws UserNotFoundException 用户找不到异常
	 * @throws UpdateException 更新异常
	 */
	public void changeAvatar(Integer uid, String avatar, String modifiedUser) throws UserNotFoundException, UpdateException;

	
}



















