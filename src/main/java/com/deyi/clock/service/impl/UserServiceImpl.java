package com.deyi.clock.service.impl;

import com.deyi.clock.dao.UserMapper;
import com.deyi.clock.domain.User;
import com.deyi.clock.domain.dto.UserListDto;
import com.deyi.clock.domain.vo.UserVo;
import com.deyi.clock.service.UserService;
import com.deyi.clock.utils.EmptyUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName UserServiceImpl
 * @Description TODO
 * @createTime 2019年05月22日 17:02
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User selectUserByName(String userName) {
        return userMapper.selectUserByName(userName);
    }

    @Override
    public Integer insertUser(User user) {
        return userMapper.insertUser(user);
    }

    @Override
    public Integer deleteUser(Integer id) {
        return userMapper.deleteUser(id);
    }

    @Override
    public Integer updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public List<UserVo> allUser(UserListDto userListDto) {
        if(EmptyUtils.isNotEmpty(userListDto.getStartNum())){
            userListDto.setStartNum((userListDto.getStartNum()-1)*userListDto.getSize());
        }
        List<UserVo> mapList = userMapper.allUser(userListDto);
        return mapList;
    }

    @Override
    public Integer allUserCount(UserListDto userListDto) {
        return userMapper.allUserCount(userListDto);
    }
}
