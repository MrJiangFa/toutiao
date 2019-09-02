package com.springtest.toutiao.controller;

import com.springtest.toutiao.async.EventConsumer;
import com.springtest.toutiao.async.EventModel;
import com.springtest.toutiao.async.EventProducer;
import com.springtest.toutiao.async.EventType;
import com.springtest.toutiao.model.EntityType;
import com.springtest.toutiao.model.HostHolder;
import com.springtest.toutiao.model.News;
import com.springtest.toutiao.service.LikeService;
import com.springtest.toutiao.service.NewsService;
import com.springtest.toutiao.util.ToutiaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LikeController {
    @Autowired
    HostHolder hostHolder;
    @Autowired
    LikeService likeService;
    @Autowired
    NewsService newsService;
    @Autowired
    EventProducer eventProducer;
    @Autowired
    EventConsumer eventConsumer;

    @RequestMapping(path = {"/like"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String like(@RequestParam("newsId") int newsId) {
        int userId = hostHolder.getUser().getId();
        News news=newsService.getById(newsId);
        System.out.println(userId);
        long likeCount = likeService.like(userId, EntityType.ENTITY_NEWS, newsId);
        System.out.println(likeCount);
        newsService.updatelikeCount(newsId, (int) likeCount);
        eventProducer.fireEvent(new EventModel(EventType.LIKE).
                setActorId(hostHolder.getUser().getId()).setEntityId(newsId).setEntityType(EntityType.ENTITY_NEWS).
                setEntityOwnerId(news.getUserId()).setExt("a","b"));//发出事件

        return ToutiaoUtil.getJSONString(0, String.valueOf(likeCount));
    }

    @RequestMapping(path = {"/dislike"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String disLike(@RequestParam("newsId") int newsId) {
        int userId = hostHolder.getUser().getId();
        long likeCount = likeService.dislike(userId, EntityType.ENTITY_NEWS, newsId);
        newsService.updatelikeCount(newsId, (int) likeCount);
        return ToutiaoUtil.getJSONString(0, String.valueOf(likeCount));
    }
}
