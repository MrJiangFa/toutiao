package com.springtest.toutiao.service;

import com.springtest.toutiao.util.JedisAdapter;
import com.springtest.toutiao.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    @Autowired
    JedisAdapter jedisAdapter;

    /**
     * 如果喜欢返回1，如果不喜欢返回-1，否则返回0
     *
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    public int getLikeStatus(int userId, int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType);
        if (jedisAdapter.sismember(likeKey, String.valueOf(userId))) {
            return 1;
        }
        String disLikeKey = RedisKeyUtil.getDislikeKey(entityId, entityType);

        return jedisAdapter.sismember(disLikeKey, String.valueOf(userId)) ? -1 : 0;

    }

    public long like(int userId, int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType);
        System.out.println(likeKey);
        jedisAdapter.sadd(likeKey, String.valueOf(userId));
        String dislikeKey = RedisKeyUtil.getDislikeKey(entityId, entityType);
        jedisAdapter.srem(dislikeKey, String.valueOf(userId));
        return jedisAdapter.scard(likeKey);
    }

    public long dislike(int userId, int entityType, int entityId) {
        String dislikeKey = RedisKeyUtil.getDislikeKey(entityId, entityType);
        jedisAdapter.sadd(dislikeKey, String.valueOf(userId));
        String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType);
        jedisAdapter.srem(likeKey, String.valueOf(userId));
        return jedisAdapter.scard(likeKey);
    }
}
