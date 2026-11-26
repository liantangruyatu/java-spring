package com.city.facility.controller;

import com.city.facility.dto.ApiResponse;
import com.city.facility.dto.StatisticsResponse;
import com.city.facility.service.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 统计控制器
 */
@Slf4j
@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    
    @Autowired
    private StatisticsService statisticsService;
    
    /**
     * 生成日统计数据
     */
    @PostMapping("/generate")
    public ApiResponse<?> generateDailyStatistics(@RequestParam(required = false) String date) {
        try {
            LocalDate statDate = date != null ? LocalDate.parse(date) : LocalDate.now();
            statisticsService.generateDailyStatistics(statDate);
            
            return ApiResponse.success("统计数据生成成功");
        } catch (Exception e) {
            log.error("生成统计数据失败: {}", e.getMessage());
            return ApiResponse.error(500, e.getMessage());
        }
    }
    
    /**
     * 获取指定日期的统计数据
     */
    @GetMapping("/date/{date}")
    public ApiResponse<List<StatisticsResponse>> getStatisticsByDate(@PathVariable String date) {
        try {
            List<StatisticsResponse> statistics = statisticsService.getStatistics(LocalDate.parse(date));
            return ApiResponse.success(statistics);
        } catch (Exception e) {
            log.error("查询统计数据失败: {}", e.getMessage());
            return ApiResponse.error(500, e.getMessage());
        }
    }
    
    /**
     * 获取最新的统计数据
     */
    @GetMapping("/latest")
    public ApiResponse<List<StatisticsResponse>> getLatestStatistics() {
        try {
            List<StatisticsResponse> statistics = statisticsService.getLatestStatistics();
            return ApiResponse.success(statistics);
        } catch (Exception e) {
            log.error("查询统计数据失败: {}", e.getMessage());
            return ApiResponse.error(500, e.getMessage());
        }
    }
}
