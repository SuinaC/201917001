package com.jia.service.impl;

import com.jia.domain.ResponseResult;
import com.jia.domain.entity.LoginUser;
import com.jia.domain.entity.User;
import com.jia.domain.vo.BlogUserLoginVo;
import com.jia.domain.vo.UserInfoVo;
import com.jia.service.BlogLoginService;
import com.jia.utils.BeanCopyUtils;
import com.jia.utils.JwtUtil;
import com.jia.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
@Service
public class BlogLoginServiceImpl implements BlogLoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
//        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
//        //判断是否认证通过
//        if(Objects.isNull(authenticate)){
//            throw new RuntimeException("用户名或密码错误");
//        }
//        //获取userid 生成token
//        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
//        String userId = loginUser.getUser().getId().toString();
//        String jwt = JwtUtil.createJWT(userId);
//        //把用户信息存入redis
//        redisCache.setCacheObject("bloglogin:"+userId,loginUser);
//
//        //把token和userinfo封装 返回
//        //把User转换成UserInfoVo
//        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
//        BlogUserLoginVo vo = new BlogUserLoginVo(jwt,userInfoVo);
//        return ResponseResult.okResult(vo);
        //AuthenticationManager authenticate进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //如果认证没通过，给出对应的提示
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("登录失败");
        }
        //认证通过，通过userid生成一个jwt（json web token）jwt存入ResponseResult返回
        LoginUser loginUser= (LoginUser) authenticate.getPrincipal();
        String userid=loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userid);
        Map<String, String> map=new HashMap<>();
        map.put("token",jwt);
        //把用户信息存入Redis
        redisCache.setCacheObject("login:"+userid,loginUser);
        return new ResponseResult(200,"登录成功",map);
        }
}
