package com.maxzuo.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Socket进行http请求
 * <p>
 * Created by zfh on 2019/07/03
 */
public class SocketHttpRequestExample {

    private static final Logger logger = LoggerFactory.getLogger(SocketHttpRequestExample.class);

    private Socket socket;

    public static void main(String[] args) {
        SocketHttpRequestExample client = new SocketHttpRequestExample();
        client.createConnect();
        client.request();
    }

    /**
     * 建立连接
     */
    private void createConnect () {
        try {
            // socket = new Socket("www.baidu.com", 80);
            socket = SSLSocketFactory.getDefault().createSocket("www.baidu.com", 443);
        } catch (IOException e) {
            logger.info("连接异常！", e);
        }
    }

    /**
     * 发送请求
     */
    private void request () {
        StringBuilder stringBuilder = new StringBuilder();
        // 请求行
        stringBuilder.append("GET / HTTP/1.1\r\n");
        // 请求头
        stringBuilder.append("Host: www.baidu.com\r\n");
        stringBuilder.append("User-Agent: curl/7.54.0\r\n");
        stringBuilder.append("Accept: */*\r\n");
        // TODO：不维持连接
        stringBuilder.append("Connection: close\r\n");
        // 空行
        stringBuilder.append("\r\n");

        try {
            OutputStream os = socket.getOutputStream();
            os.write(stringBuilder.toString().getBytes());
            os.flush();

            // 请求响应
            StringBuilder response = new StringBuilder();

            InputStream is = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len;
            // TODO: read 阻塞 !!! Connection: Keep-Alive
            while ((len = is.read(bytes)) != -1) {
                response.append(new String(bytes, 0, len));
            }
            System.out.println("response: " + response);
            socket.close();
        } catch (Exception e) {
            logger.info("请求异常！", e);
        }
    }
}
