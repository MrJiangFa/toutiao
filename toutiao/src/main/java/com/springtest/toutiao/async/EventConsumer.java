package com.springtest.toutiao.async;

import com.alibaba.fastjson.JSON;
import com.springtest.toutiao.util.JedisAdapter;
import com.springtest.toutiao.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EventConsumer implements InitializingBean, ApplicationContextAware {
    /**
     * 取队列中的数据，将其反序列化EventModel对象，找到对应的EventHandler进行处理
     */
    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);
    //对应的一个Event过来，对应要处理的Handler，然后一个个执行
    private Map<EventType, List<EventHandler>> config = new HashMap<>();

    private ApplicationContext applicationContext;

    @Autowired
    JedisAdapter jedisAdapter;

    @Override
    public void afterPropertiesSet() throws Exception {
        //依据EventHandler类型，获取spring容器中所有的EventHandler
        Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
        if (beans != null) {
            for (Map.Entry<String, EventHandler> entry : beans.entrySet()) {//遍历所有已经实现EventHandler的类
                //每一个EventHandler肯定要关注好几个事件
                List<EventType> eventTypes = entry.getValue().getSupprotEventTypes();
                //反过来关注每个Event事件有几个EventHandler对其进行处理，
                // 以后每过来一个Event，就有一个对应的Handler的处理链对其进行处理
                for (EventType type : eventTypes) {
                    if(!config.containsKey(type)){
                        config.put(type,new ArrayList<>());
                    }
                    config.get(type).add(entry.getValue());
                }
            }
        }
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String key = RedisKeyUtil.getEventQueueKey();
                    List<String> events = jedisAdapter.brpop(0, key);
                    for (String message : events) {
                        if (message.equals(key)) {
                            continue;
                        }
                        System.out.println(message);
                        EventModel eventModel = JSON.parseObject(message,EventModel.class);
                        System.out.println(eventModel.getEntityId());
                        System.out.println(eventModel.getActorId());
                        if (!config.containsKey(eventModel.getType())) {
                            logger.error("不能识别的事件");
                            continue;
                        }
                        for (EventHandler handler : config.get(eventModel.getType())) {
                            handler.doHandle(eventModel);
                        }
                    }
                }
            }
        });
        thread.start();
    }

    //获得所有实现了EventHandler接口的对象
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
