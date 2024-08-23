package org.example.librarysystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.example.librarysystem.config.RsaProperties;
import org.example.librarysystem.entity.LoginUser;
import org.example.librarysystem.entity.UserInfo;
import org.example.librarysystem.mapper.UserInfoMapper;
import org.example.librarysystem.service.UserInfoService;
import org.example.librarysystem.utils.JwtTokenUtils;
import org.example.librarysystem.utils.PasswordEncoder;
import org.example.librarysystem.utils.RsaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService, UserDetailsService {
    @Autowired
    private RsaProperties rsaProperties;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Override
    public String login(UserInfo userInfo) {
        Map<String, Object> claims = new HashMap<>();
        String token = "";
        String decryptedPassword = RsaUtils.decryptByPrivateKey(rsaProperties.getPrivateKey(),userInfo.getPasswordHash());
        userInfo.setPasswordHash(decryptedPassword);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userInfo.getUserName(), userInfo.getPasswordHash());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        if (authentication == null){
            log.error(userInfo.getUserName() +"登录失败，用户名或密码错误");
            throw new RuntimeException("用户名或密码错误");
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        claims.put("userAccount", loginUser.getUserInfo().getUserName());
        claims.put("id", loginUser.getUserInfo().getId());
        claims.put("userPassword", loginUser.getUserInfo().getPasswordHash());
        token = jwtTokenUtils.createToken(claims);
        stringRedisTemplate.opsForValue().set("token:" + token, "Bearer "+token);
        stringRedisTemplate.expire("token:" + token, Duration.ofDays(1));
        log.info("用户"+loginUser.getUserInfo().getUserName()+"登录成功");
        return token;
    }


    @Override
    public UserInfo getByUsername(String userAccount) {
        LambdaQueryWrapper<UserInfo> sysUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysUserLambdaQueryWrapper.eq(UserInfo::getUserName,userAccount);

        UserInfo sysUser = this.getOne(sysUserLambdaQueryWrapper);
        return sysUser;
    }



    @Override
    public UserInfo getSysUser(UserInfo sysUser) {
        LambdaQueryWrapper<UserInfo> sysUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysUserLambdaQueryWrapper.eq(StringUtils.hasLength(sysUser.getUserName()),UserInfo::getUserName,sysUser.getUserName());
        sysUserLambdaQueryWrapper.eq(StringUtils.hasLength(sysUser.getPhoneNumber()),UserInfo::getPhoneNumber,sysUser.getPhoneNumber());
        if (sysUser.getId() != null && StringUtils.hasLength(String.valueOf(sysUser.getId()))){
            sysUserLambdaQueryWrapper.eq(UserInfo::getId,sysUser.getId());
        }
        UserInfo sysUser1 = this.getOne(sysUserLambdaQueryWrapper);
        return sysUser1;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo  sysUser = getByUsername(username);
        if (sysUser == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        return new LoginUser(sysUser);
    }
}
