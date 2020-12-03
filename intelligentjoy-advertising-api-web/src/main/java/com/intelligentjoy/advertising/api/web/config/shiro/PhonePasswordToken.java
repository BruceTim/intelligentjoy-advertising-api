package com.intelligentjoy.advertising.api.web.config.shiro;

import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

import java.io.Serializable;

/**
 * 手机号及密码token
 * @author BruceTim
 * @date 2020-12-03
 */
public class PhonePasswordToken implements HostAuthenticationToken, RememberMeAuthenticationToken, Serializable {

    private String phoneAreaCode;

    private String phone;

    private char[] password;

    private boolean rememberMe;

    private String host;

    @Override
    public String getHost() {
        return this.host;
    }

    @Override
    public boolean isRememberMe() {
        return this.rememberMe;
    }

    @Override
    public Object getPrincipal() {
        return phone;
    }

    @Override
    public Object getCredentials() {
        return this.password;
    }

    public PhonePasswordToken() {
    }

    public PhonePasswordToken(String phoneAreaCode, String phone, String password) {
        this.phoneAreaCode = phoneAreaCode;
        this.phone = phone;
        this.password = password.toCharArray();
    }

    public PhonePasswordToken(String phoneAreaCode, String phone, char[] password) {
        this.phoneAreaCode = phoneAreaCode;
        this.phone = phone;
        this.password = password;
    }

    public PhonePasswordToken(String phoneAreaCode, String phone, char[] password, boolean rememberMe) {
        this.phoneAreaCode = phoneAreaCode;
        this.phone = phone;
        this.password = password;
        this.rememberMe = rememberMe;
    }

    public PhonePasswordToken(String phoneAreaCode, String phone, char[] password, boolean rememberMe, String host) {
        this.phoneAreaCode = phoneAreaCode;
        this.phone = phone;
        this.password = password;
        this.rememberMe = rememberMe;
        this.host = host;
    }

    public String getPhoneAreaCode() {
        return phoneAreaCode;
    }

    public void setPhoneAreaCode(String phoneAreaCode) {
        this.phoneAreaCode = phoneAreaCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }
}
