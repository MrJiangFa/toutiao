package com.springtest.toutiao.async;

import com.alibaba.fastjson.JSONObject;
import com.springtest.toutiao.util.JedisAdapter;
import com.springtest.toutiao.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EventProducer {
    /**
     * 此类主要用于发送事件，将其序列化，然后发送到一个redis的队列中
     */
    @Autowired
    JedisAdapter jedisAdapter;

    public boolean fireEvent(EventModel model) {
        try {
            String json = JSONObject.toJSONString(model);
            String key = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key,json);
            System.out.println(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
