package com.hcl.lantrick.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @Author: hechenglo03
 * @Date:2019/9/23
 * @Description:
 */
public class ProxyMessageDecoder extends LengthFieldBasedFrameDecoder {

    private static final byte HEADER  = 4;

    private static final byte SERIAL_NUMBER_SIZE = 8;

    private static final byte URI_LENGTH_SIZE = 1;

    public ProxyMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }

    public ProxyMessageDecoder(int maxFrameLength,
                        int lengthFieldOffset, int lengthFieldLength,
                        int lengthAdjustment, int initialBytesToStrip,boolean failFast){
        super(maxFrameLength,lengthFieldOffset,lengthFieldLength,lengthAdjustment,initialBytesToStrip,failFast);
    }

    @Override
    public ProxyMessage decode(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {
        ByteBuf in = (ByteBuf) super.decode(ctx,byteBuf);
        if(in == null){
            return null;
        }

        if(in.readableBytes() < HEADER){
            return null;
        }

        int frameLength = in.readInt();
        if(in.readableBytes() < frameLength){
            return null;
        }

        ProxyMessage proxyMessage = new ProxyMessage();
        byte type = in.readByte();
        long serialNumber = in.readLong();

        //读取请求信息的内容
        int uriLength = in.readByte();
        byte[] bytes = new byte[uriLength];
        in.readBytes(bytes);
        String uri = new String(bytes);

        byte[] data = new byte[frameLength - HEADER - SERIAL_NUMBER_SIZE - URI_LENGTH_SIZE - uriLength];
        in.readBytes(data);

        proxyMessage.setUri(uri);
        proxyMessage.setSerialNumber(serialNumber);
        proxyMessage.setType(type);
        proxyMessage.setData(data);
        return proxyMessage;

    }

}
