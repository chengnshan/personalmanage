package com.cxp.personalmanage.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : cheng
 * @date : 2019-11-13 20:39
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.rabbitmq",name = {"enable"},havingValue = "true", matchIfMissing = true)
public class RabbitmqExchangeConfig {

    /**
     *  FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
     HeadersExchange: 通过添加属性key-value匹配
     DirectExchange: 按照routingkey分发到指定队列
     TopicExchange: 多关键字匹配(*代表匹配一个字符，#代表匹配多个字符)
     */

    @Value(value = "${custom.rabbitmq.exchange.direct.consumeDetail}")
    private String consumeDetailDirectExchange;

    @Bean(name = "consumeDetailDirectExchange")
    public DirectExchange consumeDetailDirectExchange(){
        return new DirectExchange(consumeDetailDirectExchange,true,false, null);
    }

    @Value(value = "${custom.rabbitmq.queue.consumeDetail}")
    private String consumeDetailQueue;

    @Bean
    public Queue consumeDetailQueue(){
        /**

         * name: 队列名称
         * durable：队列是否持久化.false:队列在内存中,服务器挂掉后,队列就没了;
         *                      true:服务器重启后,队列将会重新生成.注意:只是队列持久化,不代表队列中的消息持久化!!!!
         * exclusive：队列是否专属,专属的范围针对的是连接,也就是说,一个连接下面的多个信道是可见的.
         *              对于其他连接是不可见的.连接断开后,该队列会被删除.注意,不是信道断开,是连接断开.
         *              并且,就算设置成了持久化,也会删除.
         * autoDelete：true, 如果所有消费者都断开连接了,是否自动删除.如果还没有消费者从该队列获取过消息或者监听该队列,
         *              那么该队列不会删除.只有在有消费者从该队列获取过消息后,
         *              该队列才有可能自动删除(当所有消费者都断开连接,不管消息是否获取完)
         * arguments：队列的配置,一共10个::
         *              Message TTL : 消息生存周期,单位毫秒
         *              Auto expire : 队列多长时间(毫秒)没有被使用(访问)就会被删除.换个说法就是,当队列在指定的时间内没有被使用(访问)就会被删除.
         *              Max length : 队列可以容纳的消息的最大条数
         *              Max length bytes : 队列可以容纳的消息的最大字节数,超过这个字节数,队列头部的消息将会被丢弃
         *              Overflow behaviour : 队列中的消息溢出后如何处理
         *              Dead letter exchange : 溢出的消息需要发送到绑定该死信交换机的队列
         *              Dead letter routing key : 溢出的消息需要发送到绑定该死信交换机,并且路由键匹配的队列
         *              Maximum priority : 最大优先级
         *              Lazy mode : 懒人模式
         *              Master locator :集群相关设置
         * */
        return new Queue(consumeDetailQueue, true,false, false, null);
    }

    @Value(value = "${custom.rabbitmq.routingKey.consumeDetail}")
    private String consumeDetailRoutingKey;

    @Bean
    public Binding consumeDetailBinding(){
        return BindingBuilder.bind(consumeDetailQueue()).to(consumeDetailDirectExchange()).with(consumeDetailRoutingKey);
    }
}
