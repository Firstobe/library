package org.example.librarysystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("user_info")
@Schema(description = "用户信息")
public class UserInfo extends BaseEntity{

    @Schema(name = "用户名")
    private String userName ;
    @Schema(name = "密码")
    private String passwordHash ;
    @Schema(name = "角色")
    private String role ;

    @Schema(name = "电话")
    private String phoneNumber ;
    @Schema(name = "邮箱")
    private String email ;
    @Schema(name = "性别")
    private String gender ;

}
