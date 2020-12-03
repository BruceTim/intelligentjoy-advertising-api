//package com.intelligentjoy.advertising.api.web.config.shiro;
//
//import org.apache.shiro.session.Session;
//import org.apache.shiro.session.mgt.ValidatingSession;
//import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//
//import java.io.Serializable;
//import java.util.concurrent.TimeUnit;
//
//public class CustomSessionDao extends CachingSessionDAO{
//
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    /**
//     * 默认缓存过期时间：30分钟
//     */
//    public final static long DEFAULT_EXPIRE = 60 * 30;
//
//    @Override
//    protected Serializable doCreate(Session session) {
//        //创造SessionId
//        Serializable sessionId = generateSessionId(session);
//        //注册SessionId
//        assignSessionId(session, sessionId);
//        //缓存Session
//        redisTemplate.opsForValue().set(sessionId.toString(), session, DEFAULT_EXPIRE, TimeUnit.SECONDS);
//        return sessionId;
//    }
//
//    @Override
//    protected void doUpdate(Session session) {
//        if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
//            //会话过期/停止
//            return;
//        }
//        redisTemplate.opsForValue().set(session.getId().toString(), session, DEFAULT_EXPIRE, TimeUnit.SECONDS);
//    }
//
//    @Override
//    protected void doDelete(Session session) {
//        redisTemplate.delete(session.getId().toString());
//    }
//
//    @Override
//    protected Session doReadSession(Serializable sessionId) {
//        return (Session) redisTemplate.opsForValue().get(sessionId.toString());
//    }
//}
