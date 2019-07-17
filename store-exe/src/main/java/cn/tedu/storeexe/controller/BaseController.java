package cn.tedu.storeexe.controller;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.storeexe.controller.ex.FileEmptyException;
import cn.tedu.storeexe.controller.ex.FileSizeException;
import cn.tedu.storeexe.controller.ex.FileStateException;
import cn.tedu.storeexe.controller.ex.FileTypeException;
import cn.tedu.storeexe.controller.ex.FileUploadException;
import cn.tedu.storeexe.controller.ex.FileUploadIOException;
import cn.tedu.storeexe.service.ex.AccessDeniedException;
import cn.tedu.storeexe.service.ex.AddressCountLimitException;
import cn.tedu.storeexe.service.ex.AddressNotFoundException;
import cn.tedu.storeexe.service.ex.DeleteException;
import cn.tedu.storeexe.service.ex.InsertException;
import cn.tedu.storeexe.service.ex.PasswordNotMatchException;
import cn.tedu.storeexe.service.ex.ProductNotFoundException;
import cn.tedu.storeexe.service.ex.ServiceException;
import cn.tedu.storeexe.service.ex.UpdateException;
import cn.tedu.storeexe.service.ex.UserNotFoundException;
import cn.tedu.storeexe.service.ex.UsernameDuplicateException;
import cn.tedu.storeexe.util.JsonResult;

/**
 * 用户控制器的基类
 * @author Administrator
 *
 */
public abstract class BaseController {
	
	public static final Integer SUCCESS = 2000;
	
	/**1. 处理异常操作*/
	@ExceptionHandler({ServiceException.class, FileUploadException.class})
	@ResponseBody
	public JsonResult<Void> handleException(Exception e){
		// 创建json对象,并对message赋值为异常信息
		JsonResult<Void> jr = new JsonResult<>(e);
		
		//判断异常为哪种类型的异常，并对state赋值
		if (e instanceof UsernameDuplicateException) {
			jr.setState(4000);
		} else if (e instanceof UserNotFoundException) {
			jr.setState(4001);
		} else if (e instanceof PasswordNotMatchException) {
			jr.setState(4002);
		} else if (e instanceof AddressCountLimitException) {
			jr.setState(4003);
		} else if (e instanceof AddressNotFoundException) {
			jr.setState(4004);
		} else if (e instanceof AccessDeniedException) {
			jr.setState(4005);
		} else if (e instanceof ProductNotFoundException) {
			jr.setState(4006);
		} else if (e instanceof InsertException) {
			jr.setState(5000);
		} else if (e instanceof UpdateException) {
			jr.setState(5001);
		} else if (e instanceof DeleteException) {
			jr.setState(5002);
		} else if (e instanceof FileEmptyException) {
			jr.setState(6000);
		} else if (e instanceof FileSizeException) {
			jr.setState(6001);
		} else if (e instanceof FileTypeException) {
			jr.setState(6002);
		} else if (e instanceof FileStateException) {
			jr.setState(6003);
		} else if (e instanceof FileUploadIOException) {
			jr.setState(6004);
		}


		//返回json对象
		return jr;
	}
	
	/**2. 从session获取uid*/
	public Integer getUidFromSession(HttpSession session) {
		Integer uid = Integer.valueOf(session.getAttribute("uid").toString());
		return uid;
	}
	
	/**3. 从session获取username*/
	public String getUsernameFromSession(HttpSession session) {
		String username = String.valueOf(session.getAttribute("username").toString());
		return username;
	}
	
}














