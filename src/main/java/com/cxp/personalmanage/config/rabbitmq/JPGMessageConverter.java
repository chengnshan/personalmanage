package com.cxp.personalmanage.config.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

/**
 * @author 程
 * @date 2019/7/9 下午12:43
 */
public class JPGMessageConverter implements MessageConverter {

    @Value(value = "${file.uploadFolder}")
    private String jpgFilePath;

    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
        return null;
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        System.out.println("JPGMessageConverter fromMessage : ====JPGMessageConverter====");
        System.out.println("JPGMessageConverter fromMessage : "+jpgFilePath);
        byte[] body = message.getBody();
        String fileName = UUID.randomUUID().toString();
        String path = jpgFilePath+fileName+".jpg";
        File file = new File(path);
        try{
            Files.copy(new ByteArrayInputStream(body),file.toPath());
        }catch (IOException e){
            e.printStackTrace();
        }
        return file;
    }
}
