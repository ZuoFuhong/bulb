package com.maxzuo.bytebuddy.model;

import java.util.Arrays;
import java.util.List;

/**
 * Created by zfh on 2019/01/29
 */
public class MemoryDatabase {

    public List<String> load(String var1) {
        return Arrays.asList("one", "two", var1);
    }
}
