package com.buyi.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName train
 */
@Data
public class Train implements Serializable {
    /**
     * 培训计划编号
     */
    private Integer id;

    /**
     * 培训名称
     */
    private String name;

    /**
     * 年度学期
     */
    private String term;

    /**
     * 学生人数
     */
    private Integer count;

    /**
     * 培训时长（天）
     */
    private Integer duration;

    /**
     * 分值
     */
    private Integer score;

    /**
     * 启用状态：0-启用，1-禁用
     */
    private Integer status;

    private static final long serialVersionUID = 1L;
}