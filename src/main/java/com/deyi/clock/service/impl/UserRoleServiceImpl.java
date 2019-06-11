package com.deyi.clock.service.impl;

import com.deyi.clock.dao.UserRoleMapper;
import com.deyi.clock.domain.UserRole;
import com.deyi.clock.service.UserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName UserRoleServiceImpl
 * @Description TODO
 * @createTime 2019年06月03日 15:37
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public int deleteByPrimaryKey(Integer userId, Integer roleId) {
        return userRoleMapper.deleteByPrimaryKey(userId,roleId);
    }


    @Override
    public int insertSelective(UserRole record) {
        return userRoleMapper.insertSelective(record);
    }

    @Override
    public int deleteByUserId(Integer userId) {
        return userRoleMapper.deleteByUserId(userId);
    }
}
