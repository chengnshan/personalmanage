package com.cxp.personalmanage.controller.redis;

import com.cxp.personalmanage.controller.base.BaseController;
import com.cxp.personalmanage.pojo.dto.RedisKeyValueDto;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/redis")
public class RedisDetailController extends BaseController {

    @Autowired
    @Qualifier(value = "stringRedisTemplate")
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    @Qualifier(value = "redisTemplate")
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "/queryRedisDetail",method = {RequestMethod.POST,RequestMethod.GET})
    public String queryRedisDetail(String redisKey){
        RedisKeyValueDto redisKeyValueDto = new RedisKeyValueDto();

        Boolean hasKey = redisTemplate.hasKey(redisKey);
        if (hasKey){
            String value = stringRedisTemplate.opsForValue().get(redisKey);
            Long expire = stringRedisTemplate.getExpire(redisKey, TimeUnit.SECONDS);

            DataType type = redisTemplate.type(redisKey);

            redisKeyValueDto.setDataType(type.code());
            redisKeyValueDto.setKey(redisKey);
            redisKeyValueDto.setValue(value);
            redisKeyValueDto.setExpire(expire);
            if (StringUtils.isNotBlank(value)){
                return buildSuccessResultInfo(redisKeyValueDto);
            }
        }
        return buildFailedResultInfo(-1,"没有符合条件的数据!");
    }
}
