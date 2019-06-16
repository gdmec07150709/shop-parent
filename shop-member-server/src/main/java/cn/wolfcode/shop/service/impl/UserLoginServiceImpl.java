package cn.wolfcode.shop.service.impl;

import cn.wolfcode.shop.domain.UserInfo;
import cn.wolfcode.shop.domain.UserLogin;
import cn.wolfcode.shop.exception.UserException;
import cn.wolfcode.shop.service.IUserLoginService;
import cn.wolfcode.shop.exception.CodeMsg;
import cn.wolfcode.shop.mapper.UserInfoMapper;
import cn.wolfcode.shop.mapper.UserLoginMapper;
import cn.wolfcode.shop.utils.RedisContants;
import cn.wolfcode.shop.util.MD5;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class UserLoginServiceImpl implements IUserLoginService{
    @Autowired
    private UserLoginMapper userLoginMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public void register(String username, String password) {
        if(isExists(username)){
            throw new UserException(CodeMsg.USER_NAME_EXISTS);
        }else{
            UserLogin userLogin = new UserLogin();
            userLogin.setUserName(username);
            userLogin.setPassword(MD5.encode(password));
            userLogin.setState(new Byte("0"));
            userLoginMapper.insert(userLogin);
            UserInfo userInfo = new UserInfo();
            userInfo.setId(userLogin.getId());
            userInfo.setRegistTime(new Date());
            userInfoMapper.insert(userInfo);
        }
    }

    @Override
    public String login(String username, String password) {
        UserLogin userLogin =  userLoginMapper.login(username,MD5.encode(password));
        if(userLogin==null){
            throw new UserException(CodeMsg.USER_NAME_OR_PASSWORD_ERROR);
        }
        String token = createToken(userLogin);
        return token;
    }

    @Override
    public void logout(String token) {
        //如果token为空,提示用户"登录信息异常
        if(!StringUtils.hasLength(token)){
            throw new UserException(CodeMsg.USER_TOKEN_ERROR);
        }
        //通过token去redis查询,若无,则提示登录超时
        UserLogin userLogin = (UserLogin) redisTemplate.opsForValue().get(RedisContants.CURRENT_USER.replace("TOKEN", token));
        if(userLogin == null){
            throw new UserException(CodeMsg.USER_TIME_OUT);
        }
        //否则,删除token对应的登录用户信息
        redisTemplate.delete(RedisContants.CURRENT_USER.replace("TOKEN",token));
    }

    /**
     * 创建token并且把登录用户存在redis中
     * @param userLogin
     * @return
     */
    private String createToken(UserLogin userLogin) {
        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(RedisContants.CURRENT_USER.replace("TOKEN",token),userLogin);
        redisTemplate.expire(RedisContants.CURRENT_USER.replace("TOKEN",token),30, TimeUnit.DAYS);
        return token;
    }

    /**
     * 返回true表示已经注册,返回flase表示未注册
     * @param username
     * @return
     */
    private boolean isExists(String username) {
        if (userLoginMapper.isExist(username) != null) {
            return true;
        } else {
            return false;
        }
    }
}
