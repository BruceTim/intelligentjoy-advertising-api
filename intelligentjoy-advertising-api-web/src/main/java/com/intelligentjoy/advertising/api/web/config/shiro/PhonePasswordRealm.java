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
public class PhonePasswordRealm extends CustomRealm {

    @Override
    public String getName() {
        return PhonePasswordRealm.class.getName();
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof PhonePasswordToken;
    }

    @Override
    protected void initCredentialsMatcher() {
        //设置用于匹配密码的CredentialsMatcher
        this.setCredentialsMatcher((authenticationToken, authenticationInfo) -> {
            PhonePasswordToken phoneToken = (PhonePasswordToken) authenticationToken;
            //获得用户输入的密码:(可以采用加盐(salt)的方式去检验)
            String inPassword = new String(phoneToken.getPassword());
            String phoneAreaCode = phoneToken.getPhoneAreaCode();
            String phone = phoneToken.getPhone();
            //获得数据库中的密码
            String dbPassword = (String) authenticationInfo.getCredentials();
            String salt = userService.getUserByPhone(phoneAreaCode, phone).getSalt();
            inPassword = DigestUtils.sha256Hex(salt + inPassword + salt);
            //进行密码的比对
            return Objects.equals(inPassword, dbPassword);
        });
    }

    //定义如何获取用户信息的业务逻辑，给shiro做登录
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if (!(token instanceof PhonePasswordToken)) {
            return null;
        }
        PhonePasswordToken upToken = (PhonePasswordToken) token;
        String phoneAreaCode = upToken.getPhoneAreaCode();
        String phone = upToken.getPhone();
        // Null phone is invalid
        if (StringUtils.isEmpty(phoneAreaCode) && StringUtils.isEmpty(phone)) {
            throw new AccountException("请输入手机号");
        }
        User userDB = userService.getUserByPhone(phoneAreaCode, phone);
        return getAuthenticationInfo(userDB);
    }


}
