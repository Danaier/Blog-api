package dye.blogapi.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@TableName("user")
public class User {

    //主键生成策略
    @TableId(type = IdType.AUTO)
    private Integer id;
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotBlank(message = "邮箱不能为空")
    private String email;
//    @NotBlank(message = "电话号码不能为空")
//    private String phone;
//
//    private String question;
//    private String answer;
//
//    private Integer role;
//
//    //LocalDateTime Java8
//    //MyBatisPlus 自动化命名转化 create_time
//    @TableField("create_time")
//    private LocalDateTime createTime;
//    @TableField("update_time")
//    private LocalDateTime updateTime;

}
