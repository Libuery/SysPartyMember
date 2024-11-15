package com.buyi.mapper;

import com.buyi.entity.Student;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StudentMapper {
    @Select("select * from student where id = #{id}")
    Student getById(Integer id);

    @Select("select * from student")
    List<Student> findAll();

    int insert(Student student);

    @Delete("delete from student where id = #{id}")
    int deleteById(Integer id);

    int update(Student student);
}
