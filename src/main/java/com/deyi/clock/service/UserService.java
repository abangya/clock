package com.deyi.clock.service;

import com.deyi.clock.domain.User;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName UserService
 * @Description TODO
 * @createTime 2019年05月22日 17:02
 */
public interface UserService {

    User selectUserByName(String userName);

    Integer insertUser(User user);

    Integer deleteUser(Integer id);

    Integer updateUser(User user);

}
