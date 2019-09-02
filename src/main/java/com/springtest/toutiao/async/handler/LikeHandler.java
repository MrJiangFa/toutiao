package com.springtest.toutiao.async.handler;

import com.springtest.toutiao.async.EventHandler;
import com.springtest.toutiao.async.EventModel;
import com.springtest.toutiao.async.EventType;
import com.springtest.toutiao.model.Message;
import com.springtest.toutiao.model.User;
import com.springtest.toutiao.service.MessageService;
import com.springtest.toutiao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class LikeHandler implements EventHandler {
    @Resource(name = "userService")
    @Autowired
    @Qualifier("userService")
    UserService userService;
    @Autowired
    MessageService messageService;

    @Override
    public void doHandle(EventModel model) {
        Message message = new Message();
        User user = userService.getUser(model.getActorId());
        int fromId = user.getId();
        int toId = model.getEntityOwnerId();
        message.setFromId(fromId);
        message.setToId(toId);
        message.setContent("用户" + user.getName() + "赞了你");
        message.setCreatedDate(new Date());
        message.setConversationId(fromId < toId ? String.format("%d_%d", fromId, toId) : String.format("%d_%d", toId, fromId));
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupprotEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
