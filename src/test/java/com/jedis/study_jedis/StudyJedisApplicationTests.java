package com.jedis.study_jedis;

import com.jedis.study_jedis.entity.User;
import com.jedis.study_jedis.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@SpringBootTest
class StudyJedisApplicationTests {
    @Autowired
    UserServiceImpl service;
    @Autowired
    JedisPool jedisPool;
    @Test
    void contextLoads() {
        //获取连接
        Jedis resource = jedisPool.getResource();
        resource.set("aname","xiaohong");
        String aname = resource.get("aname");
        System.out.println(aname);
    }

    /**
     * 第一次查询的数据是在数据库中进行查询
     * 第二次查询数据在缓存中进行查询
     */
    @Test
    void m2(){
       String key= "username";
        String string = service.getString(key);
        System.out.println(string);
    }
    @Test
    void m3(){
        String id ="1";
        User user = service.getUser(id);
        System.out.println(user);
    }

}
