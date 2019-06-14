package com.deyi.clock.controller.user;

import com.deyi.clock.config.core.Result;
import com.deyi.clock.config.core.ResultGenerator;
import com.deyi.clock.controller.BaseController;
import com.deyi.clock.domain.User;
import com.deyi.clock.domain.dto.UserDto;
import com.deyi.clock.domain.dto.UserListDto;
import com.deyi.clock.domain.vo.UserVo;
import com.deyi.clock.service.UserService;
import com.deyi.clock.utils.EmptyUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

import static com.deyi.clock.utils.Md5Utils.md5;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName UserController
 * @Description TODO
 * @createTime 2019年05月22日 17:00
 */
@Controller
@RequestMapping(value = "user")
public class UserController extends BaseController {

    @Resource
    private UserService userService;


    @RequestMapping(value = "updateUser",method = RequestMethod.POST)
    @ResponseBody
    public Result updateUser(@RequestBody User user){
        userService.updateUser(user);
        return  ResultGenerator.genSuccessResult("","修改成功");
    }

    /**
     * @title deleteUser
     * @description 删除用户
     * @author lyz
     * @param: id
     * @updateTime 2019/6/12 0012 11:11
     * @return: com.deyi.clock.config.core.Result
     * @throws
     */
    @RequiresRoles(value = {"admin"},logical = Logical.OR)
    @GetMapping(value = "deleteUser/{id}")
    @ResponseBody
    public Result deleteUser(@PathVariable(value = "id",required = false)Integer id){
        platformLogger.info("id={}",id);
        return ResultGenerator.genSuccessResult( userService.deleteUser(id),"删除成功!");
    }

    /**
     * @title allUser
     * @description 全部用户
     * @author lyz
     * @param: userListDto
     * @updateTime 2019/6/12 0012 11:11
     * @return: com.deyi.clock.config.core.Result
     * @throws
     */
    @RequestMapping(value = "allUser",method = RequestMethod.POST)
    @ResponseBody
    public Result allUser(@RequestBody UserListDto userListDto){
        if(EmptyUtils.isNotEmpty(userListDto.getSearchRoleStr())){
            String [] arr = userListDto.getSearchRoleStr().split(",");
            userListDto.setSearchRole(new HashSet(Arrays.asList(arr)));
        }
        Map<String,Object> map = new HashMap<>();
        if(EmptyUtils.isNotEmpty(userListDto.getSearchTime())){
            String[] searchTime = userListDto.getSearchTime().replace(" ","").split("~");
            userListDto.setStartTime(searchTime[0]);
            userListDto.setEndTime(searchTime[1]);
        }
        List<UserVo> userVoList =  userService.allUser(userListDto);
        map.put("total",userService.allUserCount(userListDto));
        map.put("list",userVoList);
        return  ResultGenerator.genSuccessResult(map);
    }

    /**
     * @title setP
     * @description 修改密码
     * @author lyz
     * @param: password
     * @updateTime 2019/6/12 0012 11:11
     * @return: com.deyi.clock.config.core.Result
     * @throws
     */
    @RequestMapping("setPwd")
    @ResponseBody
    public Result setP(@RequestParam String password){
        Date date = new Date();
        platformLogger.info(password);
        //获取当前登陆的用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        User userTemp = userService.selectUserByName(user.getUserName());
        if(EmptyUtils.isEmpty(userTemp)){
            return ResultGenerator.genFailResult("该用户不存在");
        }
        String[] arr = md5(password,user.getUserName());
        userTemp.setSalt(arr[0]);
        userTemp.setPassword(arr[1]);
        userTemp.setUpdateTime(date);
        userTemp.setUpdateUser(user.getId());
        int result = userService.updateUser(userTemp);
        if(result == 0){
            platformLogger.error("用户修改密码失败！");
            return ResultGenerator.genFailResult("修改密码失败！");
        }

        platformLogger.info("用户修改密码成功！");
        return ResultGenerator.genSuccessResult("","修改密码成功！");
    }

    /**
     * @title setUser
     * @description 新增或修改用户
     * @author lyz
     * @param: user
     * @updateTime 2019/6/12 0012 11:10
     * @return: com.deyi.clock.config.core.Result
     * @throws
     */
    @RequestMapping(value = "/setUser", method = RequestMethod.POST)
    @ResponseBody
    public Result setUser(@RequestBody UserDto user) {
        Result result = new Result();
        platformLogger.info("设置用户[新增或更新]！user:{}",user);
        if(user.getUser().getId() == null){
            result = userService.insertUser(user);
        }else{
            result = userService.updateUser(user);
        }
        return result;
    }
}
