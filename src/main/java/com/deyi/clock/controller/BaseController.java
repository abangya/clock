package com.deyi.clock.controller;

import com.deyi.clock.utils.log.LogUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;

import java.util.UUID;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName BaseController
 * @Description TODO
 * @createTime 2019年06月03日 09:17
 */
@Controller
public class BaseController {
    //获取业务日志logger
    protected final Logger bussinessLogger = LogUtils.getBussinessLogger();
    //获取异常日志logger
    protected final Logger exceptionLogger = LogUtils.getExceptionLogger();
    //获取数据库日志logger
    protected final Logger dbLogger = LogUtils.getDBLogger();
    //获取平台日志logger
    protected final Logger platformLogger = LogUtils.getPlatformLogger();

    //生成32位UUID
    protected String UUID(){return UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");}
}
