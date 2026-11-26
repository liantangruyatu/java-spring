package com.city.facility.service;

import com.city.facility.dto.StatisticsResponse;
import com.city.facility.entity.Statistics;

import java.time.LocalDate;
import java.util.List;

/**
 * 统计服务接口
 */
public interface StatisticsService {
    
    /**
     * 生成指定日期的统计数据
     */
    List<Statistics> generateDailyStatistics(LocalDate date);
    
    /**
     * 获取指定日期的统计数据
     */
    List<StatisticsResponse> getStatistics(LocalDate date);
    
    /**
     * 获取最新的统计数据
     */
    List<StatisticsResponse> getLatestStatistics();
    
    /**
     * 计算故障率
     */
    double calculateFaultRate(String facilityType);
    
    /**
     * 计算平均维修耗时(小时)
     */
    double calculateAvgRepairTime(String facilityType);
}
