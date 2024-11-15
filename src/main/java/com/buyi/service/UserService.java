package com.buyi.service;

import com.buyi.entity.User;
import com.buyi.entity.vo.LoginStatus;
import com.buyi.entity.vo.PasswordChangeRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    String login(User user, HttpServletRequest request, HttpServletResponse response);

    void register(User user);

    LoginStatus getLoginStatus(HttpServletRequest request);

    void logout(HttpServletRequest request, HttpServletResponse response);

    void updatePassword(PasswordChangeRequest passwordData, User user);

}
