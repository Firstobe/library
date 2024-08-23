package org.example.librarysystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserInfoDTO extends BaseTDO{
    /** 用户名 */
    @Schema(name = "用户名")
    private String userName ;
    /** 角色 */
    @Schema(name = "角色")
    private String role ;
    @Schema(name = "电话")
    private String phoneNumber ;
    @Schema(name = "邮箱")
    private String email ;
}
