package com.deyi.clock.service;

import com.deyi.clock.domain.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName UserRoleService
 * @Description TODO
 * @createTime 2019年06月03日 15:34
 */
public interface UserRoleService {

    int deleteByPrimaryKey(Integer userId,Integer roleId);

    int insertSelective(UserRole record);

    int deleteByUserId(Integer userId);

    int addRoles(List<UserRole> userRoleList);
}
