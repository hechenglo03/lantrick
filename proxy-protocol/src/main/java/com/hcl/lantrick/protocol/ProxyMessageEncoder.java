package com.hcl.lantrick.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author: hechenglo03
 * @Date:2019/9/23
 * @Description:
 */
public class ProxyMessageEncoder extends MessageToByteEncoder<ProxyMessage> {

    private static final int SERIAL_LENGTH = 8;

    private static final int URI_LENGTH = 1;

    private static final int TYPE_SIZE = 1;

    protected void encode(ChannelHandlerContext ctx, ProxyMessage msg, ByteBuf out) throws Exception {
        int bodyLength = SERIAL_LENGTH + URI_LENGTH + TYPE_SIZE;
        byte[] data = null;

        //计算 uri的长度
        if(StringUtils.isNotEmpty(msg.getUri())){
            data = msg.getUri().getBytes();
            bodyLength += data.length;
        }

        //计算 data的长度
        if(msg.getData() != null){
            bodyLength += msg.getData().length;
        }

        out.writeInt(bodyLength);
        out.writeByte(msg.getType());
        out.writeLong(msg.getSerialNumber());
        if(data != null){
            out.writeByte((byte)data.length);
            out.writeBytes(data);
        }else{
            out.writeByte((byte) 0);
        }

        if(msg.getData() != null){
            out.writeBytes(msg.getData());
        }


    }
}
