package com.deyi.clock.controller.user;

import com.deyi.clock.config.core.Result;
import com.deyi.clock.config.core.ResultGenerator;
import com.deyi.clock.controller.BaseController;
import com.deyi.clock.service.CheckInStatisticsService;
import com.deyi.clock.utils.EmptyUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 签到统计controller
 */
@RestController
@RequestMapping(value = "checkInStatistics")
public class CheckInStatisticsController extends BaseController {

    @Autowired
    private CheckInStatisticsService cisService;

    /**
     * @title dayCount
     * @description 触发日统计
     * @author wrz
     * @updateTime 2019/6/12 0012 14:35
     * @return: int
     * @throws
     */
    @GetMapping("dayCount")
    public int dayCount(){
        int i = cisService.dayCount();
        return i;
    }

    /**
     * @title getStatistics
     * @description 统计列表
     * @author lyz
     * @param: type
     * @updateTime 2019/6/12 0012 14:36
     * @return: com.deyi.clock.config.core.Result
     * @throws
     */
    @GetMapping("getDayCountList")
    public Result getDayCountList(
            @RequestParam(value = "type",required = false,defaultValue = "day") String type,
            @RequestParam(value = "startNum",required = false,defaultValue = "1") Integer startNum,
            @RequestParam(value = "size",required = false,defaultValue = "10") Integer size,
            @RequestParam(value = "name",required = false) String name,
            @RequestParam(value = "searchTime",required = false) String searchTime

    ){
        Map<String,Object> map = new HashMap<>();
        map.put("startNum",startNum);
        map.put("size",size);
        map.put("name",name);
        if(EmptyUtils.isNotEmpty(searchTime)){
            String[] searchTimeArr = searchTime.replace(" ","").split("~");
            map.put("startTime",searchTimeArr[0]);
            map.put("endTime",searchTimeArr[1]);
        }
        platformLogger.info("统计查询map:{}",map);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<>();
        switch (type){
            case "day":
                pageInfo = cisService.getListOfDay(map);
                break;
            case "week":
                pageInfo = cisService.getListOfWeek(map);
                break;
            case "month":
                pageInfo = cisService.getListOfMonth(map);
                 break;
            default:
                return ResultGenerator.genFailResult("抱歉，您输入的参数有误！");

        }
        return ResultGenerator.genSuccessResult(pageInfo);
    }

}
