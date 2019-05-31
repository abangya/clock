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

        //正常打卡次数
            List<Map<String, Object>> levelClock = this.levelClock(clockDto.getId());
            //人员打卡记录
            List<Map<String, Object>> mapList = clockMapper.userClock(clockDto);
            String endClockStr = "";//上一次记录时间
            String endStr = "";//上一次区间结束时间
            for (int i = 0;i<levelClock.size();i++){
            //本次区间正常打卡时间
            String startTime = levelClock.get(i).get("startTime").toString();
            String endTime = levelClock.get(i).get("endTime").toString();

            //初始打卡记录状态
            levelClock.get(i).put("workTime","");
            levelClock.get(i).put("workTimeMsg","未打卡");
            levelClock.get(i).put("afterTime","");
            levelClock.get(i).put("afterTimeMsg","未打卡");

            for (int j = 0;j < mapList.size();j++){
                //本次循环打卡时间
                String createTime = DateUtils.HourMinuteSecond(mapList.get(j).get("createTime").toString());
                //没有上班打卡记录
                //上班打卡
                if(EmptyUtils.isEmpty(levelClock.get(i).get("workTime"))){
                    if(!DateUtils.compTime(createTime,startTime)){
                        if(EmptyUtils.isNotEmpty(endClockStr)){
                            //取出上一次打卡时间的时分秒
                            //不用同一时刻打卡
                            String endStrHHmmss = DateUtils.HourMinuteSecond(endClockStr);
                            if(!DateUtils.compTime(createTime,startTime) && DateUtils.compTime(createTime,endStr) && DateUtils.compTime(createTime,endStrHHmmss)){
                                //第一次正常打卡时间之前
                                levelClock.get(i).put("workTime",mapList.get(j).get("createTime"));
                                levelClock.get(i).put("workTimeMsg","打卡成功");
                                //更新打卡时间
                                endClockStr = mapList.get(j).get("createTime").toString();
                            }
                        }else{
                            //第一次正常打卡时间之前
                            levelClock.get(i).put("workTime",mapList.get(j).get("createTime"));
                            levelClock.get(i).put("workTimeMsg","打卡成功");
                            //更新打卡时间
                            endClockStr = mapList.get(j).get("createTime").toString();
                        }
                    }
                    //迟到打卡时间
                    if(DateUtils.compTime(createTime,startTime) && !DateUtils.compTime(createTime,endTime)){
                        levelClock.get(i).put("workTime",mapList.get(j).get("createTime").toString());
                        levelClock.get(i).put("workTimeMsg","迟到");
                        endClockStr = mapList.get(j).get("createTime").toString();
                    }
                }
                //下班打卡
                if(EmptyUtils.isNotEmpty(levelClock.get(i).get("workTime"))){
                    //早退打卡时间
                    if(DateUtils.compTime(createTime,startTime) && !DateUtils.compTime(createTime,endTime)){
                        if(!mapList.get(j).get("createTime").toString().equals(levelClock.get(i).get("workTime").toString())){
                            levelClock.get(i).put("afterTime",mapList.get(j).get("createTime"));
                            levelClock.get(i).put("afterTimeMsg","早退");
                            //更新打卡时间
                            endClockStr = mapList.get(j).get("createTime").toString();
                        }
                    }
                    //正常打卡时间
                    if(DateUtils.compTime(createTime,endTime)){
                        //判断是否有下个区间
                        if( i < levelClock.size()-1){
                            if(EmptyUtils.isNotEmpty( levelClock.get(i).get("afterTime"))){
                                String timeTemp = DateUtils.HourMinuteSecond(levelClock.get(i).get("afterTime").toString());
                                //小于下个区间的起始值
                                if(!DateUtils.compTime(timeTemp,startTime)){
                                    if(!DateUtils.compTime(createTime,levelClock.get(i+1).get("startTime").toString())){
                                        levelClock.get(i).put("afterTime",mapList.get(j).get("createTime"));
                                        levelClock.get(i).put("afterTimeMsg","打卡成功");
                                        //更新打卡时间
                                        endClockStr = mapList.get(j).get("createTime").toString();
                                    }
                                }
                            }else{
                                levelClock.get(i).put("afterTime",mapList.get(j).get("createTime"));
                                levelClock.get(i).put("afterTimeMsg","打卡成功");
                                //更新打卡时间
                                endClockStr = mapList.get(j).get("createTime").toString();
                            }
                        }else{
                            levelClock.get(i).put("afterTime",mapList.get(j).get("createTime"));
                            levelClock.get(i).put("afterTimeMsg","打卡成功");
                            //更新打卡时间
                            endClockStr = mapList.get(j).get("createTime").toString();
                        }
                    }
                }/*else{
                     //上班忘记打卡
                    if(DateUtils.compTime(createTime,endTime)){
                        for(int l = levelClock.size()-1;l>=0;l--){
                            if(DateUtils.compTime(createTime,levelClock.get(l).get("endTime").toString())){
                                levelClock.get(l).put("afterTime",mapList.get(j).get("createTime").toString());
                                levelClock.get(l).put("afterTimeMsg","打卡成功4");
                                endClockStr = mapList.get(j).get("createTime").toString();
                                break;
                            }
                        }
                    }
                }*/
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
