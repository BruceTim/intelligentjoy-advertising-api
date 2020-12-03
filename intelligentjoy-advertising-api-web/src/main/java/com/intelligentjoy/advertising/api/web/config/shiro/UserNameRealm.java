package com.intelligentjoy.advertising.api.web.config.shiro;

import com.intelligentjoy.advertising.api.base.model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;

import java.util.Objects;

/**
 * 用户名及密码校验
 *
 * @author BruceTim
 * @date 2020-12-03
 */
public class UserNameRealm extends CustomRealm {

    @Override
    protected void initCredentialsMatcher() {
        this.setCredentialsMatcher((token, info) -> {
            UsernamePasswordToken utoken = (UsernamePasswordToken) token;
            //获得用户输入的密码:(可以采用加盐(salt)的方式去检验)
            String inPassword = new String(utoken.getPassword());
            String username = utoken.getUsername();
            //获得数据库中的密码
            String dbPassword = (String) info.getCredentials();
            String salt = userService.getUserByName(username).getSalt();
            inPassword = DigestUtils.sha256Hex(salt + inPassword + salt);
            //进行密码的比对
            return Objects.equals(inPassword, dbPassword);
        });
    }

    //定义如何获取用户信息的业务逻辑，给shiro做登录
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        // Null username is invalid
        if (username == null) {
            throw new AccountException("请输入用户名");
        }
        User userDB = userService.getUserByName(username);
        if (userDB == null) {
            throw new UnknownAccountException("用户不存在");
        }
        return getAuthenticationInfo(userDB);
    }

}
