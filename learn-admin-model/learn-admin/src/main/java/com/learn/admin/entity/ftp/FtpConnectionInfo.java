package com.learn.admin.entity.ftp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.io.Serializable;

/**
 * <p>
 * FTP链接管理类
 * </p>
 *
 * @author lijun
 * @since 2020-10-27
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_ftp_connection_info")
public class FtpConnectionInfo extends Model<FtpConnectionInfo> implements Serializable {

    @TableId(value = "UUID", type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "UUID")
    private String uuid;

    /**
     * 系统名称
     **/
    @NonNull
    @TableField("SYSTEM_NAME")
    @ApiModelProperty(value = "系统名称")
    private String systemName;


    /**
     * ip地址
     **/
    @NonNull
    @TableField("IP_ADDRESS")
    @ApiModelProperty(value = "IP地址")
    private String ipAddress;

    /**
     * 端口
     **/
    @NonNull
    @TableField("PORT_ADDRESS")
    @ApiModelProperty(value = "端口")
    private String portAddress;

    /**
     * 用户名
     **/
    @NonNull
    @TableField("USER_NAME")
    @ApiModelProperty(value = "用户名")
    private String userName;

    /**
     * 密码
     **/
    @NonNull
    @TableField("PASSWORD")
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 状态 0：未启用  1：已启用
     **/
    @NonNull
    @TableField("STATE")
    @ApiModelProperty(value = "状态",example = "0")
    private Integer state=0;

    /**
     * 根节点
     **/
    @TableField("PATH")
    @ApiModelProperty(value = "根节点")
    private String path;

    /**
     * 远程类型
     **/
    @TableField("TYPE")
    @ApiModelProperty(value = "远程类型")
    private String type;

    /**
     * 创建人
     **/
    @TableField("CREATE_USER")
    @ApiModelProperty(value = "创建人")
    private String createUser;

    /**
     * 创建时间
     **/
    @TableField("CREATE_DATE")
    @ApiModelProperty(value = "创建时间")
    private String createDate;

    /**
     * 更新时间
     **/
    @TableField("UPDATE_DATE")
    @ApiModelProperty(value = "更新时间")
    private String updateDate;
}
