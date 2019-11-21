package com.cxp.personalmanage.config.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author : cheng
 * @date : 2019-11-20 15:36
 */
@Component
@EnableConfigurationProperties(NettyProperties.class)
@ConditionalOnProperty(prefix = "netty.server",name = "enable", havingValue = "true",matchIfMissing = true)
public class NettyServer {

    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    @Autowired
    private NettyProperties nettyProperties;

    @PostConstruct
    public void init(){
        logger.info("NettyServer start : {}",this.getClass().getName());
    }

    public void startNetty(){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup,workerGroup)
                //Server是NioServerSocketChannel 客户端是NioSocketChannel绑定了jdk NIO创建的ServerSocketChannel对象,
                //用它来建立新accept的连接
                .channel(NioServerSocketChannel.class)
                // 第2次握手服务端向客户端发送请求确认，同时把此连接放入队列A中，
                // 然后客户端接受到服务端返回的请求后，再次向服务端发送请求，表示准备完毕，此时服务端收到请求，把这个连接从队列A移动到队列B中，
                // 此时A+B的总数，不能超过SO_BACKLOG的数值，满了之后无法建立新的TCP连接,2次握手后和3次握手后的总数
                // 当服务端从队列B中按照FIFO的原则获取到连接并且建立连接[ServerSocket.accept()]后，B中对应的连接会被移除，这样A+B的数值就会变小
                //此参数对于程序的连接数没影响，会影响正在准备建立连接的握手。
                .option(ChannelOption.SO_BACKLOG, nettyProperties.getSoBacklog())
                .childHandler(new NettyServerInitializer());

        try {
            ChannelFuture channelFuture = bootstrap.bind(nettyProperties.getPort()).sync();
            logger.info("netty服务启动: [port:{}]",nettyProperties.getPort());
            // 等待服务器socket关闭
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error("startNetty InterruptedException exception : " + e.getMessage(),e);
        }finally {
             bossGroup.shutdownGracefully();
             workerGroup.shutdownGracefully();
        }
    }
}
