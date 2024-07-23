package com.swpu.userserver.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("permission")
public class Permission {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField("perm_name")
    private String permName;
    @TableField("perm_type")
    private Integer permType;
    @TableField("perm_key")
    private String permKey;
    @TableField("perm_url")
    private String permUrl;
    @TableField("parentId")
    private String parentId;
    @TableField("create_time")
    private LocalDateTime createTime;
    @TableField("create_by")
    private String createBy;
    @TableField("update_time")
    private LocalDateTime updateTime;
    @TableField("operator")
    private String operator;


}
