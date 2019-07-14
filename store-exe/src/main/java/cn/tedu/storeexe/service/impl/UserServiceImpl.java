package cn.tedu.storeexe.service.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.tedu.storeexe.entity.User;
import cn.tedu.storeexe.mapper.UserMapper;
import cn.tedu.storeexe.service.IUserService;
import cn.tedu.storeexe.service.ex.InsertException;
import cn.tedu.storeexe.service.ex.PasswordNotMatchException;
import cn.tedu.storeexe.service.ex.UpdateException;
import cn.tedu.storeexe.service.ex.UserNotFoundException;
import cn.tedu.storeexe.service.ex.UsernameDuplicateException;

/**
 * 处理用户相关请求的业务逻辑
 * @author Administrator
 *
 */
@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserMapper userMapper;

	/**1. 处理用户注册操作*/
	public void reg(User user) {
		// 根据参数user中的getUsername()获取尝试注册的用户名
		String username = user.getUsername();
		// 根据以上用户名查询用户数据
		User result = userMapper.findByUsername(username);
		// 判断查询结果是否不为null
		if(result != null) {
			// 是：用户名已经被占用，抛出UsernameDuplicateException
			throw new UsernameDuplicateException("注册失败！用户名(" + username + ")已存在！");
		} else {
			// 用户名未被占用，允许注册
			// 向参数user中补全属性：盐值
			String salt = UUID.randomUUID().toString();
			// 取出参数user中的原始密码
			String password = user.getPassword();
			// 将原始密码加密
			String md5Password = getMd5Password(password, salt);
			// 向参数user中补全属性：加密后的密码
			user.setPassword(md5Password);
			// 向参数user中补全属性：isDelete-0
			user.setIsDelete(0);
			// 向参数user中补全属性：盐值
			user.setSalt(salt);
			// 向参数user中补全属性：4项日志
			Date now = new Date();
			user.setCreatedUser(username);
			user.setCreatedTime(now);
			user.setModifiedUser(username);
			user.setModifiedTime(now);
			// 执行注册
			Integer rows = userMapper.addnew(user);
			if(rows != 1) {
				throw new InsertException("注册失败！插入用户数据时出现未知错误！请联系管理员！");
			}
		}        
	}

	/**
	 * 2.用户登录
	 * @param 用户名
	 * @param 密码
	 * @return	用户数据
	 * @throws 用户找不到异常
	 * @throws 密码不匹配异常
	 */
	@Override
	public User login(String username, String password) throws UserNotFoundException, PasswordNotMatchException {
		User result = userMapper.findByUsername(username);
		// 判断查询结果是否为null
		if(result == null) {
			// 是：抛出UserNotFoundException
			throw new UserNotFoundException("登录失败！用户数据不存在！");
		}

		// 判断查询结果中的isDelete是否为1
		if(result.getIsDelete() == 1) {
			// 是：UserNotFoundException
			throw new UserNotFoundException("登录失败！用户数据不存在！");
		}

		// 查询结果中获取盐值
		String salt = result.getSalt();
		// 基于参数password和盐值执行加密
		String md5Password = getMd5Password(password, salt);
		if(!result.getPassword().equals(md5Password)) {
			throw new PasswordNotMatchException("登录失败！密码错误！");
		}

		// 将查询结果中的password设置为null
		result.setPassword(null);
		// 将查询结果中的salt设置为null
		result.setSalt(null);
		// 将查询结果中的isDelete设置为null
		result.setIsDelete(null);
		// 返回查询结果
		return result;
	}

	/**
	 * 3.修改密码操作
	 */
	@Override
	public void changePassword(Integer uid, String oldPassword, String newPassword, String modifiedUser)
			throws UserNotFoundException, PasswordNotMatchException, UpdateException {
		// 根据参数uid查询用户数据
		User result = userMapper.findByUid(uid);
		// 判断查询结果是否为null：UserNotFoundException
		if(result == null) {
			throw new UserNotFoundException("修改密码失败！用户数据不存在！");
		}

		// 判断查询结果中的isDelete是否为1：UserNotFoundException
		if(result.getIsDelete() == 1) {
			throw new UserNotFoundException("修改密码失败！用户数据不存在！");
		}

		// 从查询结果中获取盐值
		String salt = result.getSalt();
		// 对参数oldPassword执行加密，得到oldMd5Password
		String oldMd5Password = getMd5Password(oldPassword, salt);
		// 判断查询结果中的密码与oldMd5Password是否不匹配：PasswordNotMatchException
		if(!result.getPassword().equals(oldMd5Password)) {
			throw new PasswordNotMatchException("修改密码失败！原密码错误！");
		}

		// 对参数newPassword执行加密，得到newMd5Passowrd
		String newMd5Passowrd = getMd5Password(newPassword, salt);
		// 执行更新，获取返回值(受影响的行数)
		Integer rows = userMapper.updatePassword(uid, newMd5Passowrd, modifiedUser, new Date());
		// 判断受影响的行数是否不为1：UpdateException
		if(rows != 1) {
			throw new UpdateException("修改密码失败！更新数据时出现未知错误！");
		}
	}

	/**
	 * 4.修改个人信息操作
	 */
	@Override
	public void changeInfo(Integer uid, String username, User user) throws UserNotFoundException, UpdateException {
		// 根据参数uid查询用户数据
		User result = userMapper.findByUid(uid);
		// 判断查询结果是否为null：UserNotFoundException
		if(result == null) {
			throw new UserNotFoundException("用户不存在！");
		}

		// 判断查询结果中isDelete是否为1：UserNotFoundException
		if(result.getIsDelete() == 1) {
			throw new UserNotFoundException("用户不存在！");
		}

		// 将参数uid和参数username封装到参数user的uid和modifiedUser属性中
		user.setUid(uid);
		user.setModifiedUser(username);
		user.setModifiedTime(new Date());
		// 执行更新，获取返回值（受影响的行数）
		Integer rows = userMapper.updateInfo(user);
		// 判断受影响的行数是否不为1：UpdateException
		if(rows != 1) {
			throw new UpdateException("修改异常！由于未知因素！");
		}
	}

	/**
	 * 5.通过uid获取相关个人信息
	 */
	@Override
	public User getByUid(Integer uid) {
		// 根据参数uid查询用户数据
		User result = userMapper.findByUid(uid);
		// 判断查询结果是否为null：UserNotFoundException
		if(result == null) {
			throw new UserNotFoundException("用户数据不存在！");
		}

		// 判断查询结果中的isDelete是否为1：UserNotFoundException
		if(result.getIsDelete() == 1) {
			throw new UserNotFoundException("用户数据不存在！");
		}

		// 将查询结果中不相关的数据设置为null
		User user = new User();
		user.setUsername(result.getUsername());
		user.setPhone(result.getPhone());
		user.setEmail(result.getEmail());
		user.setGender(result.getGender());
		// 返回查询结果
		return user;
	}

	/**
	 * 执行密码加密，获取加密后的密码
	 * @param password
	 * @return 加密后的密码
	 */
	private String getMd5Password(String password, String salt) {
		// 加密规则：使用“salt+password+salt”作为消息，执行3次消息摘要
		String str = salt + password + salt;
		for(int i=0; i<3; i++) {
			str = DigestUtils.md5DigestAsHex(str.getBytes()); 
		}
		return str;
	}

	@Override
	public void changeAvatar(Integer uid, String avatar, String modifiedUser)
			throws UserNotFoundException, UpdateException {
		// 根据参数uid查询用户数据
		User result = userMapper.findByUid(uid);
		
		// 判断查询结果是否为null：UserNotFoundException
		if(result == null) {
			throw new UserNotFoundException("修改头像失败！用户不存存在！");
		}
		
		// 判断查询结果中的isDelete是否为1：UserNotFoundException
		if(result.getIsDelete() == 1) {
			throw new UserNotFoundException("修改头像失败！用户不存存在！");
		}

		// 执行更新，获取返回值(受影响的行数)
		Integer rows = userMapper.updateAvatar(uid, avatar, modifiedUser, new Date());
		
		// 判断受影响的行数是否不为1：UpdateException
		if(rows != 1) {
			throw new UpdateException("修改头像失败！由于未知因素！");
		}
	}

}














