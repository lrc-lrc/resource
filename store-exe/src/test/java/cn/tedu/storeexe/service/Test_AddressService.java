package cn.tedu.storeexe.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.storeexe.entity.Address;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test_AddressService {

	@Autowired
	private IAddressService  iAddressService;

	@Test
	public void create() {
		try {
			Integer uid = 1;
			String username = "root";
			Address address = new Address();
			address.setName("小月同学");
			address.setAddress("广州");
			iAddressService.create(uid, username, address);
			System.err.println("ok.");
		} catch (Exception e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}

	/**2.根据uid查询用户的收货地址信息*/
	@Test
	public void getByUid(){
		try {
			Integer uid = 1;
			List<Address> addresses = iAddressService.getByUid(uid);	
			for(Address a : addresses) {
				System.err.println(a);
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}

	/**3. 将收货地址设置为默认*/
	@Test
	public void setDefault() {
		try {
			Integer aid = 10;
			Integer uid = 1;
			String username = "超级管理员";
			iAddressService.setDefault(aid, uid, username);
			System.err.println("ok");
		} catch (Exception e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	/**4. 删除收货地址信息*/
	@Test
	public void delete() {
		try {
			Integer aid = 1;
			Integer uid = 1;
			String username = "啦啦啦";
			iAddressService.delete(aid, uid, username);
			System.err.println("ok");
		} catch (Exception e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
}





















