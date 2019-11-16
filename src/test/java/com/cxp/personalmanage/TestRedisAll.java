package com.cxp.personalmanage;

import com.cxp.personalmanage.mapper.postgresql.UserInfoMapper;
import com.cxp.personalmanage.pojo.SystemParameterInfo;
import com.cxp.personalmanage.service.ConsumeDetailInfoService;
import com.cxp.personalmanage.service.MailService;
import com.cxp.personalmanage.service.SystemParameterInfoService;
import com.cxp.personalmanage.service.UserInfoService;
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

import java.util.List;

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

        System.out.println(consumeDetailInfoService.list());
    }
}
