package com.maxzuo.bulb.im.service;

import io.netty.channel.ChannelHandlerContext;

/**
 * Created by zfh on 2019/09/22
 */
public interface IHeartBeatHandler {

    /**
     * 处理客户端断线
     */
    boolean processDisconnect(ChannelHandlerContext ctx) throws Exception;
}
