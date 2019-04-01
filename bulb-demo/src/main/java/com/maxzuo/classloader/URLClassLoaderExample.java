package com.maxzuo.classloader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * URLClassLoader的使用
 * <p>
 * Created by zfh on 2019/04/01
 */
class URLClassLoaderExample {

    @DisplayName("测试加载jar文件")
    @Test
    void testLoadJarFile () {
        final List<URL> jarURLList = new ArrayList<>();

        String agentLibPath = "F:\\bulb\\bulb-agent\\target\\lib";
        File agentDirectory = new File(agentLibPath);
        File[] findJarList = agentDirectory.listFiles();
        if (findJarList != null) {
            for (File file : findJarList) {
                URI uri = file.toURI();
                try {
                    jarURLList.add(uri.toURL());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }
        URLClassLoader urlLoader = new URLClassLoader(jarURLList.toArray(new URL[0]), null);
        System.out.println(Arrays.toString(urlLoader.getURLs()));

        try {
            Class.forName("");

            Class<?> aClass = urlLoader.loadClass("com.maxzuo.agent.model.Thuesday");
            System.out.println("ClassLoader：" + aClass.getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @DisplayName("测试URI和URL")
    @Test
    void testFileUri () {
        File file = new File("F:/bulb/bulb-agent/target/lib/byte-buddy-1.7.11.jar");
        URI uri = file.toURI();
        System.out.println("scheme: " + uri.getScheme());
        System.out.println("host: " + uri.getHost());
        System.out.println("path: " + uri.getPath());

        try {
            URL url = uri.toURL();
            System.out.println("getProtocol(): " + url.getProtocol());
            System.out.println("getPath(): " + url.getPath());
            System.out.println("getHost()：" + url.getHost());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
