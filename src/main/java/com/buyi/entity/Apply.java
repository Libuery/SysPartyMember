package com.buyi.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName apply
 */
@Data
public class Apply implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 学号
     */
    private Integer sid;

    /**
     * 申请人姓名
     */
    private String sName;

    /**
     * 申请内容
     */
    private String content;

    /**
     * 申请类型：0-申请积极分子，1-申请预备党员，2-申请党员
     */
    private Integer type;

    /**
     * 申请的状态：0-审核中，1-审核通过，2-审核未通过
     */
    private Integer status;

    private static final long serialVersionUID = 1L;
}