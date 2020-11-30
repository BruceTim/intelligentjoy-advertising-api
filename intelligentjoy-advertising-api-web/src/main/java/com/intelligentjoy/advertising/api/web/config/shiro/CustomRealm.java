package com.intelligentjoy.advertising.api.web.config.shiro;

import com.intelligentjoy.advertising.api.base.interfacees.ResourceService;
import com.intelligentjoy.advertising.api.base.interfacees.RoleService;
import com.intelligentjoy.advertising.api.base.interfacees.UserService;
import com.intelligentjoy.advertising.api.base.model.Resource;
import com.intelligentjoy.advertising.api.base.model.Role;
import com.intelligentjoy.advertising.api.base.model.User;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;
import java.util.stream.Collectors;

public class CustomRealm extends AuthorizingRealm {

    @Autowired
    @Lazy
    private UserService userService;

    @Autowired
    @Lazy
    private RoleService roleService;

    @Autowired
    @Lazy
    private ResourceService resourceService;

    //告诉shiro如何根据获取到的用户信息中的密码和盐值来校验密码
    {
        //设置用于匹配密码的CredentialsMatcher
        HashedCredentialsMatcher hashMatcher = new HashedCredentialsMatcher();
        hashMatcher.setHashAlgorithmName(Sha256Hash.ALGORITHM_NAME);
        hashMatcher.setStoredCredentialsHexEncoded(false);
        hashMatcher.setHashIterations(1024);
        this.setCredentialsMatcher(hashMatcher);
    }


    //定义如何获取用户的角色和权限的逻辑，给shiro做权限判断
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //null usernames are invalid
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        User user = (User) getAvailablePrincipal(principals);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        List<Role> roles = roleService.getRolesByUserId(user.getUserId());
        List<Resource> resources = resourceService.getResourcesByUserId(user.getUserId());
        info.setRoles(roles.stream().map(Role::getRoleCode).collect(Collectors.toSet()));
        info.setStringPermissions(resources.stream().map(Resource::getResourceCode).collect(Collectors.toSet()));
        return info;
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
        //查询用户的角色和权限存到SimpleAuthenticationInfo中，这样在其它地方
        //SecurityUtils.getSubject().getPrincipal()就能拿出用户的所有信息，包括角色和权限
        //List<Role> roles = roleService.getRolesByUserId(userDB.getUserId());
        //List<Resource> resources = resourceService.getResourcesByUserId(userDB.getUserId());
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userDB, userDB.getPassword(), getName());
        if (userDB.getSalt() != null) {
            info.setCredentialsSalt(new MySimpleByteSource(userDB.getSalt()));
        }
        return info;
    }
}
