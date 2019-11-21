package com.cxp.personalmanage.config.netty;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : cheng
 * @date : 2019-11-21 08:32
 */
@Data
@ToString
@ConfigurationProperties(prefix = "netty.server")
public class NettyProperties {

    private int port;
    private int bossThreadCount;
    private int workerThreadCount;
    private int soBacklog;

}
