package com.buyi.controller;

import com.buyi.common.Result;
import com.buyi.entity.User;
import com.buyi.entity.vo.LoginStatus;
import com.buyi.entity.vo.PasswordChangeRequest;
import com.buyi.exception.BizException;
import com.buyi.service.UserService;
import com.buyi.utils.MyUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Result<String> login(User user, HttpServletRequest request, HttpServletResponse response) {
        String data = userService.login(user, request, response);
        return Result.ok(data);
    }

    @PostMapping("/register")
    public Result<String> register(User user) {
        userService.register(user);
        return Result.ok();
    }

    @GetMapping("/loginStatus")
    public Result<LoginStatus> getLoginStatus(HttpServletRequest request) {
        LoginStatus loginStatus = userService.getLoginStatus(request);
        return Result.ok(loginStatus);
    }

    @GetMapping("/logout")
    public Result<String> logout(HttpServletRequest request, HttpServletResponse response) {
        userService.logout(request, response);
        return Result.ok();
    }
    @PostMapping("/updatePassword")
    public Result<String> updatePassword(@RequestBody PasswordChangeRequest passwordData,
                                         HttpServletRequest request) {
        User user = MyUtils.getUser(request);
        if (passwordData == null) {
            throw new BizException("参数错误");
        }
        if (passwordData.getCurrentPassword() == null || passwordData.getCurrentPassword().isEmpty()) {
            throw new BizException("请输入当前密码");
        }
        if (passwordData.getNewPassword() == null || passwordData.getNewPassword().isEmpty()) {
            throw new BizException("请输入新密码");
        }
        // 验证新密码和确认密码是否一致
        if (!passwordData.getNewPassword().equals(passwordData.getConfirmPassword())) {
            throw new BizException("新密码和确认密码不一致");
        }

        userService.updatePassword(passwordData, user);
        return Result.ok();
    }
}

