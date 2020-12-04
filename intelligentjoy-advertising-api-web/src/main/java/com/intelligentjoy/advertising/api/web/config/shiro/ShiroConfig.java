package com.intelligentjoy.advertising.api.web.config.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.AbstractAuthenticationStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ShiroConfig {

    @Bean
    public UsernamePasswordRealm usernamePasswordRealm() {
        UsernamePasswordRealm customRealm = new UsernamePasswordRealm();
        customRealm.initCredentialsMatcher();
        return customRealm;
    }

    @Bean
    public PhonePasswordRealm phonePasswordRealm() {
        PhonePasswordRealm phonePasswordRealm = new PhonePasswordRealm();
        phonePasswordRealm.initCredentialsMatcher();
        return phonePasswordRealm;
    }

    @Bean
    public EmailPasswordRealm emailPasswordRealm() {
        EmailPasswordRealm customRealm = new EmailPasswordRealm();
        customRealm.initCredentialsMatcher();
        return customRealm;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public Authenticator authenticator() {
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setAuthenticationStrategy(new AbstractAuthenticationStrategy() {
            @Override
            public AuthenticationInfo afterAttempt(Realm realm, AuthenticationToken token, AuthenticationInfo singleRealmInfo, AuthenticationInfo aggregateInfo, Throwable t) throws AuthenticationException {
                if (t != null && t instanceof AuthenticationException) {
                    throw (AuthenticationException) t;
                }
                return super.afterAttempt(realm, token, singleRealmInfo, aggregateInfo, t);
            }
        });
        return authenticator;
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setAuthenticator(authenticator());
        List<Realm> realms = new ArrayList<>(2);
        realms.add(usernamePasswordRealm());
        realms.add(phonePasswordRealm());
        realms.add(emailPasswordRealm());
        securityManager.setRealms(realms);
        return securityManager;
    }

    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("favicon.ico", "anon");
        chainDefinition.addPathDefinition("/login", "anon");
        chainDefinition.addPathDefinition("/login/phone", "anon");
        chainDefinition.addPathDefinition("/login/email", "anon");
        chainDefinition.addPathDefinition("/**", "user");
        return chainDefinition;
    }


}
