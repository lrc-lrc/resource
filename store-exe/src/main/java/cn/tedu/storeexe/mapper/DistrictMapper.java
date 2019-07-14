package cn.tedu.storeexe.mapper;

import java.util.List;

import cn.tedu.storeexe.entity.District;
/**
 * 处理省、市、区数据的持久层接口
 */
public interface DistrictMapper {
	
	/**
	 * 1. 获取全国所有省/某省所有市/某市所有区的列表
	 * @param parent 父级单位的代号，如果获取全国所有省，则使用86作为父级代号
	 * @return 匹配的省或市或区的列表
	 */
	List<District> findByParent(String parent);
	
	/**
	 * 2.根据代号查找省/市/区的名称
	 * @param code
	 * @return
	 */
	District findByCode(String code);
}












