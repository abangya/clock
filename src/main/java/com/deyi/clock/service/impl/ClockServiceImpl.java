package com.deyi.clock.service.impl;

import com.deyi.clock.dao.ClockMapper;
import com.deyi.clock.domain.dto.ClockDto;
import com.deyi.clock.domain.vo.ClockVo;
import com.deyi.clock.service.ClockService;
import com.deyi.clock.utils.DateUtils;
import com.deyi.clock.utils.EmptyUtils;
import com.deyi.clock.utils.MathUtils;
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

    public List<Map<String, Object>> userClock(ClockDto clockDto) {
        //正常打卡次数
        List<Map<String, Object>> levelClockList = this.levelClock(clockDto.getId());
        //人员打卡记录
        List<Map<String, Object>> clockMapList = clockMapper.userClock(clockDto);
        String workClockTime = "";
        //上次区间结束值
        String levelTimeEndStr = "";
        for (int i = 0;i< levelClockList.size();i++) {
            //本次区间正常打卡时间
            String startTime = levelClockList.get(i).get("startTime").toString();
            String endTime = levelClockList.get(i).get("endTime").toString();

            //初始打卡记录状态
            levelClockList.get(i).put("workTime","");
            levelClockList.get(i).put("workTimeMsg","未打卡");
            levelClockList.get(i).put("afterTime","");
            levelClockList.get(i).put("afterTimeMsg","未打卡");

            for (Map<String,Object> clockMap:clockMapList) {
                //本次区间正常打卡时间
                String clockTime = clockMap.get("createTime").toString();
                String createTime = DateUtils.HourMinuteSecond(clockMap.get("createTime").toString());
                //最早打卡，之后不更新，
                if(EmptyUtils.isEmpty(levelClockList.get(i).get("workTime"))){
                    //打卡标识为空
                    if(EmptyUtils.isEmpty(workClockTime)){
                        if(!DateUtils.compTime(createTime,startTime)){
                            //正常打卡
                            levelClockList.get(i).put("workTime",clockTime);
                            levelClockList.get(i).put("workTimeMsg","正常打卡");
                            workClockTime = clockTime;
                        }else{
                            if(!DateUtils.compTime(createTime,endTime)){
                                //区间内打卡，迟到/早退
                                levelClockList.get(i).put("workTime",clockTime);
                                levelClockList.get(i).put("workTimeMsg","迟到打卡");
                                workClockTime = clockTime;
                            }
                        }
                    }else{
                        //下一次区间打卡
                        //小于本次区间起始值
                        if(!DateUtils.compTime(createTime,startTime)){
                            //值大于上一次区间结束值
                            String workClockTimeHHmmss = DateUtils.HourMinuteSecond(workClockTime);
                            if(DateUtils.compTime(createTime,levelTimeEndStr) && DateUtils.compTime(createTime,workClockTimeHHmmss) ){
                                //正常打卡
                                levelClockList.get(i).put("workTime",clockTime);
                                levelClockList.get(i).put("workTimeMsg","正常打卡");
                                workClockTime = clockTime;
                            }
                        }else{
                            //迟到打卡
                            if(!DateUtils.compTime(createTime,endTime)){
                                if(EmptyUtils.isEmpty(levelClockList.get(i).get("workTime"))){
                                    //迟到打卡
                                    levelClockList.get(i).put("workTime",clockTime);
                                    levelClockList.get(i).put("workTimeMsg","迟到打卡");
                                    workClockTime = clockTime;
                                }
                            }
                        }
                    }
                }

                if(EmptyUtils.isNotEmpty(levelClockList.get(i).get("workTime").toString())){
                    //早退打卡
                    if(DateUtils.compTime(endTime,createTime) && DateUtils.compTime(createTime,startTime)){
                        String workClockTimeStr = DateUtils.HourMinuteSecond(levelClockList.get(i).get("workTime").toString());
                        if(DateUtils.compTime(createTime,workClockTimeStr)){
                            //早退打卡
                            levelClockList.get(i).put("afterTime",clockTime);
                            levelClockList.get(i).put("afterTimeMsg","早退打卡");
                            workClockTime = clockTime;
                        }
                    }
                    //大于本次区间的结束值
                    if(DateUtils.compTime(createTime,endTime)){
                        //判断是否有下次区间
                        if(i<levelClockList.size()-1){
                            if(EmptyUtils.isNotEmpty(levelClockList.get(i).get("afterTime"))){
                                //更新下班打卡时间，防止无意早退卡
                                if(DateUtils.compTime(levelClockList.get(i+1).get("startTime").toString(),createTime)){
                                    if(DateUtils.compTime(DateUtils.HourMinuteSecond(levelClockList.get(i).get("afterTime").toString()),endTime) ){
                                        workClockTime = levelClockList.get(i).get("afterTime").toString();
                                        break;
                                    }
                                    levelClockList.get(i).put("afterTime",clockTime);
                                    levelClockList.get(i).put("afterTimeMsg","正常打卡");
                                    workClockTime = clockTime;
                                }else{
                                    workClockTime = levelClockList.get(i).get("afterTime").toString();
                                    break;
                                }
                            }else{
                                levelClockList.get(i).put("afterTime",clockTime);
                                levelClockList.get(i).put("afterTimeMsg","正常打卡");
                                workClockTime = clockTime;
                            }
                        }else{
                            //最后一个区间//取最后值
                            levelClockList.get(i).put("afterTime",clockTime);
                            levelClockList.get(i).put("afterTimeMsg","正常打卡");
                            workClockTime = clockTime;
                        }
                    }
                }else{
                    if(i==levelClockList.size()-1){
                        if(DateUtils.compTime(createTime,endTime)){
                            //判断是否有下次区间
                            levelClockList.get(i).put("afterTime",clockTime);
                            levelClockList.get(i).put("afterTimeMsg","正常打卡");
                            workClockTime = clockTime;
                        }
                    }else{
                       if(DateUtils.compTime(createTime,startTime) && DateUtils.compTime(levelClockList.get(i+1).get("startTime").toString(),createTime)){
                           levelClockList.get(i).put("afterTime",clockTime);
                           levelClockList.get(i).put("afterTimeMsg","正常打卡");
                           workClockTime = clockTime;
                           break;
                       }
                    }
                }
            }
            levelTimeEndStr = endTime;
        }
        return levelClockList;
    }

    @Override
    public List<Map<String, Object>> levelClock(Integer userId) {
        return clockMapper.levelClock(userId);
    }

}
