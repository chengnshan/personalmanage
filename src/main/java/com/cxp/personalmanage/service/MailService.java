package com.cxp.personalmanage.service;

import java.io.ByteArrayOutputStream;

/**
 * @author : cheng
 * @date : 2019-11-08 21:53
 */
public interface MailService {
    /**
     * 发送邮件
     * @param to 目的地
     * @param subject 主题
     * @param content 内容
     * @param os 附件
     * @param attachmentFilename 附件名
     * @throws Exception
     */
    public void sendMail(String to, String subject, String content, ByteArrayOutputStream os,
                         String attachmentFilename) throws Exception;
}
