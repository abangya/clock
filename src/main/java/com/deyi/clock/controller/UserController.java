package com.deyi.clock.controller;

import com.deyi.clock.config.core.Result;
import com.deyi.clock.config.core.ResultGenerator;
import com.deyi.clock.domain.User;
import com.deyi.clock.domain.dto.LoginDTO;
import com.deyi.clock.service.UserService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName UserController
 * @Description TODO
 * @createTime 2019年05月22日 17:00
 */
@Controller
@RequestMapping(value = "user")
public class UserController {

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
    public Integer updateUser(@RequestBody User user){
        return  userService.updateUser(user);
    }

    @GetMapping(value = "deleteUser/{id}")
    @ResponseBody
    public Integer deleteUser(@PathVariable(value = "id",required = false)Integer id){
        return  userService.deleteUser(id);
    }

    @RequiresRoles(value = {"admin"},logical = Logical.OR)
    @RequestMapping(value = "/del",method = RequestMethod.GET)
    @ResponseBody
    public String del(Model model) {
        return "删除用户名为wangsaichao用户成功";
    }

}
