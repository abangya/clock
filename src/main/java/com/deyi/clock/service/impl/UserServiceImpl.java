package com.deyi.clock.service.impl;

import com.deyi.clock.config.core.Constant;
import com.deyi.clock.config.core.Result;
import com.deyi.clock.config.core.ResultGenerator;
import com.deyi.clock.dao.UserLevelMapper;
import com.deyi.clock.dao.UserMapper;
import com.deyi.clock.dao.UserRoleMapper;
import com.deyi.clock.domain.User;
import com.deyi.clock.domain.UserRole;
import com.deyi.clock.domain.dto.UserDto;
import com.deyi.clock.domain.dto.UserListDto;
import com.deyi.clock.domain.vo.UserVo;
import com.deyi.clock.service.UserService;
import com.deyi.clock.utils.EmptyUtils;
import com.deyi.clock.utils.Md5Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

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
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private UserLevelMapper userLevelMapper;

    @Override
    public User selectUserByName(String userName) {
        return userMapper.selectUserByName(userName);
    }

    @Override
    @Transactional
    public Result insertUser(UserDto userDto) {
        User user = userDto.getUser();
        if(EmptyUtils.isEmpty(user.getPassword())){
            user.setPassword("123456");//初始密码
        }
        String[] arr = Md5Utils.md5(user.getPassword(),user.getUserName());
        user.setPassword(arr[1]);
        user.setSalt(arr[0]);
        user.setCreateTime(new Date());
        user.setCreateUser(((User)SecurityUtils.getSubject().getPrincipal()).getId());
        int i = userMapper.insertUser(user);
        int r=0;
        if(EmptyUtils.isNotEmpty(userDto.getRoleId())){
            List<UserRole> userRoleList = new ArrayList<>();
            String[] roles = userDto.getRoleId().split(",");
            for (String str:roles) {
                UserRole userRole = new UserRole();
                userRole.setUserId(user.getId());
                userRole.setRoleId(Integer.valueOf(str));
                userRoleList.add(userRole);
            }
            r = userRoleMapper.addRoles(userRoleList);
        }else{
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getId());
            userRole.setRoleId(3);
            r = userRoleMapper.insertSelective(userRole);
        }
        if(i>0 && r>0){
            return ResultGenerator.genSuccessResult("","添加成功");
        }else{
            return ResultGenerator.genFailResult("添加失败");
        }
    }

    @Override
    @Transactional
    public Integer deleteUser(Integer id) {
        userLevelMapper.deleteByUserId(id);
        userRoleMapper.deleteByUserId(id);
        return userMapper.deleteUser(id);
    }

    @Override
    @Transactional
    public Result updateUser(UserDto userDto) {
        User user = userDto.getUser();
        user.setUpdateTime(new Date());
        user.setUpdateUser(((User)SecurityUtils.getSubject().getPrincipal()).getId());
        int i = userMapper.updateUser(user);
        userRoleMapper.deleteByUserId(user.getId());
        if(EmptyUtils.isNotEmpty(userDto.getRoleId())){
            List<UserRole> userRoleList = new ArrayList<>();
            String[] roles = userDto.getRoleId().split(",");
            for (String str:roles) {
                UserRole userRole = new UserRole();
                userRole.setRoleId(Integer.valueOf(str));
                userRole.setUserId(user.getId());
                userRoleList.add(userRole);
            }
            userRoleMapper.addRoles(userRoleList);
        }
        if(i>0){
            return ResultGenerator.genSuccessResult("","修改成功");
        }else{
            return ResultGenerator.genFailResult("修改失败");
        }
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
