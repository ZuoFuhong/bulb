package com.maxzuo.bytebuddy.model;

/**
 * Created by zfh on 2019/01/29
 */
public interface ISlow {

    default String m() {
        return "Islow";
    }
}
