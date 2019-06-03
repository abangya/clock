/*
*
* PermissionMapper.java
* @date 2019-06-03
*/
package com.deyi.clock.dao;

import com.deyi.clock.domain.Permission;
import org.apache.ibatis.annotations.Param;

public interface PermissionMapper {

    int deleteByPrimaryKey(@Param("id") Integer id);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(@Param("id")Integer id);

    int updateByPrimaryKeySelective(Permission record);

}