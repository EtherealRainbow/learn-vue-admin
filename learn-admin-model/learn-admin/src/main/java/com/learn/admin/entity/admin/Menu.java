package com.learn.admin.entity.admin;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 菜单
 *
 * @author lijun
 * @program learn-admin-model
 * @date 2022/6/15 11:35
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_menu")
public class Menu extends Model<Menu> {

    @TableId(value = "UUID", type = IdType.ASSIGN_UUID)
    private String uuid;

    /**
     * 父菜单ID，一级菜单为0
     */
    private String pid;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单URL
     */
    private String url;
    /**
     * 授权(多个用逗号分隔，如：sys:user:list,sys:user:save)
     */
    private String permissions;
    /**
     * 类型   0：菜单   1：按钮
     */
    private Integer type;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updater;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;
    /**
     * 上级菜单名称
     */
    @TableField(exist = false)
    private String parentName;


}
