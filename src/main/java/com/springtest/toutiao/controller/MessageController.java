package com.springtest.toutiao.controller;

import com.springtest.toutiao.model.HostHolder;
import com.springtest.toutiao.model.Message;
import com.springtest.toutiao.model.User;
import com.springtest.toutiao.model.ViewObject;
import com.springtest.toutiao.service.MessageService;
import com.springtest.toutiao.service.UserService;
import com.springtest.toutiao.util.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
    @Autowired
    MessageService messageService;
    @Autowired
    UserService userService;
    @Autowired
    HostHolder hostHolder;

    @RequestMapping(path = {"/msg/addMessage"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam("fromId") int fromId,
                             @RequestParam("toId") int toId,
                             @RequestParam("content") String content) {
        try {
            Message message = new Message();
            message.setContent(content);
            message.setFromId(fromId);
            message.setToId(toId);
            message.setCreatedDate(new Date());
            message.setHasRead(1);
            message.setConversationId(fromId < toId ? String.format("%d_%d", fromId, toId) : String.format("%d_%d", toId, fromId));
            messageService.addMessage(message);
            return ToutiaoUtil.getJSONString(0, String.valueOf(message.getId()));
        } catch (Exception e) {
            logger.error("添加信息错误" + e.getMessage());
            return ToutiaoUtil.getJSONString(1, "添加信息失败");
        }
    }

    @RequestMapping(path = {"/msg/detail"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String conversationDetail(@RequestParam("conversationId") String conversationId, Model model) {
        try {
            List<Message> conversationList = messageService.getConversationDetail(conversationId, 0, 10);
            List<ViewObject> messages = new ArrayList<>();
            for (Message message : conversationList) {
                ViewObject vo = new ViewObject();
                vo.set("message", message);
                User user = userService.getUser(message.getFromId());
                if (user == null)
                    continue;
                vo.set("headUrl", user.getHeadUrl());
                vo.set("userId", user.getId());
                messages.add(vo);
            }
            model.addAttribute("messages", messages);
        } catch (Exception e) {
            logger.error("获取详情消息失败" + e.getMessage());
        }
        return "letterDetail";
    }

    @RequestMapping(path = {"/msg/conversationList"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String conversationDetail(Model model) {
        try {
            int localUserId = hostHolder.getUser().getId();
            System.out.println(localUserId);
            List<Message> conversationList = messageService.getConversationList(localUserId, 0, 10);
            List<ViewObject> conversations = new ArrayList<>();
            for (Message message : conversationList) {
                ViewObject vo = new ViewObject();
                vo.set("conversation", message);
                int targetId = message.getFromId() == localUserId ? message.getToId() : message.getFromId();
                User user = userService.getUser(targetId);
                vo.set("user", user);
                vo.set("unread", messageService.getConversationUnreadCount(localUserId, message.getConversationId()));
                conversations.add(vo);
            }
            System.out.println(((Message) conversations.get(2).get("conversation")).getConversationId());
            System.out.println(((User) conversations.get(2).get("user")).getId());
            model.addAttribute("conversations", conversations);
        } catch (Exception e) {
            logger.error("获取站内信列表失败" + e.getMessage());
        }
        return "letter";
    }
}
