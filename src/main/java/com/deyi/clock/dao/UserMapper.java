package com.deyi.clock.dao;

import com.deyi.clock.domain.User;
import com.deyi.clock.domain.dto.UserListDto;
import com.deyi.clock.domain.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName UserMapper
 * @Description TODO
 * @createTime 2019年05月22日 17:00
 */
public interface UserMapper {

    User selectUserByName(@Param("userName") String userName);

    User selectUserById(@Param("id") Integer id);

    Integer insertUser(User user);

    Integer deleteUser(@Param("id") Integer id);

    Integer updateUser(User user);

    List<UserVo> allUser(UserListDto userListDto);

    Integer allUserCount(UserListDto userListDto);
}
