package com.deyi.clock.service;

import com.deyi.clock.domain.UserLevel;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName UserLevelService
 * @Description TODO
 * @createTime 2019年06月11日 10:10
 */
public interface UserLevelService {

    int deleteByPrimaryKey(Integer id);

    int deleteByUserId(Integer userId);

    int insertSelective(UserLevel record);

    UserLevel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLevel record);

}