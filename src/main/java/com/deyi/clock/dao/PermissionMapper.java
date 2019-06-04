/*
*
* PermissionMapper.java
* @date 2019-06-03
*/
package com.deyi.clock.dao;

import com.deyi.clock.domain.Permission;
import com.deyi.clock.domain.Role;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

public interface PermissionMapper {

    int deleteByPrimaryKey(@Param("id") Integer id);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(@Param("id")Integer id);

    int updateByPrimaryKeySelective(Permission record);

    Set<Permission> findPermissionsByRoleId(@Param("roles") Set<Role> roles);

}