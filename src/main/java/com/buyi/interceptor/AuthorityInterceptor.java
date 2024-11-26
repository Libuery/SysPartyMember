package com.buyi.interceptor;

import com.buyi.entity.User;
import com.buyi.exception.BizException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Order(3)
@Component
public class AuthorityInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new BizException("请先登录");
        }
        if (!"admin".equals(user.getUsername())) {
            throw new BizException("无管理员权限");
        }
        return true;
    }
}
