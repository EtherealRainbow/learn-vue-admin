package com.learn.admin.entity.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * TODO
 *
 * @author lijun
 * @program learn-admin-model
 * @date 2022/6/15 11:46
 */
@EqualsAndHashCode(callSuper = false)
@Data
@TableName("sys_role_user")
public class RoleUser extends Model<RoleUser> {

    @TableId(value = "UUID", type = IdType.ASSIGN_UUID)
    private String uuid;
    /**
     * 角色ID
     */
    private String roleId;
    /**
     * 用户ID
     */
    private String userId;

}
