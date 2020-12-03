package com.intelligentjoy.advertising.api.web.config.shiro;

import com.intelligentjoy.advertising.api.base.interfacees.ResourceService;
import com.intelligentjoy.advertising.api.base.interfacees.RoleService;
import com.intelligentjoy.advertising.api.base.interfacees.UserService;
import com.intelligentjoy.advertising.api.base.model.Resource;
import com.intelligentjoy.advertising.api.base.model.Role;
import com.intelligentjoy.advertising.api.base.model.User;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义校验
 *
 * @author BruceTim
 * @date 2020-12-03
 */
public abstract class CustomRealm extends AuthorizingRealm {

    @Autowired
    protected StringRedisTemplate redisTemplate;

    @Autowired
    @Lazy
    protected UserService userService;

    @Autowired
    @Lazy
    protected RoleService roleService;

    @Autowired
    @Lazy
    protected ResourceService resourceService;

    protected abstract void initCredentialsMatcher();


    //定义如何获取用户的角色和权限的逻辑，给shiro做权限判断
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //null usernames are invalid
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        User user = (User) getAvailablePrincipal(principals);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(user.getRoles());
        info.setStringPermissions(user.getResources());
        return info;
    }

    protected SimpleAuthenticationInfo getAuthenticationInfo(User user) {
        //查询用户的角色和权限存到SimpleAuthenticationInfo中，这样在其它地方
        //SecurityUtils.getSubject().getPrincipal()就能拿出用户的所有信息，包括角色和权限
        List<Role> roles = roleService.getRolesByUserId(user.getUserId());
        List<Resource> resources = resourceService.getResourcesByUserId(user.getUserId());
        user.getRoles().addAll(roles.stream().map(Role::getRoleCode).collect(Collectors.toSet()));
        user.getResources().addAll(resources.stream().map(Resource::getResourceCode).collect(Collectors.toSet()));
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        if (user.getSalt() != null) {
            info.setCredentialsSalt(new MySimpleByteSource(user.getSalt()));
        }
        return info;
    }

}
