package com.buyi.service.impl;

import com.buyi.common.PageResult;
import com.buyi.entity.Score;
import com.buyi.entity.Student;
import com.buyi.entity.User;
import com.buyi.entity.vo.StudentVo;
import com.buyi.exception.BizException;
import com.buyi.mapper.ScoreMapper;
import com.buyi.mapper.StudentMapper;
import com.buyi.service.StudentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentMapper studentMapper;
    @Resource
    private ScoreMapper scoreMapper;

    @Override
    public List<StudentVo> findAll() {
        // 获取所有学生 然后获取所有的学生id
        List<Student> studentList = studentMapper.findAll();
        if (studentList == null || studentList.isEmpty()) {
            return new ArrayList<>();
        }
        List<Integer> studentIds = studentList.stream().map(Student::getId).toList();

        // 根据所有学生id批量查询实践分
        List<Score> scoreList = scoreMapper.getAllScoreBySids(studentIds);
        // 根据学生id将查询到的实践分进行分组
        Map<Integer, List<Score>> collect = scoreList.stream().collect(Collectors.groupingBy(Score::getSid));

        // 进行对象转换
        List<StudentVo> studentVoList = studentList.stream().map(student -> {
            StudentVo studentVo = new StudentVo();
            BeanUtils.copyProperties(student, studentVo);
            return studentVo;
        }).toList();

        // 给vo对象的totalScore属性赋值
        for (StudentVo studentVo : studentVoList) {
            // 获取某个学生的学分
            List<Score> scores = collect.get(studentVo.getId());
            // 求和
            int totalScore = 0;
            // 判断学生是否有培训分
            if (scores != null && !scores.isEmpty()) {
                // 计算学生实践总分
                totalScore = scores.stream().mapToInt(Score::getScore).sum();
            }
            studentVo.setTotalScore(totalScore);
        }

        return studentVoList;
    }


    // 优化：进行分页
    /**
     * 分页查询学生信息及其实践分
     * 
     * @param page 当前页码
     * @param size 每页大小
     * @return 返回分页结果，包括学生信息和总分
     */
    @Override
    public PageResult pageQuery(int page, int size) {
        // 启动分页，设置当前页和每页大小
        PageHelper.startPage(page, size);
        // 查询所有学生信息
        List<Student> studentList = studentMapper.findAll();
        // 初始化PageInfo对象，用于获取总记录数
        PageInfo<Student> pageInfo = new PageInfo<>(studentList);
        // 获取总记录数
        long total = pageInfo.getTotal();
    
        // 提取所有学生的ID
        List<Integer> studentIds = studentList.stream().map(Student::getId).toList();
    
        // 根据所有学生id批量查询实践分
        List<Score> scoreList = scoreMapper.getAllScoreBySids(studentIds);
        // 根据学生id将查询到的实践分进行分组
        Map<Integer, List<Score>> collect = scoreList.stream().collect(Collectors.groupingBy(Score::getSid));
    
        // 进行对象转换，将学生信息转换为VO对象
        List<StudentVo> studentVoList = studentList.stream().map(student -> {
            StudentVo studentVo = new StudentVo();
            // 复制学生信息到VO对象
            BeanUtils.copyProperties(student, studentVo);
            return studentVo;
        }).toList();
    
        // 给vo对象的totalScore属性赋值
        for (StudentVo studentVo : studentVoList) {
            // 获取某个学生的学分
            List<Score> scores = collect.get(studentVo.getId());
            // 初始化总分为0
            int totalScore = 0;
            // 判断学生是否有实践分
            if (scores != null && !scores.isEmpty()) {
                // 计算学生实践总分
                totalScore = scores.stream().mapToInt(Score::getScore).sum();
            }
            // 设置学生的实践总分
            studentVo.setTotalScore(totalScore);
        }
    
        // 返回分页结果，包括总记录数、当前页码、每页大小和学生信息列表
        return new PageResult(total, page, size, studentVoList);
    }

    @Override
    public StudentVo findCurrentStudentInfo(User user) {
        Student student = studentMapper.getById(user.getSid());
        StudentVo studentVo = new StudentVo();
        BeanUtils.copyProperties(student, studentVo);
        studentVo.setUsername(user.getUsername());
        return studentVo;
    }

    @Override
    public void save(Student student) {
        if (student == null) {
            throw new BizException("不能将数据填写完整");
        }
        int row = studentMapper.insert(student);
        if (row < 0) {
            throw new BizException("添加失败");
        }
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            throw new BizException("参数不合法");
        }
        int row = studentMapper.deleteById(id);
        if (row < 0) {
            throw new BizException("删除失败");
        }
    }

    @Override
    public void update(Student student) {
        if (student == null) {
            throw new BizException("参数不合法");
        }
        int row = studentMapper.update(student);
        if (row < 0) {
            throw new BizException("更新失败");
        }
    }

    @Override
    public Student findById(Integer id) {
        return studentMapper.getById(id);
    }


}
