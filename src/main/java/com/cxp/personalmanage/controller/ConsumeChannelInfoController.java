package com.cxp.personalmanage.controller;

import com.cxp.personalmanage.common.Constant;
import com.cxp.personalmanage.controller.base.BaseController;
import com.cxp.personalmanage.pojo.consumer.ConsumeChannelInfo;
import com.cxp.personalmanage.service.ConsumeChannelInfoService;
import com.cxp.personalmanage.utils.JackJsonUtil;
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
        String channelInfos = stringRedisTemplate.opsForValue().get(Constant.RedisCustomKey.CONSUMECHANNELKEY);
        if (StringUtils.isBlank(channelInfos)){
            List<ConsumeChannelInfo> consumeChannelList = consumeChannelInfoService.findConsumeChannelList(consumeChannelInfo);
            String toString = JackJsonUtil.objectToString(consumeChannelList);
            stringRedisTemplate.opsForValue().set(Constant.RedisCustomKey.CONSUMECHANNELKEY,toString,
                    3600L, TimeUnit.SECONDS);

            channelInfos = buildSuccessResultInfo(consumeChannelList);
        }
        log.info("findConsumeChannelInfoList out param : " + channelInfos);
        return channelInfos;
    }
}
