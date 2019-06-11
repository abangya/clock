/*
*
* UserroleMapper.java
* @date 2019-06-03
*/
package com.deyi.clock.dao;

import com.deyi.clock.domain.UserRole;
import org.apache.ibatis.annotations.Param;

public interface UserRoleMapper {

    int deleteByPrimaryKey(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    int insertSelective(UserRole record);

    int deleteByUserId(@Param("userId") Integer userId);
}