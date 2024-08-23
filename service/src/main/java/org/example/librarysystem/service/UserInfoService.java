package org.example.librarysystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.librarysystem.entity.UserInfo;

public interface UserInfoService extends IService<UserInfo> {
    String login(UserInfo userInfo);
    UserInfo getSysUser(UserInfo userInfo);
    UserInfo getByUsername(String userAccount);
}
