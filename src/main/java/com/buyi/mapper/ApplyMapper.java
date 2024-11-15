package com.buyi.mapper;

import com.buyi.entity.Apply;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface ApplyMapper {

    // 根据status升序
    @Select("select * from apply order by status asc, id desc")
    List<Apply> findAll();

    int update(Apply apply);

    @Select("select * from apply where sid = #{sid} order by status asc")
    List<Apply> findBySid(Integer sid);

    @Delete("delete from apply where id = #{id}")
    int deleteById(Integer id);

    int insert(Apply apply);

    @Select("select * from apply where id = #{id}")
    Apply findById(Integer id);
}
