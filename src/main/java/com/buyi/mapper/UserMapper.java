package com.buyi.mapper;

import com.buyi.entity.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    @Select("select * from user where username = #{username}")
    User getByUsername(String username);
    @Select("select * from user where sid = #{sid}")
    User getBySid(Integer sid);
    int insert(User user);

    int update(User user);
}
