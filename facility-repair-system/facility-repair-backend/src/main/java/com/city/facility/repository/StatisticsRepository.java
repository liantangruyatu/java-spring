package com.city.facility.repository;

import com.city.facility.entity.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

/**
 * 统计数据访问层
 */
@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
    
    /**
     * 查询指定日期的统计数据
     */
    List<Statistics> findByStatDate(LocalDate statDate);
    
    /**
     * 查询指定类型和日期的统计
     */
    Optional<Statistics> findByFacilityTypeAndStatDate(String facilityType, LocalDate statDate);
}
