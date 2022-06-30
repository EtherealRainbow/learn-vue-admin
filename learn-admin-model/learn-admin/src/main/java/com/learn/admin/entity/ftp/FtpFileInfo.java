package com.learn.admin.entity.ftp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * FTP 文件创建目录
 *
 * @author lijun
 * @program learn-admin
 * @date 2022/5/20 10:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_ftp_file")
public class FtpFileInfo extends Model<FtpFileInfo> implements Serializable {

    @TableId(value = "UUID", type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "UUID")
    private String uuid;

    /**
     * 文件名称
     **/
    @TableField("FILE_NAME")
    @ApiModelProperty(value = "文件名称")
    private String systemName;

    /**
     * 当前文件/文件夹 所在路径
     **/
    @TableField("PATH")
    @ApiModelProperty(value = "当前文件/文件夹 所在路径")
    private String path;

    /**
     * 上级节点uuid
     **/
    @TableField("PARENT_ID")
    @ApiModelProperty(value = "上级节点UUID")
    private String parentId;

    /**
     * 文件类型
     **/
    @TableField("FILE_TYPE")
    @ApiModelProperty(value = "文件类型")
    private String fileType;

    /**
     * 文件大小
     **/
    @TableField("FILE_SIZE")
    @ApiModelProperty(value = "文件大小")
    private String fileSize;

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

    @TableField("SHOW_FLAG")
    private Boolean showFlag=false;

    @TableField("INPUT_FLAG")
    private Boolean inputFlag = false;

    @TableField("SUFFIX")
    private String suffix;

}
