package com.springtest.toutiao;

import com.springtest.toutiao.dao.CommentDao;
import com.springtest.toutiao.dao.LoginTicketDao;
import com.springtest.toutiao.dao.NewsDao;
import com.springtest.toutiao.dao.UserDao;
import com.springtest.toutiao.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("/init-schema.sql")
public class InitDatabaseTests {
    @Autowired
    UserDao userDao;
    @Autowired
    NewsDao newsDao;
    @Autowired
    LoginTicketDao loginTicketDao;
    @Autowired
    CommentDao commentDao;


    @Test
    public void contextLoads() {
        Random random = new Random();
        for (int i = 0; i < 11; ++i) {
            User user = new User();
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
            user.setName(String.format("USRE%d", i));
            user.setPassword(Integer.toString(i));
            user.setSalt(" ");
            userDao.addUser(user);

            News news = new News();
            news.setCommentCount(i);
            Date date = new Date();
            date.setTime(date.getTime() + 1000 * 3600 * 5);
            news.setCreatedDate(date);
            news.setImage(String.format("http://images.nowcoder.com/head/%dm.png", random.nextInt(1000)));
            news.setLikeCount(i + 1);
            news.setUserId(i + 1);
            news.setTitle(String.format("TITLE{%d}", i));
            news.setLink(String.format("http://www.nowcoder.com/%dt.html", i));
            newsDao.addNews(news);

            for(int j = 0;j<3;++j){
                Comment comment = new Comment();
                comment.setUserId(i+1);
                //每个资讯相应的都有三条评论
                comment.setEntityId(news.getId());
                comment.setEntityType(EntityType.ENTITY_NEWS);
                comment.setStatus(0);
                comment.setCreatedDate(new Date());
                comment.setContent(String.format("Comment %d",j));
                commentDao.addComment(comment);
            }

            LoginTicket loginTicket = new LoginTicket();
            loginTicket.setStatus(0);
            loginTicket.setUserId(i + 1);
            loginTicket.setExpired(date);
            loginTicket.setTicket(String.format("Ticket%d", i + 1));
            loginTicketDao.addTicket(loginTicket);
            loginTicketDao.updateStatus(loginTicket.getTicket(), 2);
        }
        newsDao.updateCommentCount(1,7);
    }
}

