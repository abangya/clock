package com.deyi.clock.dao;


import java.util.List;
import java.util.Map;

public interface CheckInStatisticsMapper {
    public List<Map<String,Object>> getstatisticsList();

    public List<Map<String,Object>> getAllUserList();

    public List<Map<String,Object>> getDimensionList();

    public int addDayCount(List<Map<String,Object>> mapList);

    public List<Map<String,Object>> getList();
}
