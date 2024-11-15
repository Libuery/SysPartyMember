package com.buyi.mapper;


import com.buyi.entity.Train;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TrainMapper {

    @Select("select * from train")
    List<Train> findAll();

    @Delete("delete from train where id = #{id}")
    int deleteById(Integer id);
    @Select("select * from train where id = #{id}")
    Train findById(Integer id);

    int update(Train train);

    @Insert("insert into train(name,term,duration,score,status) values (#{name},#{term},#{duration},#{score},0)")
    int insert(Train train);

    @Select("select * from train where status = 0")
    List<Train> findAllActive();
}
