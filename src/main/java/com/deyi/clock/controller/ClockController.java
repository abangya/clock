package com.deyi.clock.controller;

import com.deyi.clock.config.core.Result;
import com.deyi.clock.config.core.ResultGenerator;
import com.deyi.clock.domain.dto.ClockDto;
import com.deyi.clock.domain.vo.ClockVo;
import com.deyi.clock.service.ClockService;
import com.deyi.clock.utils.EmptyUtils;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.Clock;
import java.util.*;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName ClockController
 * @Description TODO
 * @createTime 2019年05月28日 17:55
 */
@RestController
@RequestMapping(value = "/clock")
public class ClockController {

    @Resource
    private ClockService clockService;

    @PostMapping("clockAllUser")
    public Result clockAllUser(@RequestBody ClockDto clockDto){
        Map<String,Object> map = new HashMap<>();
        if(EmptyUtils.isNotEmpty(clockDto.getSearchTime())){
            String[] searchTime = clockDto.getSearchTime().split("~");
            clockDto.setStartTime(searchTime[0]);
            clockDto.setEndTime(searchTime[1]);
        }
        if(EmptyUtils.isNotEmpty(clockDto.getStartTime())){
            clockDto.setEndTime(clockDto.getStartTime());
        }

        List<ClockVo> mapList = clockService.clockAllUser(clockDto);
        map.put("pageNum",clockDto.getPageNum());
        map.put("pageSize",clockDto.getPageSize());
        map.put("total",clockDto.getPageSize());
        map.put("list",mapList);
        return ResultGenerator.genSuccessResult(map);
    }

    @PostMapping("userClock")
    public Result userClock(@RequestBody ClockDto clockDto){

        return ResultGenerator.genSuccessResult( clockService.userClock(clockDto));
    }

}