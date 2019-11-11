package com.cxp.personalmanage;

import com.cxp.personalmanage.service.MailService;
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

    @Value("${spring.mail.password}")
    private String mailPwd;

    @Test
    public void test1() throws Exception {
        System.out.println(stringEncryptor.encrypt("cheng3_shan3@163.com"));

//        mailService.sendMail("276629352@qq.com","这是springboot","这是springboot=====",
//                null,null);
    }
}
