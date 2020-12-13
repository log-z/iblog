package com.log.blog.mapper;

import com.log.blog.dto.AdminParam;
import com.log.blog.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminMapper {
    Admin getAdminById(@Param("id") String id);

    Admin getAdminByEmail(@Param("email") String email);

    List<Admin> listAdmins(@Param("admin") AdminParam adminParam);

    List<Admin> findAdmins(@Param("admin") AdminParam adminParam);

    boolean insertAdmin(@Param("admin") AdminParam adminParam);

    boolean updateAdmin(@Param("admin") AdminParam adminParam);

    boolean deleteAdmin(@Param("adminId") String adminId);
}
