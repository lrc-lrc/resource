package cn.tedu.storeexe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.storeexe.entity.District;
import cn.tedu.storeexe.service.IDistrictService;
import cn.tedu.storeexe.util.JsonResult;

@RestController
@RequestMapping("district")
public class DistrictController extends BaseController {
	
	@Autowired
	private IDistrictService iDistrictService;
	
	@GetMapping("/")
	public JsonResult<List<District>> getByParent(String parent) {
		// 调用业务层对象执行查询
		List<District> list =iDistrictService.getByParent(parent);
		// 返回成功和查询结果
		return new JsonResult<List<District>>(SUCCESS,list);
	}
}
