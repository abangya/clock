package com.deyi.clock.service.impl;

import com.deyi.clock.dao.ClockMapper;
import com.deyi.clock.domain.dto.ClockDto;
import com.deyi.clock.domain.vo.ClockVo;
import com.deyi.clock.service.ClockService;
import com.deyi.clock.utils.DateUtils;
import com.deyi.clock.utils.EmptyUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName ClockServiceImpl
 * @Description TODO
 * @createTime 2019年05月28日 17:53
 */
@Service
public class ClockServiceImpl implements ClockService {

    @Resource
    private ClockMapper clockMapper;

    @Override
    public List<ClockVo> clockAllUser(ClockDto clockDto) {
        Map<String,Object> mapTemp = new HashMap<>();
        mapTemp.put("startNum",(clockDto.getPageNum()-1)*clockDto.getPageSize());
        mapTemp.put("endNum",clockDto.getPageSize());
        mapTemp.put("userName",clockDto.getUserName());
        mapTemp.put("startTime",clockDto.getStartTime());
        mapTemp.put("endTime",clockDto.getEndTime());
        List<ClockVo> mapList = clockMapper.clockAllUser(mapTemp);
        for (ClockVo map:mapList) {
            if(EmptyUtils.isEmpty(map.getUserName())){
                map.setUserName(map.getClockName());
            }
        }
        return mapList;
    }

    @Override
    public List<Map<String, Object>> userClock(ClockDto clockDto) {
        List<Map<String, Object>> mapList = clockMapper.userClock(clockDto);
        //正常打卡次数
        List<Map<String, Object>> levelClock = this.levelClock(clockDto.getId());
        String endClockStr = "";//上一次区间结束时间
        String endStr = "";
        for (int i = 0;i < levelClock.size(); i++) {
            //正常打卡时间
            String startTime = String.valueOf(levelClock.get(i).get("startTime"));
            //下班时间
            String endTime = String.valueOf(levelClock.get(i).get("endTime"));
            levelClock.get(i).put("workTime","");
            levelClock.get(i).put("workTimeMsg","未打卡");
            levelClock.get(i).put("afterTime","");
            levelClock.get(i).put("afterTimeMsg","未打卡");
            for (int j = 0;j<mapList.size(); j++) {

                //正常上班打卡时间
                String createTime = DateUtils.HourMinuteSecond(String.valueOf(mapList.get(j).get("createTime")));

                if(EmptyUtils.isNotEmpty(levelClock.get(i).get("afterTime"))){
                    //如果下班已经存在，则区间结束
                    break;
                }

                //上班打卡
                if(EmptyUtils.isEmpty(levelClock.get(i).get("workTime"))){
                    if(!DateUtils.compTime(createTime,startTime)){
                        if(EmptyUtils.isNotEmpty(endClockStr)){
                            String endStrHHmmss = DateUtils.HourMinuteSecond(endClockStr);
                            //下个区间
                            if(!DateUtils.compTime(createTime,startTime) && DateUtils.compTime(createTime,endStrHHmmss) && DateUtils.compTime(createTime,endStr)){
                                levelClock.get(i).put("workTime",mapList.get(j).get("createTime"));
                                levelClock.get(i).put("workTimeMsg","打卡成功");
                            }
                        }else{
                            //正常打卡时间之前
                            levelClock.get(i).put("workTime",mapList.get(j).get("createTime"));
                            levelClock.get(i).put("workTimeMsg","打卡成功");
                            //更新打卡时间
                            endClockStr = mapList.get(j).get("createTime").toString();
                        }
                    }
                    //迟到打卡时间
                    if(DateUtils.compTime(createTime,startTime) && !DateUtils.compTime(createTime,endTime)){
                        levelClock.get(i).put("workTime",mapList.get(j).get("createTime"));
                        levelClock.get(i).put("workTimeMsg","迟到");
                        endClockStr = mapList.get(j).get("createTime").toString();
                    }

                }

                //下班打卡
                if(EmptyUtils.isNotEmpty(levelClock.get(i).get("workTime"))){
                    //早退打卡时间
                    if(DateUtils.compTime(createTime,startTime) && !DateUtils.compTime(createTime,endTime)){
                        if(!mapList.get(j).get("createTime").equals(levelClock.get(i).get("workTime"))){
                            levelClock.get(i).put("afterTime",mapList.get(j).get("createTime"));
                            levelClock.get(i).put("afterTimeMsg","早退");
                            //更新打卡时间
                            endClockStr = mapList.get(j).get("createTime").toString();
                        }
                    }
                    //正常下班打卡时间
                    if(DateUtils.compTime(createTime,endTime) && EmptyUtils.isEmpty(levelClock.get(i).get("afterTime"))){
                        levelClock.get(i).put("afterTime",mapList.get(j).get("createTime"));
                        levelClock.get(i).put("afterTimeMsg","打卡成功");
                        //更新打卡时间
                        endClockStr = mapList.get(j).get("createTime").toString();
                    }
                }
            }
            endStr = endTime;
        }
        return levelClock;
    }

    @Override
    public List<Map<String, Object>> levelClock(Integer userId) {
        return clockMapper.levelClock(userId);
    }

}
