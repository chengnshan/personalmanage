package com.cxp.personalmanage.utils;

import com.cxp.personalmanage.pojo.Student;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author : cheng
 * @date : 2019-11-13 21:24
 */
@Component
@ConditionalOnProperty(prefix = "spring.rabbitmq",name = {"enable"},havingValue = "true", matchIfMissing = true)
public class RabbitUtil {

    private static final Logger logger = LoggerFactory.getLogger(RabbitUtil.class);

    private static RabbitTemplate rabbitTemplate;

    @Autowired
    private void setRabbitTemplate(RabbitTemplate rabbitTemplate){
        RabbitUtil.rabbitTemplate = rabbitTemplate;
    }

    @PostConstruct
    public void init(){
        logger.info("初始化RabbitUtil成功");
    }

    /**
     * 发送一个对象到队列
     * @param exchange
     * @param routingKey
     * @param t
     * @param clazz
     * @param <T>
     */
    public static <T> Object sendObject(String exchange, String routingKey, T t ,Class<T> clazz){
        if (t == null){
            return null;
        }
        String jsonStr = JackJsonUtil.objectToString(t);

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().put("__TypeId__", clazz.getSimpleName());
        messageProperties.setContentType("application/json");
        Message message = new Message(jsonStr.getBytes(),messageProperties);
        Object receive = rabbitTemplate.convertSendAndReceive(exchange, routingKey,
                message, new CorrelationData(UUID.randomUUID().toString()));
        return receive;
    }

    public static <E> void sendList(String exchange, String routingKey, Collection<E> collection, Class<E> clazz){
        if (CollectionUtils.isEmpty(collection)){
            return;
        }
        String jsonStr = JackJsonUtil.objectToString(collection);

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        messageProperties.getHeaders().put("__TypeId__","java.util.List");
        messageProperties.getHeaders().put("__ContentTypeId__", clazz.getSimpleName());
        Message message = new Message(jsonStr.getBytes(),messageProperties);

        Object receive = rabbitTemplate.convertSendAndReceive(exchange, routingKey,
                message, new CorrelationData(UUID.randomUUID().toString()));


    }

    public static <K,V> void sendMap(String exchange, String routingKey, Map<K,V> map, Class<V> clazz){
        if (MapUtils.isEmpty(map)){
            return;
        }
        String jsonStr = JackJsonUtil.objectToString(map);

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        messageProperties.getHeaders().put("__TypeId__","java.util.Map");
        messageProperties.getHeaders().put("__KeyTypeId__","java.lang.String");
        messageProperties.getHeaders().put("__ContentTypeId__", clazz.getSimpleName());

        Message message = new Message(jsonStr.getBytes(),messageProperties);

        Object receive = rabbitTemplate.convertSendAndReceive(exchange, routingKey,
                message, new CorrelationData(UUID.randomUUID().toString()));

    }

    public static void main(String[] args) {
        List<Student> list = new ArrayList<Student>(){};
        Type genericSuperclass = list.getClass().getGenericSuperclass();
        String typeName = genericSuperclass.getTypeName();
        System.out.println(typeName);

        ParameterizedType pt = (ParameterizedType) genericSuperclass;
//        ParameterizedType listGenericType = (ParameterizedType) listField.getGenericType();
//		Type[] listActualTypeArguments = listGenericType.getActualTypeArguments();
//		System.out.println(listActualTypeArguments[listActualTypeArguments.length-1]);

        System.out.println(pt.getActualTypeArguments()[0]);
    }
}
