package com.maxzuo.bulb.im.constant;

/**
 * Created by zfh on 2019/09/22
 */
public class Const {

    public static final String SERVER_NODE_PREFIX = "/bim/server";

    /**
     * 自定义报文类型
     */
    public static class CommandType{
        /**
         * 登录
         */
        public static final int LOGIN = 1 ;

        /**
         * 业务消息
         */
        public static final int MSG = 2 ;

        /**
         * ping
         */
        public static final int PING = 3 ;
    }
}
