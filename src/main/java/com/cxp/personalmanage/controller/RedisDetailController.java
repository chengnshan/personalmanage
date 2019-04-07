package com.cxp.personalmanage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/redis")
public class RedisDetailController {

    @Autowired
    @Qualifier(value = "stringRedisTemplate")
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    @Qualifier(value = "redisTemplate")
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "/queryRedisDetail",method = {RequestMethod.POST,RequestMethod.GET})
    public String queryRedisDetail(String redisKey){
        String value = stringRedisTemplate.opsForValue().get(redisKey);
        return null;
    }
}
