package com.deyi.clock.controller;

import com.deyi.clock.config.core.Result;
import com.deyi.clock.config.core.ResultGenerator;
import com.deyi.clock.domain.User;
import com.deyi.clock.domain.dto.LoginDto;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName IndexController
 * @Description TODO
 * @createTime 2019年05月28日 10:30
 */
@Controller
public class IndexController extends BaseController {

    /**
     * @throws
     * @title test
     * @description 访问项目根路径
     * @author lyz
     * @param: model
     * @updateTime 2019/6/3 0003 17:06
     * @return: java.lang.String
     */
    @GetMapping(value = "/")
    public String test(Model model) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if (user == null) {
            return "redirect:/login";
        } else {
            return "redirect:/home";
        }
    }

    /**
     * 跳转到login页面
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if (user == null) {
            return "login";
        } else {
            return "redirect:home";
        }
    }
    /**
     * 转发login
     * @return
     */
    @GetMapping(value = "notLogin")
    public String notLogin(Model model) {
        platformLogger.info("退出转发到login");
        return "redirect:/login";
    }

    /**
     * 登录
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Result login(HttpServletRequest request, @RequestBody LoginDto loginDTO, HttpSession session, Model model) {
        platformLogger.info("登录操作{}",loginDTO.toString());
        boolean flag = false;
        if (null != loginDTO.getRememberMe() && loginDTO.getRememberMe().equals("on")) {
            flag = true;
        }
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(loginDTO.getUserName(), loginDTO.getPassword(),flag);
        Subject subject = SecurityUtils.getSubject();
        try {
            //登录操作
            subject.login(usernamePasswordToken);
            // 设置 remenmberMe 的功能

            User user = (User) subject.getPrincipal();
            //更新用户登录时间，也可以在ShiroRealm里面做
            session.setAttribute("user", user);
            model.addAttribute("user", user);
            return ResultGenerator.genSuccessResult(user);
        } /*catch (Exception e) {
            //登录失败从request中获取shiro处理的异常信息 shiroLoginFailure:就是shiro异常类的全类名
            String exception = (String) request.getAttribute("shiroLoginFailure");
            model.addAttribute("msg", e.getMessage());
            //返回登录页面
            return ResultGenerator.genFailResult(e.getMessage());
        }*/catch (IncorrectCredentialsException e) {
            return ResultGenerator.genFailResult("密码错误，请重新输入！");
        } catch (LockedAccountException e) {
            return ResultGenerator.genFailResult("登录失败，该用户已被冻结！");
        } catch (AuthenticationException e) {
            return ResultGenerator.genFailResult("该用户不存在！");
        } catch (Exception e) {
            return ResultGenerator.genFailResult("未知信息！");
        }
    }

    @RequestMapping("/home")
    public String index(HttpSession session, Model model) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if (user == null) {
            return "login";
        } else {
            model.addAttribute("user", user);
            return "home";
        }
    }

    /**
     * 登出  这个方法没用到,用的是shiro默认的logout
     *
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("logout")
    public String logout(HttpSession session, Model model) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        model.addAttribute("msg", "安全退出！");
        platformLogger.info("退出"+subject.getPrincipal());
        return "login";
    }

    /**
     * 跳转到无权限页面
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("/unauthorized")
    public String unauthorized(HttpSession session, Model model) {
        platformLogger.info("跳转无权限页面");
        return "unauthorized";
    }

}