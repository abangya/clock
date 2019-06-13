package com.deyi.clock.service;


import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface CheckInStatisticsService {
    public List<Map<String,Object>> getstatisticsList();

    public int dayCount();

    public PageInfo<Map<String, Object>> getListOfWeek(Map<String,Object> map);

    public PageInfo<Map<String, Object>> getListOfMonth(Map<String,Object> map);

    public PageInfo<Map<String, Object>> getListOfDay(Map<String,Object> map);
}
