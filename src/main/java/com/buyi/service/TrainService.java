package com.buyi.service;

import com.buyi.common.PageResult;
import com.buyi.common.Result;
import com.buyi.entity.Train;

import java.util.List;

public interface TrainService {
    List<Train> findAll();

    void deleteById(Integer id);

    Train findById(Integer id);

    void update(Train train);

    void publish(Train train);

    void study(Integer sid, Integer id);

    PageResult pageQuery(int page, int size);

    PageResult pageQuery(Integer sid, int page, int size);
}
