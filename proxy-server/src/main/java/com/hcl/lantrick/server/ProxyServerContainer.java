package com.hcl.lantrick.server;

import com.hcl.common.container.Container;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: hechenglo03
 * @Date:2019/9/23
 * @Description:
 */
public class ProxyServerContainer implements Container{

    private Logger logger = LoggerFactory.getLogger(ProxyServerContainer.class);

    private NioEventLoopGroup serverBossGroup;

    private NioEventLoopGroup serverWokerGroup;

    public ProxyServerContainer(){
        this.serverBossGroup = new NioEventLoopGroup();
        this.serverWokerGroup = new NioEventLoopGroup();
    }

    public void stop() {
    }

    public void start() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(serverBossGroup,serverWokerGroup)
                .channel(NioServerSocketChannel.class)
    }


}
