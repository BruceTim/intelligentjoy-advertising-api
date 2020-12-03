package com.intelligentjoy.advertising.api.web.controller;

import com.intelligentjoy.advertising.api.base.model.JsonResult;
import com.intelligentjoy.advertising.api.web.config.shiro.PhonePasswordToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class LoginController {

    @RequestMapping(value = "login", params = "userName", method = RequestMethod.POST)
    public Object loginByUserName(String userName, String password) {
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        //默认不记住密码
        token.setRememberMe(true);
        return doLogin(token);
    }

    @RequestMapping(value = "login/phone", method = RequestMethod.POST)
    public Object loginByPhone(String aa, String bb, String password) {
        PhonePasswordToken token = new PhonePasswordToken(aa, bb, password);
        //默认不记住密码
        token.setRememberMe(true);
        return doLogin(token);
    }

    private Object doLogin(AuthenticationToken token) {
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.login(token);
            return JsonResult.success();
        } catch (UnknownAccountException e) {
            return JsonResult.fail(1, e.getMessage());
        } catch (DisabledAccountException e) {
            return JsonResult.fail(1, "您的账户已经被冻结");
        } catch (IncorrectCredentialsException e) {
            return JsonResult.fail(1, "密码错误");
        } catch (ExcessiveAttemptsException e) {
            return JsonResult.fail(1, "您错误的次数太多了吧,封你半小时");
        } catch (RuntimeException e) {
            return JsonResult.fail(1, "运行异常");
        }
    }
}
