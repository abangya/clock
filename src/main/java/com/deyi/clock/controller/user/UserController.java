package com.deyi.clock.controller.user;

import com.deyi.clock.config.core.Result;
import com.deyi.clock.config.core.ResultGenerator;
import com.deyi.clock.controller.BaseController;
import com.deyi.clock.domain.User;
import com.deyi.clock.domain.dto.LoginDTO;
import com.deyi.clock.domain.dto.UserListDto;
import com.deyi.clock.domain.vo.UserVo;
import com.deyi.clock.service.UserService;
import com.deyi.clock.utils.EmptyUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

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

    @RequestMapping(value = "find/{userName}",method = RequestMethod.GET)
    @ResponseBody
    public Result findUser(@PathVariable(value = "userName",required = false)String userName){
        System.out.println(userName);
        return  ResultGenerator.genSuccessResult(userService.selectUserByName(userName));
    }

    @RequestMapping(value = "insertUser",method = RequestMethod.POST)
    @ResponseBody
    public Integer insertUser(@RequestBody User user){
        return  userService.insertUser(user);
    }

    @RequestMapping(value = "updateUser",method = RequestMethod.POST)
    @ResponseBody
    public Result updateUser(@RequestBody User user){
        userService.updateUser(user);
        return  ResultGenerator.genSuccessResult("","修改成功");
    }

    @RequiresRoles(value = {"admin"},logical = Logical.OR)
    @GetMapping(value = "deleteUser/{id}")
    @ResponseBody
    public Result deleteUser(@PathVariable(value = "id",required = false)Integer id){
        platformLogger.info("id={}",id);
        return ResultGenerator.genSuccessResult( userService.deleteUser(id),"删除成功!");
    }

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
     * 功能描述: 修改密码
     */
    @RequestMapping("setPwd")
    @ResponseBody
    public Result setP(@RequestParam String password){
        platformLogger.info(password);
        //获取当前登陆的用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        platformLogger.info("进行密码重置{}",user);
       /* int result = userService.updatePwd(user.getSysUserName(),pwd);
        if(result == 0){
            data.put("code",0);
            data.put("msg","修改密码失败！");
            logger.error("用户修改密码失败！");
            return data;
        }
        data.put("code",1);
        data.put("msg","修改密码成功！");
        logger.info("用户修改密码成功！");*/
        return ResultGenerator.genSuccessResult();
    }

}
