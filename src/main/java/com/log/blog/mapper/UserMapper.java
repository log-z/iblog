package com.log.blog.mapper;

import com.log.blog.dto.UserParam;
import com.log.blog.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    User getUserById(@Param("id") String id);

    User getUserByEmail(@Param("email") String email);

    List<User> listUsers(@Param("user") UserParam userParam);

    List<User> findUsers(@Param("user") UserParam userParam);

    boolean insertUser(@Param("user") UserParam userParam);

    boolean updateUser(@Param("user") UserParam feature);

    boolean deleteUser(@Param("userId") String userId);
}
