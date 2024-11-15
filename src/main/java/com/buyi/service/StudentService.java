package com.buyi.service;

import com.buyi.common.PageResult;
import com.buyi.entity.Student;
import com.buyi.entity.User;
import com.buyi.entity.vo.StudentVo;

import java.util.List;

public interface StudentService {
    List<StudentVo> findAll();

    void save(Student student);

    void delete(Integer id);

    void update(Student student);

    Student findById(Integer id);

    PageResult pageQuery(int page, int size);

    StudentVo findCurrentStudentInfo(User user);
}
