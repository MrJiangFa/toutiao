package com.springtest.toutiao.controller;

import com.springtest.toutiao.model.EntityType;
import com.springtest.toutiao.model.HostHolder;
import com.springtest.toutiao.model.News;
import com.springtest.toutiao.model.ViewObject;
import com.springtest.toutiao.service.LikeService;
import com.springtest.toutiao.service.NewsService;
import com.springtest.toutiao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

//spring mvc中注解了@Controller的类就称为控制器；
@Controller
public class HomeController {
    @Autowired
    UserService userService;
    @Autowired
    NewsService newsService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    LikeService likeService;

    private List<ViewObject> getNews(int userId, int offset, int limit) {
        List<News> newsList = newsService.getLatestNews(userId, offset, limit);
        int localUserId = hostHolder.getUser() != null ? hostHolder.getUser().getId() : 0;
        List<ViewObject> vos = new ArrayList<>();
        for (News news : newsList) {
            ViewObject vo = new ViewObject();
            vo.set("news", news);
            vo.set("user", userService.getUser(news.getUserId()));
            System.out.println(userService.getUser(news.getUserId()).getId());
            if (localUserId != 0) {
                vo.set("like", likeService.getLikeStatus(localUserId, EntityType.ENTITY_NEWS, news.getId()));
            } else {
                vo.set("like", 0);
            }
            vos.add(vo);
        }
        return vos;
    }

    // private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    //@RequestMapping 配置URL和方法之间的映射
    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET})
    public String index(Model model) {
//        logger.info("visit index");
        model.addAttribute("vos", getNews(0, 0, 10));
        return "home";
    }
    //控制器（@Controller）中的一个方法的返回值的字符串表示的就是试图（view)名；
    //spring mvc中有一个专门的类Model，用来和view之间进行数据交互；

    /**
     * 接收请求参数{userId};
     *
     * @param model
     * @param userId
     * @param pop
     * @return
     */
    @RequestMapping(path = {"/user/{userId}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String userIndex(Model model, @PathVariable("userId") int userId,
                            @RequestParam(value = "pop", defaultValue = "0") int pop) {
//        logger.info("visit index");
//        int userIdDup = Integer.parseInt(userId);
        model.addAttribute("vos", getNews(userId, 0, 10));
        model.addAttribute("pop", pop);
        return "home";
    }
}
