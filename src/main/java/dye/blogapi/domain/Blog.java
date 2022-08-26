package dye.blogapi.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@TableName("blog")
public class Blog {

    @TableField(exist = false)
    private String content;

    @TableId(type = IdType.AUTO)
    private Integer blogId;

    @NotBlank(message = "博客名不能为空")
    private String blogName;

    @NotBlank(message = "用户id不能为空")
    private String userId;


}
