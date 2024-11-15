package com.buyi.service.impl;

import com.buyi.common.PageResult;
import com.buyi.common.Result;
import com.buyi.entity.Score;
import com.buyi.entity.Train;
import com.buyi.entity.vo.TrainVo;
import com.buyi.exception.BizException;
import com.buyi.mapper.ScoreMapper;
import com.buyi.mapper.TrainMapper;
import com.buyi.service.TrainService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainServiceImpl implements TrainService {

    @Resource
    private TrainMapper trainMapper;
    @Resource
    private ScoreMapper scoreMapper;

    @Override
    public List<Train> findAll() {
        return trainMapper.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        if (id == null) {
            throw new BizException("参数不合法");
        }
        int row = trainMapper.deleteById(id);
        if (row < 0) {
            throw new BizException("删除失败");
        }
    }

    @Override
    public Train findById(Integer id) {
        return trainMapper.findById(id);
    }

    @Override
    public void update(Train train) {
        if (train == null) {
            throw new BizException("参数不合法");
        }
        int row = trainMapper.update(train);
        if (row < 0) {
            throw new BizException("修改失败");
        }
    }

    @Override
    public void publish(Train train) {
        if (train == null) {
            throw new BizException("参数不合法");
        }
        int row = trainMapper.insert(train);
        if (row < 0) {
            throw new BizException("发布失败");
        }
    }

    @Override
    @Transactional
    public void study(Integer sid, Integer id) {
        if (id == null) {
            throw new BizException("参数不合法");
        }
        Train train = trainMapper.findById(id);
        // 1.在score中插入一条学习数据
        Score score = new Score();
        score.setScore(train.getScore());
        score.setSid(sid);
        score.setTid(id);
        int row1 = scoreMapper.insert(score);

        // 2.修改train中的count数量 count +1
        train.setCount(train.getCount() + 1);
        int row2 = trainMapper.update(train);

        if (row1 < 0 || row2 < 0) {
            throw new BizException("操作失败");
        }
    }

    @Override
    public PageResult pageQuery(int page, int size) {
        PageHelper.startPage(page, size);
        List<Train> list = trainMapper.findAll();
        PageInfo<Train> pageInfo = new PageInfo<>(list);

        long total = pageInfo.getTotal();
        List<Train> data = pageInfo.getList();
        return new PageResult(total, page, size, data);
    }

    /**
     * 根据学生ID查询分页的培训课程信息，并标记学生已学习的课程
     * 
     * @param sid 学生ID，用于查询学生已学习的课程
     * @param page 当前页码，用于分页查询
     * @param size 每页大小，用于分页查询
     * @return 返回包含培训课程信息和分页数据的PageResult对象
     */
    @Override
    public PageResult pageQuery(Integer sid, int page, int size) {
        // 开始分页查询
        PageHelper.startPage(page, size);
        
        // 查询所有启用状态的培训课程
        List<Train> trainList = trainMapper.findAllActive();
        
        // 初始化PageInfo对象，用于获取总记录数等信息
        PageInfo<Train> pageInfo = new PageInfo<>(trainList);
        long total = pageInfo.getTotal();
    
        // 根据学生ID查询学生已学习的课程分数
        List<Score> scoreList = scoreMapper.getScoreBySid(sid);
    
        // 将培训课程列表转换为TrainVo列表
        List<TrainVo> trainVoList = trainList.stream().map(train -> {
            TrainVo trainVo = new TrainVo();
            BeanUtils.copyProperties(train, trainVo);
            return trainVo;
        }).toList();
    
        // 提取已学习课程的培训ID列表
        List<Integer> tidlist = scoreList.stream().map(Score::getTid).toList();
        
        // 标记学生已学习的课程
        for (TrainVo trainVo : trainVoList) {
            if (tidlist.contains(trainVo.getId())) {
                trainVo.setLearned(true);
            }
        }
    
        // 返回分页结果
        return new PageResult(total, page, size, trainVoList);
    }
}
