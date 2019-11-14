package com.cxp.personalmanage.config.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

import java.nio.charset.Charset;


/**
 * @author 程
 * @date 2019/7/9 下午12:40
 */
public class TextMessageConverter implements MessageConverter {

    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
        System.out.println("TextMessageConverter toMessage !");
        return new Message(object.toString().getBytes(),messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        System.out.println("TextMessageConverter fromMessage!");
        return new String(message.getBody(),Charset.forName("UTF-8"));
    }
}
