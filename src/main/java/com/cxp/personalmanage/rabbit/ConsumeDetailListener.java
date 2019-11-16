package com.cxp.personalmanage.rabbit;

import com.cxp.personalmanage.pojo.consumer.ConsumeDetailInfo;
import com.cxp.personalmanage.service.ConsumeDetailInfoService;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * @author : cheng
 * @date : 2019-11-16 12:25
 */
@Component
@ConditionalOnProperty(prefix = "spring.rabbitmq",name = {"enable"},havingValue = "true", matchIfMissing = true)
public class ConsumeDetailListener {

    private static final Logger logger = LoggerFactory.getLogger(ConsumeDetailListener.class);

    @Autowired
    @Qualifier(value = "consumeDetailInfoService")
    private ConsumeDetailInfoService consumeDetailInfoService;

    @PostConstruct
    public void init(){
        logger.info("初始化成功: {}", this.getClass().getSimpleName());
    }

    @RabbitListener(queues ={"consumeDetail_queue"} )
    public String handlerConsumeDetail(ConsumeDetailInfo consumeDetailInfo, Channel channel, Message message){
        try {
            /**
             * prefetchCount限制每个消费者在收到下一个确认回执前一次可以最大接受多少条消息,通过basic.qos方法
             * 设置prefetch_count=1,这样RabbitMQ就会使得每个Consumer在同一个时间点最多处理一个Message
             */
//            channel.basicQos(1);

            int num = consumeDetailInfoService.saveConsumeDetail(consumeDetailInfo);
            /**
             * 确认消息已经消费成功
             * 第一个参数 deliveryTag：就是接受的消息的deliveryTag,可以通过msg.getMessageProperties().getDeliveryTag()获得
             * 第二个参数 multiple：如果为true，确认之前接受到的消息；如果为false，只确认当前消息。
             */
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            return String.valueOf(num);
        } catch (Exception e) {
            logger.error("MQ消息处理异常，消息ID：{}，消息体:{}",
                    message.getMessageProperties().getCorrelationIdString(),
                    consumeDetailInfo.toString(),e);
            try {
                //异常消息,拒绝消费消息（丢失消息） 给死信队列
                channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}
