package com.buyi.service;

import com.buyi.common.PageResult;
import com.buyi.entity.Apply;
import com.buyi.entity.User;

import java.util.List;

public interface ApplyService {

    List<Apply> findAll();

    void audit(Apply apply);

    List<Apply> findBySid(Integer sid);

    void deleteById(Integer id);

    void update(Apply apply);

    void save(User user, Apply apply);

    PageResult pageQuery(int page, int size);

    PageResult pageQueryOne(Integer sid, int page, int size);
}
