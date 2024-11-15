package com.buyi.controller;

import com.buyi.common.PageResult;
import com.buyi.common.Result;
import com.buyi.entity.Student;
import com.buyi.entity.User;
import com.buyi.entity.vo.StudentVo;
import com.buyi.service.StudentService;
import com.buyi.utils.MyUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Resource
    private StudentService studentService;


    @GetMapping("/findStudents")
    public Result<List<StudentVo>> findAll() {
        List<StudentVo> studentVoList = studentService.findAll();
        return Result.ok(studentVoList);
    }

    @GetMapping("/page")
    public Result<PageResult> pageQuery(@RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        PageResult pageResult = studentService.pageQuery(page, size);
        return Result.ok(pageResult);
    }

    @GetMapping("/find/{id}")
    public Result<Student> find(@PathVariable Integer id) {
        Student student = studentService.findById(id);
        return Result.ok(student);
    }

    /**
     * 根据当前请求查找学生信息
     * 该方法旨在通过HttpServletRequest对象来查找并返回当前用户的学生信息
     * 使用GET请求映射到"/find"路径
     *
     * @param request HTTP请求对象，用于获取当前用户信息
     * @return 返回一个Result对象，其中包含学生信息（StudentVo）
     *         Result对象用于统一响应格式，便于处理成功和失败的响应
     */
    @GetMapping("/find")
    public Result<StudentVo> findCurrent(HttpServletRequest request) {
        // 从请求中获取当前用户信息
        User user = MyUtils.getUser(request);
        // 调用服务层方法，根据用户信息查找当前学生信息
        StudentVo studentVo = studentService.findCurrentStudentInfo(user);
        // 返回成功结果，包含找到的学生信息
        return Result.ok(studentVo);
    }

    @PostMapping("/save")
    public Result<String> save(@ModelAttribute Student student) {
        studentService.save(student);
        return Result.ok();
    }

    @GetMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Integer id) {
        studentService.delete(id);
        return Result.ok();
    }

    /**
     * 管理员更新学生信息
     *
     * @param student 包含更新信息的学生对象，通过请求参数自动填充
     * @return Result对象
     */
    @PostMapping("/updateAdmin")
    public Result<String> updateAdmin(@ModelAttribute Student student) {
        studentService.update(student);
        return Result.ok();
    }

    /**
     * 学生更新个人信息
     *
     * @param student 包含更新信息的学生对象，通过请求体传递
     * @return Result对象
     */
    @PostMapping("/update")
    public Result<String> update(@RequestBody Student student) {
        studentService.update(student);
        return Result.ok();
    }
}
