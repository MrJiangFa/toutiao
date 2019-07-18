package com.springtest.toutiao;

import com.springtest.toutiao.aoptest.Service.ProductService;
import com.springtest.toutiao.aoptest.Service.ProductServiceSon;
import com.springtest.toutiao.aoptest.domain.Product;
import com.springtest.toutiao.aoptest.security.CurrentUserHolder;
import com.springtest.toutiao.model.User;
import com.springtest.toutiao.service.UserService;
import com.springtest.toutiao.util.ToutiaoUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ToutiaoApplicationTests {
	@Autowired
	ProductService productService;

	@Autowired
	ProductServiceSon productServiceSon;

	@Autowired
	UserService userService;

	@Test
	public void testLogin(){
		User user = userService.getUser(25);
		System.out.println(user.getSalt());
		System.out.println(user.getPassword());
		System.out.println(ToutiaoUtil.MD5("1234567" + user.getSalt()));
		System.out.println(ToutiaoUtil.MD5("1234567" + user.getSalt()));
	}

	@Test
	public void annoInsertTest() {
		CurrentUserHolder.set("admin");
		productService.delete(1);//
		productService.insert(new Product());
		productServiceSon.append();
	}
}

