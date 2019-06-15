/*
*
* RoleMapper.java
* @date 2019-06-03
*/
package com.deyi.clock.service.impl;

import com.deyi.clock.dao.RoleMapper;
import com.deyi.clock.domain.Role;
import com.deyi.clock.service.RoleService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return roleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(Role record) {
        return roleMapper.insertSelective(record);
    }

    @Override
    public Role selectByPrimaryKey(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Role record) {
        return roleMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<Role> roles() {
        return roleMapper.roles();
    }

    @Override
    public List<Role> roleList() {
        return roleMapper.roleList();
    }
}