package cn.tedu.storeexe.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.storeexe.entity.District;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test_DistrictService {
	
	@Autowired
	private IDistrictService iDistrictService;
	
	@Test
	public void getByParent() {
		try {
			String parent = "86";
			List<District> list =  iDistrictService.getByParent(parent);
			System.err.println("count=" + list.size());
			for (District item : list) {
				System.err.println(item);
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void getByCode() {
		String code = "110101";
		District district = iDistrictService.getByCode(code);
		System.err.println(district);
	}
}










