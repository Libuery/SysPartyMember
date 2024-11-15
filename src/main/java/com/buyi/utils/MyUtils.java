package com.buyi.utils;

import com.buyi.entity.User;
import com.buyi.exception.BizException;
import jakarta.servlet.http.HttpServletRequest;

public class MyUtils {
    public static User getUser(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new BizException("请先登录");
        }
        return user;
    }
}
