package com.maxzuo.bulb.im.common;

/**
 * 登录实体
 * <p>
 * Created by zfh on 2019/11/29
 */
public class LoginMessageDTO extends MessageDTO {

    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginMessageDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
