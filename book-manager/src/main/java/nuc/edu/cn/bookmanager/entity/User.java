package nuc.edu.cn.bookmanager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户实体类
 */
@Data
@TableName("t_user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long userId;
    
    private String userName;
    
    private String password;
    
    private String phone;
    
    private String email;
    
    private String address;
    
    /**
     * 0-普通用户 1-管理员
     */
    private Integer role;
    
    /**
     * 0-禁用 1-正常
     */
    private Integer status;
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
}

