package com.deyi.clock.service;

import com.deyi.clock.config.core.Result;
import com.deyi.clock.domain.User;
import com.deyi.clock.domain.dto.UserDto;
import com.deyi.clock.domain.dto.UserListDto;
import com.deyi.clock.domain.vo.UserVo;

import java.util.List;
import java.util.Map;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName UserService
 * @Description TODO
 * @createTime 2019年05月22日 17:02
 */
public interface UserService {

    User selectUserByName(String userName);

    User selectUserById(Integer id);

    Result insertUser(UserDto user);

    Integer deleteUser(Integer id);

    Result updateUser(UserDto user);

    Integer updateUser(User user);

    List<UserVo> allUser(UserListDto userListDto);

    Integer allUserCount(UserListDto userListDto);

}
