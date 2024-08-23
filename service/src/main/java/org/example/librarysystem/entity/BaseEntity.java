package org.example.librarysystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /*
     * @Author cms
     * @Description //TODO $
     * @Date $ $
     * @Param $
     * @return $
     **/
    @TableId(
            value = "id",
            type = IdType.ASSIGN_ID
    )
    private String id;
    private String createUser;
    private Date createTime;
    private String updateUser;
    private Date updateTime;
}
