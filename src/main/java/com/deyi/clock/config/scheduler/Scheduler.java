package com.deyi.clock.config.scheduler;

import com.deyi.clock.utils.log.LogUtils;
import org.slf4j.Logger;
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

    //每隔30分执行一次
    @Scheduled(fixedRate = 1000 * 60 * 30)
    public void testTasks1() {
        platformLogger.info("/***************定时任务开始*****************/");

        platformLogger.info("/***************定时任务结束*****************/");
}

    //每天3：05执行
   /* @Scheduled(cron = "0 05 03 ? * *")
    public void testTasks2() {
        System.out.println("定时任务执行时间：" + dateFormat.format(new Date()));
    }*/

}
