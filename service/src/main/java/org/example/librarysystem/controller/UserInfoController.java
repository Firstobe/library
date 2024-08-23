package org.example.librarysystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.example.librarysystem.config.JwtSecurityProperties;
import org.example.librarysystem.config.RsaProperties;
import org.example.librarysystem.dto.UserInfoDTO;
import org.example.librarysystem.entity.LoginUser;
import org.example.librarysystem.entity.UserInfo;
import org.example.librarysystem.service.UserInfoService;
import org.example.librarysystem.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("laboratory/auth")
@RestController
@Slf4j
public class UserInfoController {
    @Autowired
    private UserInfoService sysUserService;
    @Autowired
    private JwtSecurityProperties jwtSecurityProperties;
    @Autowired
    private RsaProperties rsaProperties;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @PostMapping("/login")
    public Result login(@RequestBody UserInfo userInfo) {

        String token = "";
        try{
            token =	sysUserService.login(userInfo);
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail("账号或者密码错误");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userToken",jwtSecurityProperties.getTokenStartWith()+token);
        map.put("userInfo",sysUserService.getSysUser(userInfo));
        return Result.succ(map);
    }
    @Operation(summary = "注册")
    @PostMapping("/register")
    public Result register(@RequestBody UserInfo sysUser){
        try{
            UserInfo sysUser1 =	sysUserService.getByUsername(sysUser.getUserName());
            if (sysUser1 != null){
                return Result.fail("该账号已存在");
            }
            //解密密码
            String decryptedPassword = RsaUtils.decryptByPrivateKey(rsaProperties.getPrivateKey(),sysUser.getPasswordHash());
            String password = passwordEncoder.encode(decryptedPassword);

            sysUser.setPasswordHash(password);
            sysUserService.save(sysUser);
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail("注册失败");
        }
        return Result.succ("注册成功");
    }
    @Operation(summary = "获取RSA加密公钥")
    @GetMapping(value = "/publicKey")
    public Result publicKey(){
        try{
            return Result.succ("获取RSA加密公钥成功",rsaProperties.getPublicKey());
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail("获取RSA加密公钥失败");
        }
    }
    @GetMapping(value = "userInfo")
    public Result getUserInfo(){
        UserInfo sysUser = sysUserService.getById(SecurityUtils.getLoginUserId());
        return Result.succ(sysUser);
    }
    @PostMapping("logout")
    public Result logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        String token = null;
        JwtSecurityProperties jwtSecurityProperties = SpringContextHolder.getBean(JwtSecurityProperties.class);
        String bearerToken = httpServletRequest.getHeader(jwtSecurityProperties.getHeader());
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(jwtSecurityProperties.getTokenStartWith())) {
            token = bearerToken.substring(jwtSecurityProperties.getTokenStartWith().length());
        }
        Authentication authentication = null;
        if (StringUtils.hasText(token) && jwtTokenUtils.validateToken(token)) {
            authentication = jwtTokenUtils.getAuthentication(token);
        }
        if (authentication != null) {
            stringRedisTemplate.delete("token:" + token);
            new SecurityContextLogoutHandler().logout(httpServletRequest, httpServletResponse, authentication);
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        log.info(loginUser.getUsername() +"退出成功");
        return Result.succ("退出成功");
    }
    //修改信息
    @PutMapping("update")
    public Result update(@RequestBody UserInfo userInfo){
        UserInfo sysUser = sysUserService.getById(userInfo.getId());
        sysUser.setUserName(userInfo.getUserName());
        sysUser.setPhoneNumber(userInfo.getPhoneNumber());
        sysUser.setEmail(userInfo.getEmail());
        sysUserService.updateById(sysUser);
        log.info(sysUser.getUserName() +"修改成功");
        return Result.succ("修改成功");
    }
    @GetMapping("/getInfoByRole")
    public Result getInfoByRole(UserInfoDTO userInfoDTO){
        int pageNo = userInfoDTO.getPageNum() == null ? 1 : userInfoDTO.getPageNum();
        int pageSize = userInfoDTO.getPageSize() == null ? 10 : userInfoDTO.getPageSize();
        LambdaQueryWrapper<UserInfo> wrapper = new LambdaQueryWrapper<>();
        Page<UserInfo> sysUser = sysUserService.page(new Page(pageNo,pageSize),wrapper);
        return Result.succ("获取信息成功",sysUser);
    }
    @DeleteMapping()
    public Result deleteUserInfo(@RequestBody UserInfo userInfo){
        try{
            sysUserService.removeById(userInfo.getId());
        }catch (Exception e){
           log.error(userInfo.getUserName()+"删除失败,原因"+e.getMessage());
            return Result.fail("删除失败");
        }
        log.info(userInfo.getUserName()+"删除成功");
        return Result.succ("删除成功");
    }

}
