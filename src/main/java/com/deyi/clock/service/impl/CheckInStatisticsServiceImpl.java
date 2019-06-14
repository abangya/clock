package com.deyi.clock.service.impl;

import com.deyi.clock.dao.CheckInStatisticsMapper;
import com.deyi.clock.service.CheckInStatisticsService;
import com.deyi.clock.utils.EmptyUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CheckInStatisticsServiceImpl extends BaseService implements CheckInStatisticsService {
    @Resource
    private CheckInStatisticsMapper cisMapper;

    public List<Map<String,Object>> getstatisticsList(){
        return cisMapper.getstatisticsList();
    }

    public PageInfo<Map<String, Object>> getListOfWeek(Map<String,Object> map){
        PageHelper.startPage((Integer) map.get("startNum"), (Integer) map.get("size"));
        PageInfo<Map<String, Object>> page = new PageInfo<>(cisMapper.getListOfWeek(map));
        return page;
    }

    public  PageInfo<Map<String, Object>> getListOfMonth(Map<String, Object> map){
        PageHelper.startPage((Integer) map.get("startNum"), (Integer) map.get("size"));
        PageInfo<Map<String, Object>> page = new PageInfo<>(cisMapper.getListOfMonth(map));
        return page;
    }

    @Override
    public PageInfo<Map<String, Object>> getListOfDay(Map<String,Object> map){
        PageHelper.startPage((Integer) map.get("startNum"), (Integer) map.get("size"));
        PageInfo<Map<String, Object>> page = new PageInfo<>(cisMapper.getListOfDay(map));
        return page;
    }

    /**
     *
     * @return 0成功，其他的是失败
     */
    public int dayCount(){
        PLATFORM_LOGGER.info("start--CheckInStatisticsServiceImpl---dayCount");
        //1.获取所有员工当日数据
        //这里已经排序过了，打卡记录时间从早到晚
        /*
         * 返回数据
         *  c.id,c.name,c.createTime,c.image,c.ipAddress,l.level
         */
        List<Map<String,Object>> userList = cisMapper.getAllUserList();//源数据
        PLATFORM_LOGGER.info("userList:" + userList.toString());
        //2.获取分级
        /*
         * 返回数据
         * startTime,endTime,parentId,sort
         */
        List<Map<String,Object>> levelList = cisMapper.getDimensionList();
        PLATFORM_LOGGER.info("levelList:" + levelList.toString());
        //3.将分级分组
        //这里是已经排序过了，区间从早到晚，在分组后，区间也是从早到晚的排序
        Map<String,List<Map<String,Object>>> sortLevelMap = new HashMap<String,List<Map<String,Object>>>();
        for (int i=0;i<levelList.size();i++){
            Map<String, Object> iMap = levelList.get(i);
            String levelId = Integer.toString((int)iMap.get("parentId"));
            if(null == sortLevelMap.get(levelId)){
                sortLevelMap.put(levelId,new ArrayList<Map<String,Object>>());
            }
            Map<String,Object> map = new TreeMap<String,Object>();
            map.put("startTime",iMap.get("startTime"));
            map.put("endTime",iMap.get("endTime"));
            sortLevelMap.get(levelId).add(map);
        }
        PLATFORM_LOGGER.info("sortLevelMap:" + sortLevelMap.toString());
        /*
         * 这里的数据结构应该是这样的：
         * {asdf:{姓名:adsf，应打卡次数，实际打卡次数，迟到，早退，未打卡}},
         * asdf:{姓名:adsf，应打卡次数，实际打卡次数，迟到，早退，未打卡}]
         */
        Map<String,Map<String,Object>> countMap = new HashMap<String,Map<String,Object>>();
        //4.对员工打卡情况进行考核
        /*
         * 需要记录的数据：
         * 姓名，应打卡次数，实际打卡次数，迟到，早退，未打卡
         * 区间第一次打卡必是上班卡
         */
        List<Map<String, Object>> handleList = recordHandle(userList, sortLevelMap, countMap);
        //4.将统计结果存入数据库
        PLATFORM_LOGGER.info("end--CheckInStatisticsServiceImpl---dayCount,return:{}",handleList);
        int result=0;
        if(handleList.size()>0){
             result = cisMapper.addDayCount(handleList);
        }
        return result;
    }

    /**
     *
     * @param userList 打卡记录
     * @param sortLevelMap 打卡等级map
     * @param countMap 存储统计结果的map
     * @return
     */
    private List<Map<String,Object>> recordHandle(List<Map<String, Object>> userList, Map<String, List<Map<String, Object>>> sortLevelMap,
                              Map<String, Map<String, Object>> countMap) {
        /* 处理流程：
         * 1.循环打卡记录，准备处理
         * 2.对应当前记录，找到
         *
         * 需要记录的数据
         * 姓名，应打卡次数，实际打卡次数，正常，迟到，早退，未打卡
         */
        for (int i=0;i<userList.size();i++){//循环每一条打卡记录
            Map<String,Object> currentRec = userList.get(i);
            if(EmptyUtils.isEmpty(currentRec.get("levelId"))){
                continue;
            }
            String levelId = Integer.toString((int)currentRec.get("levelId"));//打卡等级
            List<Map<String,Object>> listLevel = sortLevelMap.get(levelId);    //打卡区间的时间集合
            int level = sortLevelMap.get(levelId).size();                      //打卡等级的区间数
            Map<String,Object> cUser;                                          //当前条记录的打卡人
            DateFormat format = new SimpleDateFormat("HH:mm:ss");
            Date createTime = null;                                            //本次打卡的时间
            try {
                String time = format.format((Date) currentRec.get("createTime"));
                createTime = format.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(countMap.containsKey(currentRec.get("name"))){//如果总数统计中已经存在当前用户，就获取该map对象
                cUser = countMap.get(currentRec.get("name"));
            }else{
                int[] checkInFlag = new int[level*2];
                Arrays.fill(checkInFlag,0);
                cUser = new HashMap<String,Object>();
                cUser.put("name",currentRec.get("name"));
                cUser.put("checkInTimes",level*2);          //应打卡数
                cUser.put("actualCheckInTimes",0);         //实际打卡数
                cUser.put("normal",0);                      //正常签到
                cUser.put("late",0);                         //迟到
                cUser.put("leaveEarly",0);                  //早退
                cUser.put("unsigned",level*2);             //未签到
                cUser.put("checkInFlag",checkInFlag);       //每个区间的上、下班签到的情况，奇数为上班卡，偶数为下班卡，0为未打卡，1为正常打卡，2为异常（即迟到或早退）
                cUser.put("userId",currentRec.get("userId"));//对应的userID
            }
            PLATFORM_LOGGER.info("createTime:"+createTime);
            for (int j=0;j<level;){//循环每一个区间
                //只有三种情况，比当前区间早，在区间内，比区间晚
                //说明是比当前区间早
                if(createTime.before((Date)listLevel.get(j).get("startTime"))){
                    //是最早区间，即早上第一次打卡,记录为上班正常打卡
                    if(j == 0){
                        if(((int[])cUser.get("checkInFlag"))[j*2] != 0)
                            break;
                        cUser.put("actualCheckInTimes",(int)cUser.get("actualCheckInTimes")+1);
                        cUser.put("normal",(int)cUser.get("normal")+1);
                        cUser.put("unsigned",(int)cUser.get("unsigned")-1);
                        ((int[])cUser.get("checkInFlag"))[j*2] = 1;
                        break;
                    }
                    //不是最早区间，而且比上个区间的结束时间早，或者上个区间没有下班卡，就跳转到上一区间处理
                    if(createTime.before((Date)listLevel.get(j-1).get("endTime"))
                            || ((int[])cUser.get("checkInFlag"))[j*2-1] == 0){
                        j--;
                        continue;
                    }
                    //比上个区间的结束时间晚，而且有下班卡，记录为当前区间的上班正常打卡
                    cUser.put("actualCheckInTimes",(int)cUser.get("actualCheckInTimes")+1);
                    cUser.put("normal",(int)cUser.get("normal")+1);
                    cUser.put("unsigned",(int)cUser.get("unsigned")-1);
                    ((int[])cUser.get("checkInFlag"))[j*2] = 1;
                    break;
                    //说明是在区间内
                }else if(createTime.after((Date)listLevel.get(j).get("startTime"))
                        && createTime.before((Date)listLevel.get(j).get("endTime"))){
                    //当前区间没有上班卡，记录为迟到上班卡
                    if(((int[])cUser.get("checkInFlag"))[j*2] == 0){
                        cUser.put("actualCheckInTimes",(int)cUser.get("actualCheckInTimes")+1);
                        cUser.put("late",(int)cUser.get("late")+1);
                        cUser.put("unsigned",(int)cUser.get("unsigned")-1);
                        ((int[])cUser.get("checkInFlag"))[j*2] = 2;
                        break;
                    }
                    //当前区间有上班卡，没有下班卡，记录为早退下班卡
                    if(((int[])cUser.get("checkInFlag"))[j*2+1] == 0){
                        cUser.put("actualCheckInTimes",(int)cUser.get("actualCheckInTimes")+1);
                        cUser.put("leaveEarly",(int)cUser.get("leaveEarly")+1);
                        cUser.put("unsigned",(int)cUser.get("unsigned")-1);
                        ((int[])cUser.get("checkInFlag"))[j*2+1] = 2;
                        break;
                    }
                    break;
                    //当前区间有上班卡，也有下班卡，替换前一个下班时间，但是仍然是早退
                    //TODO 因为这里不需要对时间做保存，所以暂时认为不需要做任何操作
                    //说明是比当前区间晚
                } else if(createTime.after((Date)listLevel.get(j).get("endTime"))){
                    //是最晚区间，即晚上最后一次打卡，记录为下班正常打卡
                    if(j == level-1){
                        //意思是当前区间前面已经打过卡了，就不处理了
                        //但是现在有个问题，如果前面打的下班卡是早退，需要处理
                        /* 总共有三种情况，
                         * 0正常处理
                         * 1不处理
                         * 2将之前的早退处理成正常
                         */
                        switch (((int[])cUser.get("checkInFlag"))[j*2+1]){
                            case 0:
                                cUser.put("actualCheckInTimes",(int)cUser.get("actualCheckInTimes")+1);
                                cUser.put("normal",(int)cUser.get("normal")+1);
                                cUser.put("unsigned",(int)cUser.get("unsigned")-1);
                                ((int[])cUser.get("checkInFlag"))[j*2+1] = 1;
                                break;
                            case 1:
                                break;
                            case 2:
                                cUser.put("normal",(int)cUser.get("normal")+1);
                                cUser.put("leaveEarly",(int)cUser.get("leaveEarly")-1);
                                break;
                            default:
                                break;
                        }
                        break;
                    }
                    //不是最晚区间，而且比下个区间的开始时间晚，跳转到下一区间处理
                    if(createTime.after((Date)listLevel.get(j+1).get("startTime"))){
                        j++;
                        continue;
                    }
                    //不是最晚区间，而且比下个区间的开始时间早，如果当前区间无下班打卡，记录为下班正常打卡
                    if(((int[])cUser.get("checkInFlag"))[j*2+1] == 0){
                        cUser.put("actualCheckInTimes",(int)cUser.get("actualCheckInTimes")+1);
                        cUser.put("normal",(int)cUser.get("normal")+1);
                        cUser.put("unsigned",(int)cUser.get("unsigned")-1);
                        ((int[])cUser.get("checkInFlag"))[j*2+1] = 1;
                        break;
                    }
                    //否则记录为下个区间的正常上班打卡
                    cUser.put("actualCheckInTimes",(int)cUser.get("actualCheckInTimes")+1);
                    cUser.put("normal",(int)cUser.get("normal")+1);
                    cUser.put("unsigned",(int)cUser.get("unsigned")-1);
                    ((int[])cUser.get("checkInFlag"))[(j+1)*2] = 1;
                    break;
                }
            }
            countMap.put((String)cUser.get("name"),cUser);
        }
        List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
        for(Map.Entry map:countMap.entrySet()){
            returnList.add((Map<String,Object>)map.getValue());
        }
        return returnList;
    }
}
