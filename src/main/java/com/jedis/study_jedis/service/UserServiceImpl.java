package com.jedis.study_jedis.service;

import com.jedis.study_jedis.entity.User;
import com.jedis.study_jedis.util.JedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl {
    @Autowired
    JedisUtils jedisUtils;
    public String getString(String key){
        Jedis jedis = jedisUtils.getJedis();
        //先从数据库中查询
        String result=null;
        if(jedis.exists(key)){
            System.out.println("从redis中进行查询");
            result = jedis.get(key);
            System.out.println(result);
        }else {
            //从数据库中查询r
            result="kangkang";
            //存入redis
            jedis.set(key,result);
            System.out.println("从数据库中查询");
            System.out.println(result);
        }
        jedisUtils.close(jedis);
        return result;
    }
    /**
     * 测试Hash
     */
    public User getUser(String id){
        String key ="user:"+id;
        Jedis jedis = jedisUtils.getJedis();
        User user =new User();
        //判断它的key值是否存在
        if(jedis.exists(key)){
            //存在从redis中获取
            log.info("从redis中进行查询");
            Map<String, String> map = jedis.hgetAll(key);
            String username = map.get("username");
            String password = map.get("password");
            user.setUsername(username);
            user.setPassword(password);
        }else {
            //从数据库中查询
            user.setUsername("wangzhe");
            user.setPassword("1234567");
            log.info("从数据库中进行查询");
            //准备一个Map集合、
            Map<String,String> map = new HashMap();
            map.put("username",user.getUsername());
            map.put("password",user.getPassword());
            //将数据存储到redis中
            jedis.hset(key,map);
        }
        jedisUtils.close(jedis);
        return user;
    }

}
