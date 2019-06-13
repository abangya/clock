package com.deyi.clock.config.scheduler;

import com.deyi.clock.service.CheckInStatisticsService;
import com.deyi.clock.utils.log.LogUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Set;

/**
 * @program: xiaowantong
 * @Date: 2019/3/22 14:09
 * @Author: lyz
 * @Description:
 */
@Component
public class Scheduler {

    private final Logger platformLogger = LogUtils.getPlatformLogger();

    @Autowired
    private CheckInStatisticsService cisService;

    /*
     * @title cisTasks
     * @description 每天凌晨1点执行一次
     * @author lyz
     * @updateTime 2019/6/12 0012 17:45
     * @throws
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void cisTasks() {
        platformLogger.info("/***************定时任务开始*****************/");
        int i = cisService.dayCount();
        platformLogger.info("插入了"+i+"条");
        platformLogger.info("/***************定时任务结束*****************/");
    }
}
