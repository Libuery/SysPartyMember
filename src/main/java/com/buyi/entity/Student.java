package com.buyi.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName student
 */
@Data
public class Student implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 班级
     */
    private String clazz;

    /**
     * 电话号码
     */
    private Integer phone;

    /**
     * 院系
     */
    private String department;

    /**
     * 专业
     */
    private String major;

    /**
     * 身份证号
     */
    private String identity;

    /**
     * 入党阶段：0-普通学生，1-积极分子，2-预备党员，3-党员
     */
    private Integer status;

    private static final long serialVersionUID = 1L;
}