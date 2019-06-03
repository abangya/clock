/*
*
* PermissionMapper.java
* @date 2019-06-03
*/
package com.deyi.clock.service;

import com.deyi.clock.domain.Permission;

public interface PermissionService {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Permission record);

}