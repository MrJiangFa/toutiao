package com.springtest.toutiao;

import com.springtest.toutiao.model.User;
import com.springtest.toutiao.util.JedisAdapter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JedisTest {
    @Autowired
    JedisAdapter jedisAdapter;

    @Test
    public void testObject(){
        User user  = new User();
        user.setHeadUrl("http://image.nowcoder.com/head/100t.png");
        user.setName("userl");
        user.setPassword("pwd");
        user.setSalt("salt");
        jedisAdapter.setObject("userlxx",user);
        User u = jedisAdapter.getObject("userlxx",User.class);
        System.out.println(ToStringBuilder.reflectionToString(u));

    }
}
