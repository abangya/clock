/*
*
* RoleMapper.java
* @date 2019-06-03
*/
package com.deyi.clock.service;

import com.deyi.clock.domain.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleService {

    int deleteByPrimaryKey(@Param("id") Integer id);

    int insertSelective(Role record);

    Role selectByPrimaryKey(@Param("id") Integer id);

    int updateByPrimaryKeySelective(Role record);

    List<Role> roles();
}