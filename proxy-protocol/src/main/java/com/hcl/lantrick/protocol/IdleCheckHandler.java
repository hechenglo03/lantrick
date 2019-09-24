package com.hcl.lantrick.protocol;

import io.netty.handler.timeout.IdleStateHandler;

/**
 * @Author: hechenglo03
 * @Date:2019/9/23
 * @Description:
 */
public class IdleCheckHandler extends IdleStateHandler {
    public IdleCheckHandler(int readerIdleTimeSeconds, int writerIdleTimeSeconds, int allIdleTimeSeconds) {
        super(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds);
    }
}
