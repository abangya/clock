package com.deyi.clock.service.impl;

import com.deyi.clock.dao.PermissionMapper;
import com.deyi.clock.domain.Permission;
import com.deyi.clock.service.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName PermissionServiceImpl
 * @Description TODO
 * @createTime 2019年06月03日 15:22
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return permissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(Permission record) {
        return permissionMapper.insertSelective(record);
    }

    @Override
    public Permission selectByPrimaryKey(Integer id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Permission record) {
        return permissionMapper.updateByPrimaryKeySelective(record);
    }
}
