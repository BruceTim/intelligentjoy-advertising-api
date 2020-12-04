package com.intelligentjoy.advertising.api.web.config.shiro;

import com.intelligentjoy.advertising.api.base.model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * 手机号及密码校验
 *
 * @author BruceTim
 * @date 2020-12-03
 */
public class EmailPasswordRealm extends CustomRealm {

    @Override
    public String getName() {
        return EmailPasswordRealm.class.getName();
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof EmailPasswordToken;
    }

    @Override
    protected void initCredentialsMatcher() {
        //设置用于匹配密码的CredentialsMatcher
        this.setCredentialsMatcher((authenticationToken, authenticationInfo) -> {
            EmailPasswordToken token = (EmailPasswordToken) authenticationToken;
            //获得用户输入的密码:(可以采用加盐(salt)的方式去检验)
            String inPassword = new String(token.getPassword());
            //获得数据库中的密码
            String dbPassword = (String) authenticationInfo.getCredentials();
            String salt = userService.getUserByEmail(token.getEmail()).getSalt();
            inPassword = DigestUtils.sha256Hex(salt + inPassword + salt);
            //进行密码的比对
            return Objects.equals(inPassword, dbPassword);
        });
    }

    //定义如何获取用户信息的业务逻辑，给shiro做登录
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if (!(token instanceof EmailPasswordToken)) {
            return null;
        }
        EmailPasswordToken upToken = (EmailPasswordToken) token;
        String email = upToken.getEmail();
        // Null email is invalid
        if (StringUtils.isEmpty(email)) {
            throw new AccountException("请输入邮箱");
        }
        User userDB = userService.getUserByEmail(email);
        return getAuthenticationInfo(userDB);
    }


}
