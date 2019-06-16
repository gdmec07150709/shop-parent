package cn.wolfcode.shop.utils;

import cn.wolfcode.shop.domain.UserLogin;
import cn.wolfcode.shop.exception.UserException;
import cn.wolfcode.shop.exception.CodeMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

@Component
public class UserLoginArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> parameterType = methodParameter.getParameterType();
        return parameterType == UserLogin.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) nativeWebRequest.getNativeRequest();
        String token = request.getHeader("head_token");
        //判断token是否合法
        if (!StringUtils.hasLength(token)) {
            throw new UserException(CodeMsg.USER_TOKEN_ERROR);
        }
        //通过token获取当前登录用户
        UserLogin userLogin = (UserLogin) redisTemplate.opsForValue().get(RedisContants.CURRENT_USER.replace("TOKEN", token));
        if (userLogin == null) {
            throw new UserException(CodeMsg.USER_TIME_OUT);
        }
        return userLogin;
    }
}
