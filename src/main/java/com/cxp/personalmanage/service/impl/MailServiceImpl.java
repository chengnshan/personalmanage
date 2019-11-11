package com.cxp.personalmanage.service.impl;

import com.cxp.personalmanage.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;

/**
 * @author : cheng
 * @date : 2019-11-08 21:53
 */
@Service
@Slf4j
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${mail.fromMail.addr}")
    private String from;

    @Override
    public void sendMail(String to, String subject, String content, ByteArrayOutputStream os, String attachmentFilename) throws Exception {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content);
            if (null != os) {
                //附件
                InputStreamSource inputStream = new ByteArrayResource(os.toByteArray());
                helper.addAttachment(attachmentFilename, inputStream);
            }

            javaMailSender.send(message);
            log.info("简单邮件已经发送。");
        } catch (Exception e) {
            log.error("发送简单邮件时发生异常！", e);
            throw new Exception("发送简单邮件时发生异常！",e);
        }
    }

}
