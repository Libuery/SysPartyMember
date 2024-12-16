package com.buyi.service.impl;

import com.buyi.entity.Score;
import com.buyi.entity.Student;
import com.buyi.entity.User;
import com.buyi.entity.vo.LoginStatus;
import com.buyi.entity.vo.PasswordChangeRequest;
import com.buyi.exception.BizException;
import com.buyi.mapper.ScoreMapper;
import com.buyi.mapper.StudentMapper;
import com.buyi.mapper.UserMapper;
import com.buyi.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private ScoreMapper scoreMapper;


    @Override
    public String login(User user, HttpServletRequest request, HttpServletResponse response) {
        String username = user.getUsername();
        String password = user.getPassword();
        if ("admin".equals(username) && "admin".equals(password)) {
            user.setUsername("admin");
            request.getSession().setAttribute("user", user);
            // 自动登录
            String auto = request.getParameter("auto");
            if (auto != null) {
                Cookie cookie = new Cookie("user", "admin-admin");
                response.addCookie(cookie);
                cookie.setMaxAge(7200);
            }
            return "admin";
        }

        //账号||密码为null
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            throw new BizException("账号密码不能为空");
        }

        User one = userMapper.getByUsername(username);
        //账号不存在
        if (one == null) {
            throw new BizException("账号不存在");
        }

        //账号存在，校验密码
        if (password.equals(one.getPassword())) {
            //密码正确，登录成功
            HttpSession session = request.getSession();
            session.setAttribute("user", one);
            //自动登录
            String auto = request.getParameter("auto");
            if (auto != null) {
                Cookie cookie = new Cookie("user", one.getUsername() + "-" + one.getPassword());
                response.addCookie(cookie);
                cookie.setMaxAge(7200);
            }
            return "登录成功";
        } else {
            //密码错误
            throw new BizException("密码错误");
        }
    }

    @Override
    public void register(User user) {
        if (user.getUsername() == null || user.getPassword() == null || user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
            throw new BizException("用户名和密码不能为空");
        }

        if (user.getSid() == null) {
            throw new BizException("学号不能为空");
        }

        //查询学生是否存在
        Student student = studentMapper.getById(user.getSid());
        if (student == null) {
            throw new BizException("不存在该学生，请检查学生id");
        }

        // 查询该学生是否已有账号
        User exit = userMapper.getBySid(user.getSid());
        if (exit != null) {
            throw new BizException("您已注册过了，请勿重复注册");
        }

        //查询用户名是否存在
        User one = userMapper.getByUsername(user.getUsername());
        if (one != null) {
            throw new BizException("用户名已经存在，换一个试试");
        }

        //校验完成，插入数据库
        int row = userMapper.insert(user);
        if (row < 0) {
            throw new BizException("注册失败");
        }
    }

    @Override
    public LoginStatus getLoginStatus(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        //封装返回JSON对象
        LoginStatus status = new LoginStatus();
        //用户登录
        if (user != null) {
            String username = user.getUsername();
            status.setLoggedIn(true);
            status.setUsername(username);
            // 判断是否是管理员
            if ("admin".equals(username)) {
                status.setAdmin(true);
                status.setUsername("admin");
                return status;
            }

            // 正常用户 添加实践分和入党阶段信息
            // 添加实践分
            List<Score> scoreList = scoreMapper.getScoreBySid(user.getSid());
            int totalScore = 0;
            for (Score score : scoreList) {
                totalScore += score.getScore();
            }
            status.setTotalScore(totalScore);

            // 添加入党阶段
            Student student = studentMapper.getById(user.getSid());
            status.setStatus(student.getStatus());
        }

        return status;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        //删除用户的cookie
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("user".equals(cookie.getName())) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                break;
            }
        }
    }

    @Override
    public void updatePassword(PasswordChangeRequest request, User user) {
        if (!request.getCurrentPassword().equals(user.getPassword())) {
            throw new BizException("当前密码不正确");
        }
        user.setPassword(request.getNewPassword());
        int row = userMapper.update(user);
        if (row < 0) {
            throw new BizException("修改密码失败");
        }
    }
}
