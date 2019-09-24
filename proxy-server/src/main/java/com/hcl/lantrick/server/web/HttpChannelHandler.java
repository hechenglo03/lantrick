package com.hcl.lantrick.server.web;

import com.sun.org.apache.regexp.internal.RE;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.rtsp.RtspHeaders;
import io.netty.handler.stream.ChunkedNioFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * hechenglong
 * 2019/9/24
 */
public class HttpChannelHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private Logger logger = LoggerFactory.getLogger(HttpChannelHandler.class);

    private static final String FOLDER_PATH = System.getProperty("app.home",System.getProperty("user.dir")) +"/proxy-server/src/main/resource";

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        if(msg.getMethod().equals(HttpMethod.GET)){
            outputPages(ctx, msg);
            return;
        }

        ResponseInfo responseInfo = ApiRoute.run(msg);
        outputContent(ctx,responseInfo.getCode()/100,msg,"","Application/json;charset=utf-8");
    }

    private void outputPages(ChannelHandlerContext ctx,FullHttpRequest request) throws Exception {
        HttpResponseStatus code = HttpResponseStatus.OK;
        URI uri = new URI(request.getUri());
        String uriPath = uri.getPath();
        //设置访问为http://ip:port/index也是可以的
        uriPath = uriPath.equals("/index") ? "/index.html":uriPath;
        String filePath = FOLDER_PATH + uriPath;
        File file = new File(filePath);

        if(!file.exists()){
            HttpResponseStatus status = HttpResponseStatus.NOT_FOUND;
            outputContent(ctx,status.code(),request,status.toString(),"text/html");
            return;
        }

        RandomAccessFile raf = null;
        raf = new RandomAccessFile(file,"r");

        HttpResponse response = new DefaultHttpResponse(request.getProtocolVersion(),HttpResponseStatus.OK);
        response.headers().add(HttpHeaders.Names.CONTENT_TYPE,"text/html");
        response.headers().add(HttpHeaders.Names.CONTENT_LENGTH,raf.length());

        //判断是否保持连接
        if(HttpHeaders.isKeepAlive(request)){
            response.headers().add(HttpHeaders.Names.CONNECTION,HttpHeaders.Values.KEEP_ALIVE);
        }

        ctx.write(response);
        DefaultFileRegion defaultFileRegion = new DefaultFileRegion(raf.getChannel(),0,raf.length());
        ctx.write(defaultFileRegion);
        ChannelFuture channelFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
        if(!HttpHeaders.isKeepAlive(request)){
            channelFuture.addListener(ChannelFutureListener.CLOSE);
        }

    }

    private void outputContent(ChannelHandlerContext ctx,int status,FullHttpRequest request,String content,String contentType) throws Exception {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.valueOf(status),
                Unpooled.wrappedBuffer(content.getBytes("utf-8")));
        response.headers().add(HttpHeaders.Names.CONTENT_TYPE,contentType);
        response.headers().add(HttpHeaders.Names.CONTENT_LENGTH,response.content().readableBytes());
        ChannelFuture future = ctx.writeAndFlush(response);
        if(!HttpHeaders.isKeepAlive(request)){
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }


}
