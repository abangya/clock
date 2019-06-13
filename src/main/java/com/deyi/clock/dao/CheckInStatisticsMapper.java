package com.deyi.clock.dao;


import java.util.List;
import java.util.Map;

public interface CheckInStatisticsMapper {
    public List<Map<String,Object>> getstatisticsList();

    public List<Map<String,Object>> getAllUserList();

    public List<Map<String,Object>> getDimensionList();

    public int addDayCount(List<Map<String,Object>> mapList);

    public List<Map<String,Object>> getListOfWeek(Map<String,Object> map);

    public List<Map<String,Object>> getListOfMonth(Map<String,Object> map);

    public List<Map<String,Object>> getListOfDay(Map<String,Object> map);
}
