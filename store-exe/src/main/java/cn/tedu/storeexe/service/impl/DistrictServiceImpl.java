package cn.tedu.storeexe.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.storeexe.entity.District;
import cn.tedu.storeexe.mapper.DistrictMapper;
import cn.tedu.storeexe.service.IDistrictService;

@Service
public class DistrictServiceImpl implements IDistrictService {
	
	@Autowired
	private DistrictMapper districtMapper;
	
	/**1. 获取全国所有省/某省所有市/某市所有区的列表*/
	public List<District> getByParent(String parent) {
		return findByParent(parent);
	}
	
	/**2.获取省/市/区的信息*/
	@Override
	public District getByCode(String code) {	
		return findByCode(code);
	}
	
	
	
	
	
	
	
	
	/**
	 * 	获取全国所有省/某省所有市/某市所有区的列表
	 * @param parent 父级单位的代号，如果获取全国所有省，则使用86作为父级代号
	 * @return 匹配的省或市或区的列表
	 */
	private List<District> findByParent(String parent) {
		return districtMapper.findByParent(parent);
	}
	
	/**
	 * 	根据代号查找省/市/区的名称
	 * @param code
	 * @return
	 */
	private District findByCode(String code) {
		return districtMapper.findByCode(code);
	}

}
