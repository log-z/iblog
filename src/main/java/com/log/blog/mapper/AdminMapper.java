package com.log.blog.mapper;

import com.log.blog.dto.Range;
import com.log.blog.entity.Admin;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;

public interface AdminMapper {
    Admin getAdminById(@Param("id") String id) throws SQLException;

    Admin getAdminByEmail(@Param("email") String email) throws SQLException;

    List<Admin> getAllAdmin(@Param("range") Range range) throws SQLException;

    void insertAdmin(@Param("admin") Admin admin) throws SQLException;

    void deleteAdmin(@Param("adminId") String adminId) throws SQLException;
}
