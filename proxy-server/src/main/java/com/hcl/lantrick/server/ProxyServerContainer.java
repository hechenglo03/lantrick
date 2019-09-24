package com.hcl.lantrick.server;

import com.hcl.common.container.Container;
import com.hcl.lantrick.server.config.ProxyConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import com.hcl.lantrick.protocol.ProxyMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: hechenglo03
 * @Date:2019/9/23
 * @Description:
 */
public class ProxyServerContainer implements Container{

    public static final

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
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new ProxyMessageDecoder());
                        pipeline.addLast(new )
                    }
                });
        ChannelFuture channelFuture = serverBootstrap.bind(ProxyConfig.getInstance().getServerIp(),
                ProxyConfig.getInstance().getServerPort());
        channelFuture.channel().closeFuture();

    }


}
