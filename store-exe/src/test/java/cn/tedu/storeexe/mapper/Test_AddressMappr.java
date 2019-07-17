package cn.tedu.storeexe.mapper;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.storeexe.entity.Address;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test_AddressMappr {
	
	@Autowired
	private AddressMapper addressMapper;
	
	/**1. 插入收货地址数据*/
	@Test
	public void addnew() {
		Address address = new Address();
		address.setUid(1);
		address.setName("小刘同学");
		Integer rows = addressMapper.addnew(address);
		System.err.println("rows=" + rows);
	}
	
	/**2. 根据uid统计收货地址数量*/
	@Test
	public void countByUid() {
		Integer uid = 1;
		Integer count = addressMapper.countByUid(uid);
		System.err.println("count=" + count);
	}
	
	/**3.根据uid查询用户的收货地址列表*/
	@Test
	public void findByUid(){
		Integer uid = 1;
		List<Address> addresses = addressMapper.findByUid(uid);
		for(Address a : addresses) {
			System.err.println(a);
		}
	}
	
	/**4. 根据aid将指定的收货地址设置为默认*/
	@Test
	public void updateDefault() {
		Integer aid = 1;
		String username = "超级管理员";
		Integer rows = addressMapper.updateDefault(aid, username, new Date());
		System.err.println("rows=" + rows);
	}
	
	/**5. 根据uid将收货地址都修改为非默认*/
	@Test
	public void updateNonDefault() {
		Integer uid = 1;
		Integer rows = addressMapper.updateNonDefault(uid);
		System.err.println("rows=" + rows);
	}
	
	/**6. 根据aid查询收货地址信息*/
	@Test
	public void findByAid() {
		Integer aid = 1;
		Address address = addressMapper.findByAid(aid);
		System.err.println(address);
	}
	
	/**7. 根据aid删除用户收货地址*/
	@Test
	public void deleteByAid() {
		Integer aid = 1;
		Integer rows = addressMapper.deleteByAid(aid);
		System.err.println("rows=" + rows);
	}
	
	/**8.  查询某用户最后修改的收货地址数据*/
	@Test
	public void findLastModified() {
		Integer uid = 1;
		Address result = addressMapper.findLastModified(uid);
		System.err.println(result);
	}
}















