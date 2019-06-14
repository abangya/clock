package com.deyi.clock.controller.user;

import com.deyi.clock.config.core.Result;
import com.deyi.clock.config.core.ResultGenerator;
import com.deyi.clock.controller.BaseController;
import com.deyi.clock.service.RoleService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName RoleController
 * @Description TODO
 * @createTime 2019年06月06日 16:11
 */
@Controller
@RequestMapping(value = "role")
public class RoleController extends BaseController {

    @Resource
    private RoleService roleService;

    @GetMapping("roles")
    @ResponseBody
    public Result roles(){
        return ResultGenerator.genSuccessResult(roleService.roles());
    }


}
