package com.hcl.lantrick.server;

import com.hcl.common.container.Container;
import com.hcl.lantrick.server.web.HttpChannelHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * hechenglong
 * 2019/9/24
 */
public class HttpServerContainer implements Container {

    private NioEventLoopGroup serverWorkGroup;

    private NioEventLoopGroup bossWorkGroup;

    public HttpServerContainer(){
        this.bossWorkGroup = new NioEventLoopGroup();
        this.serverWorkGroup = new NioEventLoopGroup();
    }

    public void stop() {

    }

    public void start() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossWorkGroup,serverWorkGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>(){

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new HttpServerCodec());
                        pipeline.addLast(new HttpObjectAggregator(64 * 1024));
                        pipeline.addLast(new ChunkedWriteHandler());
                        pipeline.addLast(new HttpChannelHandler());
                    }
                });
        ChannelFuture channelFuture = serverBootstrap.bind("0.0.0.0",80);
        channelFuture.channel().closeFuture();

    }

    public static void main(String[] args){
        new HttpServerContainer().start();
    }
}
