package cn.tedu.storeexe.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.storeexe.entity.District;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test_DistrictMapper {

	@Autowired
	DistrictMapper districtMapper;
	
	@Test
	public void findByParent() {
		String parent = "86";
		List<District> list = districtMapper.findByParent(parent);
		System.err.println("count=" + list.size());
		for (District item : list) {
			System.err.println(item);
		}
	}
	
	@Test
	public void findByCode() {
		String code = "110101";
		District district = districtMapper.findByCode(code);
		System.err.println(district);
	}
	
}









