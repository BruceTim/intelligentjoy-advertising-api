package com.intelligentjoy.advertising.api.web.config.shiro;

import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

import java.io.Serializable;

/**
 * 手机号及密码token
 *
 * @author BruceTim
 * @date 2020-12-03
 */
public class EmailPasswordToken implements HostAuthenticationToken, RememberMeAuthenticationToken, Serializable {

    private String email;

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
        return email;
    }

    @Override
    public Object getCredentials() {
        return this.password;
    }

    public EmailPasswordToken() {
    }

    public EmailPasswordToken(String email, String password) {
        this.email = email;
        this.password = password.toCharArray();
    }

    public EmailPasswordToken(String email, char[] password) {
        this.email = email;
        this.password = password;
    }

    public EmailPasswordToken(String email, String password, boolean rememberMe) {
        this.email = email;
        this.password = password.toCharArray();
        this.rememberMe = rememberMe;
    }

    public EmailPasswordToken(String email, char[] password, boolean rememberMe) {
        this.email = email;
        this.password = password;
        this.rememberMe = rememberMe;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
