package com.hcl.lantrick.protocol;

/**
 * @Author: hechenglo03
 * @Date:2019/9/23
 * @Description:
 */
public class ProxyMessage {

    //心跳的消息
    public static final byte TYPE_HEARTBEAT = 0x07;

    //认证消息
    public static final byte C_TYPE_AUTH = 0x01;

    //连接请求消息
    public static final byte TYPE_CONNECT = 0x03;

    //断开连接请求消息
    public static final byte TYPE_DISCONNECT = 0x04;

    //数据传输消息
    public static final byte TYPE_TRANSFER = 0x05;

    private byte type;

    private Long serialNumber;

    private String uri;

    private byte[] data;

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public Long getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Long serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("消息类型为:").append(String.valueOf(this.type));
        builder.append("消息请求信息:").append(this.uri);
        builder.append("消息内容:"+new String(this.data));
        return builder.toString();
    }
}
