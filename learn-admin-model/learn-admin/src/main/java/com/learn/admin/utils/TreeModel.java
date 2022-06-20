package com.learn.admin.utils;

import lombok.Data;

import java.util.List;

/**
 * TreeModel
 *
 * @author lijun
 * @date 2021/5/27 10:12
 */
@Data
public class TreeModel {

    /**
     * 主键id
     **/
    private String id;

    /**
     * 标题
     **/
    private String title;

    /**
     * 子节点属性数组
     **/
    private List<TreeModel> children;

    /**
     * 父节点
     **/
    private String parentId;

}
