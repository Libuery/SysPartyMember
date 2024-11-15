package com.buyi.service.impl;

import com.buyi.common.PageResult;
import com.buyi.entity.Apply;
import com.buyi.entity.Score;
import com.buyi.entity.Student;
import com.buyi.entity.User;
import com.buyi.exception.BizException;
import com.buyi.mapper.ApplyMapper;
import com.buyi.mapper.ScoreMapper;
import com.buyi.mapper.StudentMapper;
import com.buyi.service.ApplyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplyServiceImpl implements ApplyService {

    @Resource
    private ApplyMapper applyMapper;
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private ScoreMapper scoreMapper;

    @Override
    public List<Apply> findAll() {
        return applyMapper.findAll();
    }

    @Override
    public void audit(Apply apply) {
        if (apply == null) {
            throw new BizException("参数不合法");
        }
        int status = apply.getStatus();
        apply = applyMapper.findById(apply.getId());
        apply.setStatus(status);
        Student student = studentMapper.getById(apply.getSid());
        // 若学生阶段和申请类型一致，表示要进行下一个阶段
        // 判断是否是批准
        if (apply.getStatus() == 1 && student.getStatus().equals(apply.getType())) {
            // 校验实践分是否够
            // 注：培训分不足80分不能申请预备党员，培训分不足160分不能申请党员
            List<Score> scoreList = scoreMapper.getScoreBySid(student.getId());
            int totalScore = 0;
            if (scoreList != null && !scoreList.isEmpty()) {
                totalScore = scoreList.stream().mapToInt(Score::getScore).sum();
            }

            // 不足80分不能申请预备党员
            if (apply.getType() == 1 && totalScore < 80) {
                throw new BizException("培训分不足80分，无法申请预备党员");
            }

            // 不足160分不能申请党员
            if (apply.getType() == 2 && totalScore < 160) {
                throw new BizException("培训分不足160分，无法申请党员");
            }

            // 更新学生阶段变化
            student.setStatus(student.getStatus() + 1);
            studentMapper.update(student);
        }

        // 更新申请信息
        int row = applyMapper.update(apply);

        if (row < 0) {
            throw new BizException("审批失败");
        }
    }

    @Override
    public List<Apply> findBySid(Integer sid) {
        return applyMapper.findBySid(sid);
    }

    @Override
    public void deleteById(Integer id) {
        if (id == null) {
            throw new BizException("参数不合法");
        }
        // 查询申请状态
        Apply apply = applyMapper.findById(id);
        if (apply.getStatus() != 0) {
            throw new BizException("审核已完成，无法撤回");
        }

        int row = applyMapper.deleteById(id);
        if (row < 0) {
            throw new BizException("操作失败");
        }
    }

    @Override
    public void update(Apply apply) {
        if (apply == null) {
            throw new BizException("参数不合法");
        }

        Apply one = applyMapper.findById(apply.getId());
        // 查询申请状态
        if (one.getStatus() != 0) {
            throw new BizException("审核已完成，无法修改");
        }

        int row = applyMapper.update(apply);
        if (row < 0) {
            throw new BizException("操作失败");
        }
    }

    @Override
    public void save(User user, Apply apply) {
        if (apply == null) {
            throw new BizException("参数不合法");
        }

        // 查询学生是否有未完成的申请
        List<Apply> applyList = applyMapper.findBySid(user.getSid());
        for (Apply a : applyList) {
            if (a.getStatus() == 0) {
                throw new BizException("有待审核的申请，无法重复申请");
            }
        }

        Student student = studentMapper.getById(user.getSid());
        // 判断是否是党员
        if (student.getStatus() == 3) {
            throw new BizException("您已是党员了");
        }

        // 补全apply信息
        apply.setSName(student.getName());
        apply.setType(student.getStatus());
        apply.setSid(student.getId());
        apply.setStatus(0);

        // 判断自身培训分是否够了
        List<Score> scoreList = scoreMapper.getScoreBySid(user.getSid());
        int totalScore = 0;
        if (scoreList != null && !scoreList.isEmpty()) {
            totalScore = scoreList.stream().mapToInt(Score::getScore).sum();
        }

        // 不足80分不能申请预备党员
        if (apply.getType() == 1 && totalScore < 80) {
            throw new BizException("培训分不足80分，无法申请预备党员");
        }

        // 不足160分不能申请党员
        if (apply.getType() == 2 && totalScore < 160) {
            throw new BizException("培训分不足160分，无法申请党员");
        }

        int row = applyMapper.insert(apply);
        if (row < 0) {
            throw new BizException("操作失败");
        }
    }

    /**
     * 分页查询所有申请
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageResult pageQuery(int page, int size) {
        PageHelper.startPage(page, size);
        List<Apply> applyList = applyMapper.findAll();

        PageInfo<Apply> pageInfo = new PageInfo<>(applyList);
        long total = pageInfo.getTotal();
        List<Apply> data = pageInfo.getList();
        return new PageResult(total, page, size, data);
    }

    /**
     * 分页查询某个学生的所有申请
     *
     * @param sid
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageResult pageQueryOne(Integer sid, int page, int size) {
        PageHelper.startPage(page, size);
        List<Apply> applyList = applyMapper.findBySid(sid);
        PageInfo<Apply> pageInfo = new PageInfo<>(applyList);
        long total = pageInfo.getTotal();
        return new PageResult(total, page, size, applyList);
    }
}
