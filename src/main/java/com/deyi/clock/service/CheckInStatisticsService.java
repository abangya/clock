package com.deyi.clock.service;


import java.util.List;
import java.util.Map;

public interface CheckInStatisticsService {
    public List<Map<String,Object>> getstatisticsList();

    public int dayCount();

    public List<Map<String,Object>> getListOfWeek();

    public List<Map<String,Object>> getListOfMonth();
}
