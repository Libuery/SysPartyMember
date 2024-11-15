package com.buyi.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName score
 */
@Data
public class Score implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 学号
     */
    private Integer sid;

    /**
     * 培训号
     */
    private Integer tid;

    /**
     * 成绩
     */
    private Integer score;

    private static final long serialVersionUID = 1L;
}