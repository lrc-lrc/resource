package cn.tedu.storeexe.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.storeexe.entity.Address;
import cn.tedu.storeexe.entity.District;
import cn.tedu.storeexe.mapper.AddressMapper;
import cn.tedu.storeexe.service.IAddressService;
import cn.tedu.storeexe.service.IDistrictService;
import cn.tedu.storeexe.service.ex.AccessDeniedException;
import cn.tedu.storeexe.service.ex.AddressCountLimitException;
import cn.tedu.storeexe.service.ex.AddressNotFoundException;
import cn.tedu.storeexe.service.ex.DeleteException;
import cn.tedu.storeexe.service.ex.InsertException;
import cn.tedu.storeexe.service.ex.UpdateException;

/**
 * 处理收货地址数据的业务实现类
 * @author Administrator
 *
 */
@Service
public class AddressServiceImpl implements IAddressService {

	@Autowired
	private AddressMapper addressMapper;

	@Autowired
	private IDistrictService districtService;		

	/**1. 处理添加用户的收货地址操作*/
	@Override
	public void create(Integer uid, String username, Address address) {
		// 基于参数uid查询该用户的收货地址数量
		Integer count = countByUid(uid);

		// 判断数量是否达到上限值
		if(count >= MAX_COUNT) {
			// 是：AddressCountLimitException
			throw new AddressCountLimitException("增加收货地址失败！当前收货地址的数量("+count+")达到("+MAX_COUNT+")上限！");
		}

		// 补全参数address中的数据：uid
		address.setUid(uid);
		// 补全参数address中的数据：省市区的名称
		address.setProvinceName(getDistrictNameByCode(address.getProvinceCode()));
		address.setCityName(getDistrictNameByCode(address.getCityCode()));
		address.setAreaName(getDistrictNameByCode(address.getAreaCode()));

		// 补全参数address中的数据：isDefault，根据收货地址数量确定该属性的值
		Integer isDefault = count==0 ? 1 : 0;
		address.setIsDefault(isDefault);
		// 创建当前时间对象
		Date now = new Date();
		// 补全参数address中的数据：4项日志
		address.setCreatedUser(username);
		address.setCreatedTime(now);
		address.setModifiedUser(username);
		address.setModifiedTime(now);
		// 执行增加
		addnew(address);
	}

	/**2.根据uid查询用户的收货地址列表*/
	public List<Address> getByUid(Integer uid){
		return findByUid(uid);		
	}

	/**3. 将收货地址设置为默认*/
	@Transactional
	public void setDefault(Integer aid, Integer uid, String username)
			throws AddressNotFoundException, AccessDeniedException, UpdateException {
		// 根据参数aid查询数据
		Address result = findByAid(aid);
		// 判断查询结果是否为null
		if(result == null) {
			// 是：AddressNotFoundException
			throw new AddressNotFoundException("设置默认收货地址失败！尝试访问的数据不存在！");
		}

		// 判断查询结果中的uid与参数uid是否不同
		if(!result.getUid().equals(uid)) {
			// 是：AccessDeniedException
			throw new AccessDeniedException("设置默认收货地址失败！不允许访问他人的数据！");
		}

		// 将该用户的所有收货地址设置为非默认
		updateNonDefault(uid);

		// 将指定的收货地址设置为默认
		updateDefault(aid, username, new Date());
	}

	/**4. 删除收货地址信息*/
	@Transactional
	public void delete(Integer aid, Integer uid, String username)
			throws AddressNotFoundException, AccessDeniedException, DeleteException, UpdateException {
		// 根据参数aid查询数据
		Address result = findByAid(aid);
		// 判断查询结果是否为null：AddressNotFoundException
		if(result == null) {
			throw new AddressNotFoundException("删除收货地址失败！尝试访问的数据不存在！");
		}

		// 判断查询结果中的uid与参数uid是否不同：AccessDeniedException
		if(!result.getUid().equals(uid)) {
			throw new AccessDeniedException("删除收货地址失败！不允许访问他人的数据！");
		}

		// 执行删除
		delete(aid);

		// 判断此前的查询结果中的isDefault是否为0：return
		if(result.getIsDefault() == 0) {
			return;
		}
		
		// 根据参数uid统计收货地址数量
		Integer count = countByUid(uid);
		// 判断数量为0：return
		if(count == 0) {
			return;
		}
		
		// 根据参数uid查询最后修改的收货地址
		Address address = findLastModified(uid);

		// 根据查询到的最后修改的收货地址中的aid执行设置默认
		setDefault(address.getAid(), uid, username);
	}
	
	/**5. 获取收货地址*/
	public Address getByAid(Integer aid) {
		return findByAid(aid);
	}

	/**
	 * 	增加收货地址
	 * @param address 收货地址数据
	 * @throws InsertException 插入数据异常
	 */
	private void addnew(Address address) {		
		Integer rows = addressMapper.addnew(address);
		if(rows != 1) {
			throw new InsertException("增加收货地址失败！插入数据时出现未知错误！");
		}
	}

	/**
	 * 	统计某用户的收货地址数量
	 * @param uid 用户的id
	 * @return 用户的收货地址的数量
	 */
	private Integer countByUid(Integer uid) {
		if(uid==null || uid<1) {
			throw new IllegalArgumentException();
		}
		return addressMapper.countByUid(uid);
	}

	/**
	 * 	根据名称获取省/市/区的信息
	 * @param code
	 * @return
	 */
	private String getDistrictNameByCode(String code) {
		District result = districtService.getByCode(code);
		return result == null ? "" : result.getName();
	}

	/**根据uid查询用户的收货地址列表*/
	private List<Address> findByUid(Integer uid){
		List<Address> address = addressMapper.findByUid(uid);
		return address;		
	}

	/**
	 * 	将指定的收货地址设置为默认
	 * @param aid 收货地址的id
	 * @param username 当前登录的用户名
	 * @param modifiedTime 操作时间
	 * @throws UpdateException 更新数据异常
	 */
	private void updateDefault(Integer aid, String username, Date modifiedTime) {
		Integer rows = addressMapper.updateDefault(aid, username, modifiedTime);
		if(rows != 1) {
			throw new UpdateException("设置默认收货失败，更新数据异常[2]！");
		}
	}

	/**
	 * 	将指定用户的收货地址全部设置为非默认
	 * @param uid 用户的id
	 * @throws UpdateException 更新数据异常
	 */
	private void updateNonDefault(Integer uid) {
		Integer rows = addressMapper.updateNonDefault(uid);
		if(rows < 1) {
			throw new UpdateException("设置默认收货失败，更新数据异常[1]！");
		}
	}

	/**
	 * 根据收货地址id查询收货地址详情
	 * @param aid 收货地址id
	 * @return 匹配的收货地址详情，如果没有匹配的数据，则返回null
	 */
	private Address findByAid(Integer aid) {
		return addressMapper.findByAid(aid);
	}

	/**删除收货地址*/
	private void delete(Integer aid) {
		Integer rows = addressMapper.deleteByAid(aid);
		if(rows != 1) {
			throw new DeleteException("删除收货地址失败，删除数据异常！");
		}
	}

	/**根据参数uid查询最后修改的收货地址*/
	private Address findLastModified(Integer uid) {
		return addressMapper.findLastModified(uid);
	}

	
}







