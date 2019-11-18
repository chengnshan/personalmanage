package com.cxp.personalmanage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxp.personalmanage.mapper.postgresql.SystemParameterInfoMapper;
import com.cxp.personalmanage.mapper.postgresql.UserInfoMapper;
import com.cxp.personalmanage.pojo.SystemParameterInfo;
import com.cxp.personalmanage.pojo.consumer.ConsumeDetailInfo;
import com.cxp.personalmanage.service.ConsumeDetailInfoService;
import com.cxp.personalmanage.service.MailService;
import com.cxp.personalmanage.service.SystemParameterInfoService;
import com.cxp.personalmanage.service.UserInfoService;
import com.cxp.personalmanage.utils.DateTimeUtil;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TestRedisAll {

    @Autowired
    @Qualifier(value = "stringRedisTemplate")
    private RedisTemplate redisTemplate;

    @Autowired
    StringEncryptor stringEncryptor;//密码解码器自动注入

    @Autowired
    private MailService mailService;

    @Autowired
    private SystemParameterInfoService systemParameterInfoService;

    @Autowired
    private SystemParameterInfoMapper systemParameterInfoMapper;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private ConsumeDetailInfoService consumeDetailInfoService;


    @Value("${spring.mail.password}")
    private String mailPwd;

    @Test
    public void test1() throws Exception {
        System.out.println(stringEncryptor.encrypt("cheng3_shan3@163.com"));

//        mailService.sendMail("276629352@qq.com","这是springboot","这是springboot=====",
//                null,null);
    }

    @Test
    public void test2() throws Exception {
        List<SystemParameterInfo> list = systemParameterInfoService.list();
        System.out.println(list);

        System.out.println(userInfoService.list());

        Page<SystemParameterInfo> page = new Page<>(1,5);
        IPage<SystemParameterInfo> page1 = systemParameterInfoMapper.selectPage(page, null);
        System.out.println(page1.getTotal());

    }

    @Test
    public void test3(){
        Map<String, Object> param = new HashMap<>();
        param.put("endTime",DateTimeUtil.parse(DateTimeUtil.DATE_TIME_PATTERN, "2019-11-18" + " 00:00:00"));
        param.put("endTime", DateTimeUtil.parse(DateTimeUtil.DATE_TIME_PATTERN, "2019-11-18" + " 23:59:59"));
        List<ConsumeDetailInfo> map = consumeDetailInfoService.findConsumeDetailInfoListByMap(param);
        System.out.println(map);
    }
}
