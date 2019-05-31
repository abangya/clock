package com.deyi.clock.dao;

import com.deyi.clock.domain.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName UserMapper
 * @Description TODO
 * @createTime 2019年05月22日 17:00
 */
public interface UserMapper {

    User selectUserByName(@Param("userName") String userName);

    Integer insertUser(User user);

    Integer deleteUser(@Param("id") Integer id);

    Integer updateUser(User user);

}