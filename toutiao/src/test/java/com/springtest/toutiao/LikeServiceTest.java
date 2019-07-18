package com.springtest.toutiao;

import com.springtest.toutiao.service.LikeService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ToutiaoApplication.class)

public class LikeServiceTest {
    @Autowired
    LikeService likeService;
    @Test
    public void testLike(){
        //运行测试用例
        likeService.like(123,1,1);
        Assert.assertEquals(1,likeService.getLikeStatus(123,1,1));//
    }
    @Test
    public void testDisLike(){
        likeService.dislike(123,1,1);
        Assert.assertEquals(-1,likeService.getLikeStatus(123,1,1));//
    }
    @Before
    public void setUp(){
        //初始化数据
    }
    @After
    public void tearDown(){
        //清除数据
    }
    @BeforeClass
    public static void beforeClass(){

    }
    @AfterClass
    public static void afterClass(){}
    //测试异常
    @Test(expected = IllegalArgumentException.class)
    public void testException(){

    }
}
