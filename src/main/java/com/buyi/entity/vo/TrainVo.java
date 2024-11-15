package com.buyi.entity.vo;

import lombok.Data;

import java.io.Serializable;


@Data
public class TrainVo implements Serializable {
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
     * 是否已学
     */
    private boolean learned;

    private static final long serialVersionUID = 1L;
}