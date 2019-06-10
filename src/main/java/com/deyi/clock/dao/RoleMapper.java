/*
*
* RoleMapper.java
* @date 2019-06-03
*/
package com.deyi.clock.dao;

import com.deyi.clock.domain.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface RoleMapper {

    int deleteByPrimaryKey(@Param("id") Integer id);

    int insertSelective(Role record);

    Role selectByPrimaryKey(@Param("id")Integer id);

    int updateByPrimaryKeySelective(Role record);

    Set<Role> findRolesByUserId(@Param("userId") Integer userId);

    List<Role> roles();
}