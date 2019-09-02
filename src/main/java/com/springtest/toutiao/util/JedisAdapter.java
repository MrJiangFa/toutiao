package com.springtest.toutiao.util;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ListPosition;
import redis.clients.jedis.Tuple;

import java.util.List;

@Service
public class JedisAdapter implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);
    private JedisPool pool = null;

    public static void commonUtils(){
        Jedis jedis = new Jedis();
        jedis.flushAll();//全部删除
        jedis.set("hello", "world");
        //设置过期时间
        jedis.setex("hello2", 12, "world");
        jedis.set("pv", "100");
        jedis.incr("pv");
        //数值实时刷新，网页火爆，临时存储，周期同步
        print(2, jedis.get("pv"));
        jedis.incrBy("pv", 5);
        print(2, jedis.get("pv"));

        //列表操作，双向列表
        String listName = "list";
        for (int i = 0; i < 10; i++) {
            jedis.lpush(listName, "a" + String.valueOf(i));//leftpush
        }
        print(3, jedis.lrange(listName, 0, 12));
        print(5, jedis.llen(listName));
        print(6, jedis.lpop(listName));
        print(7, jedis.llen(listName));
        print(8, jedis.linsert(listName, ListPosition.AFTER, "a4", "xx"));
        print(9, jedis.lrange(listName, 0, 12));

        //jedis对于不定属性，将临时属性表示出来
        String userKey = "userxx";
        jedis.hset(userKey, "name", "jin");//hashset
        jedis.hset(userKey, "age", "12");
        print(10, jedis.hget(userKey, "name"));
        print(11, jedis.hgetAll(userKey));
        jedis.hdel(userKey, "age");
        print(12, jedis.hgetAll(userKey));//删除临时属性
        print(13, jedis.hkeys(userKey));
        print(14, jedis.hexists(userKey, "email"));
        jedis.hsetnx(userKey, "name", "jj");//首先判断是否存在"name"这个字段，如果存在不变，如果不存在则加上
        print(16, jedis.hgetAll(userKey));

        String likeKey1 = "newsLike1";
        String likeKey2 = "newsLike2";
        for (int i = 0; i < 10; ++i) {
            jedis.sadd(likeKey1, String.valueOf(i));
            jedis.sadd(likeKey2, String.valueOf(2 * i));
        }
        print(17, jedis.smembers(likeKey1));
        print(18, jedis.smembers(likeKey2));
        print(19, jedis.sinter(likeKey1, likeKey2));
        print(20, jedis.sunion(likeKey1, likeKey2));
        print(21, jedis.sdiff(likeKey1, likeKey2));//我有你没有，共同好友
        jedis.srem(likeKey1, "5");//setremove
        print(22, jedis.smembers(likeKey1));
        print(23, jedis.scard(likeKey1));//元素个数

        //排行榜
        String rankKey = "ranKey";
        jedis.zadd(rankKey, 15, "jim");
        jedis.zadd(rankKey, 30, "jj");
        jedis.zadd(rankKey, 89, "li");
        jedis.zadd(rankKey, 90, "lili");
        print(24, jedis.zcard(rankKey));//多少人
        print(25, jedis.zcount(rankKey, 29, 90));//区间之内有多少人
        print(26, jedis.zscore(rankKey, "jj"));
        print(27, jedis.zrange(rankKey, 1, 3));//倒序排列
        print(28, jedis.zrevrange(rankKey, 1, 3));//正序排列
        for (Tuple tuple : jedis.zrangeByScoreWithScores(rankKey, 0, 100)) {
            print(29, tuple.getElement() + ":" + String.valueOf(tuple.getScore()));
        }
        print(30, jedis.zrank(rankKey, "li"));//某个人的排名

        JedisPool pool = new JedisPool();
        for (int i = 0; i < 100; ++i) {
            Jedis j = pool.getResource();
            j.get("a");
            System.out.println("pool" + i);
            j.close();
        }
    }

    public static void main(String[] args) {

    }

    public static void print(int index, Object obj) {
        System.out.println(String.format("%d,%s", index, obj.toString()));
    }

    //初始化bean
    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool("localhost", 6379);
    }

    public long sadd(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sadd(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
            return 0;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public long srem(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.srem(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
            return 0;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public boolean sismember(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sismember(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
            return false;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public long scard(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.scard(key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
            return 0;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public void set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.set(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public void setObject(String key, Object object) {
        set(key, JSON.toJSONString(object));//将对象序列化
    }

    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.get(key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public <T> T getObject(String key, Class<T> clazz) {
        String value = get(key);
        if (value != null) {
            return JSON.parseObject(value, clazz);
        }
        return null;
    }

    /**
     * @param key：对应列表的key
     * @param value：即将插入list中的值
     * @return
     */
    public long lpush(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.lpush(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
            return 0;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public List<String> brpop(int timeOut, String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.brpop(timeOut, key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

}
