package com.log.blog.mapper;

import com.log.blog.dto.Range;
import com.log.blog.entity.User;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;

public interface UserMapper {
    User getUserById(@Param("id") String id) throws SQLException;

    User getUserByEmail(@Param("email") String email) throws SQLException;

    List<User> getAllUsers(@Param("range") Range range) throws SQLException;

    long getUsersCount() throws SQLException;

    void insertUser(@Param("user") User user) throws SQLException;

    void updateUser(@Param("user") User user) throws SQLException;

    void deleteUser(@Param("userId") String userId) throws SQLException;
}
