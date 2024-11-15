package com.buyi.interceptor;

import com.buyi.entity.User;
import com.buyi.mapper.UserMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class AutoLoginInterceptor implements HandlerInterceptor {

    @Resource
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            //用户未登录，校验Cookie
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("user".equals(cookie.getName())) {
                        //之前有自动登录
                        String value = cookie.getValue();
                        String[] split = value.split("-");
                        String username = split[0];
                        String password = split[1];
                        if (username != null && password != null) {
                            // 管理员鉴别
                            if ("admin".equals(username) && "admin".equals(password)) {
                                //cookie中的账号密码正确，将username保存到session中
                                User admin = new User();
                                admin.setUsername("admin");
                                admin.setPassword("admin");
                                session.setAttribute("user", admin);
                                log.info("管理员自动登录成功....");
                                break;
                            }

                            User one = userMapper.getByUsername(username);
                            if (password.equals(one.getPassword())) {
                                //cookie中的账号密码正确，将username保存到session中
                                session.setAttribute("user", one);
                                log.info("用户{}自动登录成功....", one.getUsername());
                                break;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
