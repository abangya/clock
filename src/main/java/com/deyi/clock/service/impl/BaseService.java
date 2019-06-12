package com.deyi.clock.service.impl;

import com.deyi.clock.utils.log.LogUtils;
import org.slf4j.Logger;

import java.util.UUID;

public class BaseService {
    //获取业务日志logger
    protected final Logger BUSSINESS_LOGGER = LogUtils.getBussinessLogger();
    //获取异常日志logger
    protected final Logger EXCEPTION_LOGGER = LogUtils.getExceptionLogger();
    //获取数据库日志logger
    protected final Logger DB_LOGGER = LogUtils.getDBLogger();
    //获取平台日志logger
    protected final Logger PLATFORM_LOGGER = LogUtils.getPlatformLogger();

    //生成32位UUID
    protected String UUID(){return UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");}
}
