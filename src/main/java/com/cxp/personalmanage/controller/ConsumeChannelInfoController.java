package com.cxp.personalmanage.controller;

import com.cxp.personalmanage.common.Constant;
import com.cxp.personalmanage.controller.base.BaseController;
import com.cxp.personalmanage.pojo.consumer.ConsumeChannelInfo;
import com.cxp.personalmanage.service.ConsumeChannelInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping(value = "/consumeChannel")
@Slf4j
public class ConsumeChannelInfoController extends BaseController {

    @Autowired
    private ConsumeChannelInfoService consumeChannelInfoService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping(value = "/findConsumeChannelInfoList")
    public String findConsumeChannelInfoList(ConsumeChannelInfo consumeChannelInfo){
        log.info("findConsumeChannelInfoList method in param : "+consumeChannelInfo);
        String channelInfo = stringRedisTemplate.opsForValue().get(Constant.RedisCustomKey.CONSUMECHANNELKEY);
        if (StringUtils.isBlank(channelInfo)){
            List<ConsumeChannelInfo> consumeChannelList = consumeChannelInfoService.findConsumeChannelList(consumeChannelInfo);
            channelInfo = buildSuccessResultInfo(consumeChannelList);
            stringRedisTemplate.opsForValue().set(Constant.RedisCustomKey.CONSUMECHANNELKEY,channelInfo,
                    3600L, TimeUnit.SECONDS);
        }
        log.info("findConsumeChannelInfoList out param : "+channelInfo);
        return channelInfo;
    }
}
