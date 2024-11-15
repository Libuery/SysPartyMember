package com.buyi.mapper;

import com.buyi.entity.Score;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface ScoreMapper {

    @Select("select * from score where sid = #{sid}")
    List<Score> getScoreBySid(Integer sid);

    List<Score> getAllScoreBySids(@Param("studentIds") List<Integer> studentIds);

    @Insert("insert into score (sid,tid,score) values (#{sid},#{tid},#{score})")
    int insert(Score score);
}
