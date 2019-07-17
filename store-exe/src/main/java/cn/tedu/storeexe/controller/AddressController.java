package cn.tedu.storeexe.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.storeexe.entity.Address;
import cn.tedu.storeexe.service.IAddressService;
import cn.tedu.storeexe.util.JsonResult;

@RestController
@RequestMapping("address")
public class AddressController extends BaseController {

	@Autowired
	private IAddressService iAddressService;

	@RequestMapping("create")
	public JsonResult<Void> create(Address address, HttpSession session){
		// 从Session中获取uid和username
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		// 调用业务层对象执行新增收货地址
		iAddressService.create(uid, username, address);
		// 返回成功
		return new JsonResult<Void>(SUCCESS);
	}

	/**2.根据uid查询用户的收货地址列表*/
	@GetMapping("/")
	public JsonResult<List<Address>> getByUid(HttpSession session){
		Integer uid = getUidFromSession(session);
		List<Address> data = iAddressService.getByUid(uid);
		return  new JsonResult<List<Address>>(SUCCESS, data);
	}

	@RequestMapping("/{aid}/set_default")
	public JsonResult<Void> setDefault(@PathVariable("aid")Integer aid, HttpSession session){
		// 从Session中获取uid和username
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		// 调用业务层对象执行设置默认
		iAddressService.setDefault(aid, uid, username);
		// 返回
		return new JsonResult<Void>(SUCCESS);
	}

	/**4. 处理删除收货地址请求*/
	@RequestMapping("{aid}/delete")
	public JsonResult<Void> delete(@PathVariable("aid") Integer aid, HttpSession session){
		// 从Session中获取uid和username
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		// 调用业务层对象执行删除
		iAddressService.delete(aid, uid, username);
		// 返回
		return new JsonResult<Void>(SUCCESS);
	}
	
}














