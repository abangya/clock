package com.deyi.clock.service.impl;

import com.deyi.clock.dao.UserLevelMapper;
import com.deyi.clock.domain.UserLevel;
import com.deyi.clock.service.UserLevelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName UserLevelServiceImpl
 * @Description TODO
 * @createTime 2019年06月11日 10:10
 */
@Service
public class UserLevelServiceImpl implements UserLevelService {

    @Resource
    private UserLevelMapper userLevelMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return userLevelMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteByUserId(Integer userId) {
        return userLevelMapper.deleteByUserId(userId);
    }

    @Override
    public int insertSelective(UserLevel record) {
        return userLevelMapper.insertSelective(record);
    }

    @Override
    public UserLevel selectByPrimaryKey(Integer id) {
        return userLevelMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(UserLevel record) {
        return userLevelMapper.updateByPrimaryKeySelective(record);
    }
}
