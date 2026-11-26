package com.city.facility.service.impl;

import com.city.facility.dto.StatisticsResponse;
import com.city.facility.entity.Statistics;
import com.city.facility.entity.RepairOrder;
import com.city.facility.repository.StatisticsRepository;
import com.city.facility.repository.RepairOrderRepository;
import com.city.facility.repository.PublicFacilityRepository;
import com.city.facility.service.StatisticsService;
import com.city.facility.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 统计服务实现类
 */
@Slf4j
@Service
@Transactional
public class StatisticsServiceImpl implements StatisticsService {
    
    @Autowired
    private StatisticsRepository statisticsRepository;
    
    @Autowired
    private RepairOrderRepository repairOrderRepository;
    
    @Autowired
    private PublicFacilityRepository publicFacilityRepository;
    
    @Override
    public List<Statistics> generateDailyStatistics(LocalDate date) {
        List<Statistics> results = new ArrayList<>();
        
        // 统计各种类型的设施
        String[] facilityTypes = {"STREET_LIGHT", "PARK_BENCH", "PUBLIC_TOILET", "FITNESS_EQUIPMENT"};
        
        for (String facilityType : facilityTypes) {
            int totalCount = publicFacilityRepository.countByFacilityType(
                    com.city.facility.entity.PublicFacility.FacilityType.valueOf(facilityType)
            );
            
            int brokenCount = publicFacilityRepository.countBrokenByFacilityType(
                    com.city.facility.entity.PublicFacility.FacilityType.valueOf(facilityType)
            );
            
            double faultRate = totalCount > 0 ? (double) brokenCount / totalCount * 100 : 0;
            double avgRepairTime = calculateAvgRepairTime(facilityType);
            
            Statistics statistics = Statistics.builder()
                    .facilityType(facilityType)
                    .totalCount(totalCount)
                    .brokenCount(brokenCount)
                    .faultRate(BigDecimal.valueOf(faultRate))
                    .avgRepairTime(BigDecimal.valueOf(avgRepairTime))
                    .statDate(date)
                    .build();
            
            results.add(statisticsRepository.save(statistics));
        }
        
        log.info("生成日期统计数据: {}", date);
        return results;
    }
    
    @Override
    public List<StatisticsResponse> getStatistics(LocalDate date) {
        List<Statistics> statistics = statisticsRepository.findByStatDate(date);
        
        return statistics.stream()
                .map(stat -> StatisticsResponse.builder()
                        .facilityType(stat.getFacilityType())
                        .totalCount(stat.getTotalCount())
                        .brokenCount(stat.getBrokenCount())
                        .faultRate(stat.getFaultRate().doubleValue())
                        .avgRepairTime(stat.getAvgRepairTime().doubleValue())
                        .statDate(stat.getStatDate().toString())
                        .build())
                .collect(Collectors.toList());
    }
    
    @Override
    public List<StatisticsResponse> getLatestStatistics() {
        return getStatistics(LocalDate.now());
    }
    
    @Override
    public double calculateFaultRate(String facilityType) {
        int totalCount = publicFacilityRepository.countByFacilityType(
                com.city.facility.entity.PublicFacility.FacilityType.valueOf(facilityType)
        );
        
        if (totalCount == 0) {
            return 0;
        }
        
        int brokenCount = publicFacilityRepository.countBrokenByFacilityType(
                com.city.facility.entity.PublicFacility.FacilityType.valueOf(facilityType)
        );
        
        return (double) brokenCount / totalCount * 100;
    }
    
    @Override
    public double calculateAvgRepairTime(String facilityType) {
        // 获取所有完成的报修单
        List<RepairOrder> completedOrders = repairOrderRepository.findAll()
                .stream()
                .filter(ro -> ro.getStatus() == RepairOrder.RepairStatus.COMPLETED 
                        && ro.getCompleteTime() != null)
                .collect(Collectors.toList());
        
        if (completedOrders.isEmpty()) {
            return 0;
        }
        
        long totalHours = completedOrders.stream()
                .mapToLong(ro -> DateTimeUtil.getHoursBetween(ro.getSubmitTime(), ro.getCompleteTime()))
                .sum();
        
        return (double) totalHours / completedOrders.size();
    }
}
