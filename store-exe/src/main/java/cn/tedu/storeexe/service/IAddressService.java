package cn.tedu.storeexe.service;

import java.util.List;

import cn.tedu.storeexe.entity.Address;
import cn.tedu.storeexe.service.ex.AccessDeniedException;
import cn.tedu.storeexe.service.ex.AddressNotFoundException;
import cn.tedu.storeexe.service.ex.DeleteException;
import cn.tedu.storeexe.service.ex.UpdateException;

public interface IAddressService {
	
	/**
	 * 每个用户可以创建的收货地址数据的最大数量
	 */
	int MAX_COUNT = 20;
	
	/**
	 * 1. 处理添加用户的收货地址操作
	 * @param uid 用户uid
	 * @param username 用户名
	 * @param address 收货地址信息
	 */
	public void create(Integer uid, String username, Address address);
	
	/**2. 根据uid查询用户的收货地址信息*/
	public List<Address> getByUid(Integer uid);		
	
	/**3. 处理将收货地址设置为默认操作*/
	void setDefault(Integer aid, Integer uid, String username) throws AddressNotFoundException, AccessDeniedException, UpdateException;
	
	/**4. 删除收货地址信息*/
	void delete(Integer aid, Integer uid, String username) throws AddressNotFoundException, AccessDeniedException, DeleteException, UpdateException;
	
	/**5. 获取收货地址*/
	Address getByAid(Integer aid);
}
