package com.deyi.clock.controller;

import com.deyi.clock.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName IndexController
 * @Description TODO
 * @createTime 2019年05月28日 10:30
 */
@Controller
public class IndexController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @GetMapping(value = "/")
    public String test(Model model) {
        return "login";
    }
    @RequestMapping("home")
    public String home(){
        logger.info("定向主页");
        return "home";
    }
    @RequestMapping("content")
    public String content(){
        logger.info("定向主页");
        return "fragments/content";
    }
}
