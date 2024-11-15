package com.buyi.entity.vo;

import lombok.Data;

@Data
public class LoginStatus {
    private boolean loggedIn; // 是否登录
    private String username;
    private boolean admin; // 是否是管理员
    private int status; // 入党阶段
    private int totalScore; // 总实践分
}
