package com.deyi.clock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName ViewController
 * @Description templates页面跳转
 * @createTime 2019年06月04日 16:33
 */
@Controller
@RequestMapping(value = "view")
public class ViewController extends BaseController{

    @GetMapping("list")
    public String list(){
        platformLogger.info("跳转打卡管理页面");
        return "views/list";
    }
    @GetMapping("userList")
    public String userList(){
        platformLogger.info("跳转用户管理页面");
        return "views/userList";
    }

    @GetMapping("checkInStatistics")
    public String checkInStatistics(){
        platformLogger.info("跳转签到统计页面");
        return "views/checkInStatistics";
    }
    @RequestMapping("/content")
    public String content() {
        platformLogger.info("跳转首页");
        return "fragments/content";
    }
    @RequestMapping("/roleList")
    public String roleList() {
        platformLogger.info("跳转角色列表页");
        return "views/roleList";
    }
}
