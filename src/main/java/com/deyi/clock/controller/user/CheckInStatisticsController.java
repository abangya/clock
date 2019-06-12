package com.deyi.clock.controller.user;

import com.deyi.clock.config.core.Result;
import com.deyi.clock.config.core.ResultGenerator;
import com.deyi.clock.service.CheckInStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 签到统计controller
 */
@RestController("checkInStatistics")
public class CheckInStatisticsController {

    @Autowired
    private CheckInStatisticsService cisService;

    @GetMapping("dayCount")
    public int dayCount(){
        int i = cisService.dayCount();
        return i;
    }

    @GetMapping("getStatistics")
    public Result getStatistics(){
        return ResultGenerator.genSuccessResult(cisService.getList());
    }

}
