package com.swpu.userserver.config;

import com.swpu.userserver.common.exceptions.UnAuthException;
import com.swpu.userserver.user.entity.User;
import com.swpu.userserver.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 基于Interceptor的JWT身份认证流程
 * 1.前端向后台发送登录请求
 * 2.后台接收登录的用户名和密码进行验证
 * 3.验证成功，则使用JWT生成的token，返回给前端
 * 4.前端接收token，保存在缓存中
 * 5.当前端发送其他请求时，需要将token添加到请求头中
 * 6.后台服务定义Interceptor，使用preHandle拦截请求，从请求头获取token进行验证
 * 7.如果验证失败抛异常，异常由ControllerAdvice处理，处理结果返回给前端
 * 8.自定义的Interceptor要注册到WebMvcConfiguration才能生效
 */
@Component
public class JWTInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtComponent jwtComponent;
    @Autowired
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
            return true;
        }
       String token = request.getHeader("Authorization");
       //判断token是否为空
        if(ObjectUtils.isEmpty(token)) {
            // 抛token为空的异常
            throw new UnAuthException("token为空");
        }
        String username = jwtComponent.getUserName(token);
        User user = userService.getUserByUsername(username);
        if (ObjectUtils.isEmpty(user)) {
            throw new UnAuthException("user invalid");
        }
        //判断token是否过期
        Date date = jwtComponent.getExpires(token);
        if(date.before(new Date())){
            // 抛token过期异常
            throw new UnAuthException("toke已过期");
        }
        //1.获取用户名，根据用户名查询数据表，判断用户名是否存在
        //2.如果用户名存在，则使用用户名和密码做token合法验证
        if (!jwtComponent.verify(token, username, user.getPassword())) {
            throw new UnAuthException("token invalid");
        }
        return true;
    }
}
