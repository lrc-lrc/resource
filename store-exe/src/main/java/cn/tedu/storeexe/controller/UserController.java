package cn.tedu.storeexe.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.tedu.storeexe.controller.ex.FileEmptyException;
import cn.tedu.storeexe.controller.ex.FileSizeException;
import cn.tedu.storeexe.controller.ex.FileTypeException;
import cn.tedu.storeexe.entity.User;
import cn.tedu.storeexe.service.IUserService;
import cn.tedu.storeexe.util.JsonResult;

/**
 * 处理用户相关请求的控制器
 * @author Administrator
 *
 */
@RequestMapping("user")
@RestController
public class UserController extends BaseController {

	@Autowired
	private IUserService iUserService;

	/**1. 处理注册请求*/
	@PostMapping("reg")
	public JsonResult<Void> handleReg(User user) {
		iUserService.reg(user);
		return new JsonResult<Void>(SUCCESS);
	}

	/**2. 处理登录请求*/
	@PostMapping("login")
	public JsonResult<User> handleLogin(String username, String password, HttpSession session) {
		// 调用业务层对象的“登录”方法，获取返回结果
		User data = iUserService.login(username, password);	
		// 向Session中存入用户id和用户名
		session.setAttribute("uid", data.getUid());
		session.setAttribute("username", data.getUsername());
		// 返回
		return new JsonResult<User>(SUCCESS, data);
	}

	/**3. 处理修改密码请求*/
	@PostMapping("change_password")
	public JsonResult<User> handleChangePassword(@RequestParam("old_password") String oldPassword, @RequestParam("new_password") String newPassword, HttpSession session) {
		// 从session中获取uid
		Integer uid = getUidFromSession(session);
		// 从session中获取username
		String username = getUsernameFromSession(session);
		// 调用service对象执行修改密码
		iUserService.changePassword(uid, oldPassword, newPassword, username);
		// 响应成功
		return new JsonResult<User>(SUCCESS);
	}

	/**4. 处理修改个人信息请求*/
	@PostMapping("change_info")
	public JsonResult<Void> handleChangeInfo(HttpSession session, User user) {
		// 从session中获取uid
		Integer uid = getUidFromSession(session);
		// 从session中获取username
		String username = getUsernameFromSession(session);
		// 调用service对象执行修改个人信息
		iUserService.changeInfo(uid, username, user);
		// 响应成功
		return new JsonResult<Void>(SUCCESS);
	}

	/**5. 处理获取个人信息请求*/
	@GetMapping("get_by_uid")
	public JsonResult<User> handleLogin(HttpSession session) {
		// 从session中获取uid
		Integer uid = getUidFromSession(session);
		// 执行获取数据
		User data = iUserService.getByUid(uid);
		// 返回成功与数据
		return new JsonResult<User>(SUCCESS, data);
	}
	
	//允许上传的头像文件的最大大小
	public static final long AVATAR_MAX_SIZE = 2 * 1024 * 1024;
	//允许上传的头像文件的类型列表
	public static final List<String> AVATAR_CONTENT_TYPES = new ArrayList<String>();
	
	static {
		AVATAR_CONTENT_TYPES.add("image/jpeg");
		AVATAR_CONTENT_TYPES.add("image/png");
	}
	
	/**6. 处理修改头像请求*/
	@PostMapping("change_avatar")
	public JsonResult<String> handleChangePassword(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		// 检查文件是否为空
		if(file.isEmpty()) {
			throw new FileEmptyException("上传头像失败！请选择有效的图片文件！");
		}
		
		// 检查文件大小是否超出限制
		if(file.getSize() > AVATAR_MAX_SIZE) {
			throw new FileSizeException("上传头像失败！不允许上传超过"+(AVATAR_MAX_SIZE/1024)+"KB的图片文件！");
		}
		
		// 检查文件类型是否超出限制
		if(!AVATAR_CONTENT_TYPES.contains(file.getContentType())) {
			throw new FileTypeException("上传头像失败！选择的文件类型超出了限制！\r\r允许使用的文件类型有：" + AVATAR_CONTENT_TYPES);
		}
		
		// 确定文件夹
		String parentDir = request.getServletContext().getRealPath("upload");
		File parent = new File(parentDir);
		if(!parent.exists()) {
			parent.mkdirs();
		}

		//确定文件名
		String fileName = UUID.randomUUID().toString();
		String originalFilename = file.getOriginalFilename();
		int beginIndex = originalFilename.lastIndexOf(".");
		String suffix = originalFilename.substring(beginIndex);
		String child = fileName + suffix;

		// 将上传文件保存
		File dest = new File(parent, child);
		try {
			file.transferTo(dest);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// 将文件的路径记录到数据库
		String avatarPath = "/upload/" + child;
		Integer uid = getUidFromSession(request.getSession());
		String username = getUsernameFromSession(request.getSession());
		iUserService.changeAvatar(uid, avatarPath, username);

		// 响应结果
		return new JsonResult<String>(SUCCESS, avatarPath);
	}
}















