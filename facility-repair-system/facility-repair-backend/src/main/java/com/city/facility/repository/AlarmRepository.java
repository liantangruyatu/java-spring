package com.city.facility.repository;

import com.city.facility.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 预警数据访问层
 */
@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    
    /**
     * 查询未读的预警
     */
    List<Alarm> findByIsReadFalse();
    
    /**
     * 查询指定报修单的预警
     */
    List<Alarm> findByRepairOrderId(Long repairOrderId);
    
    /**
     * 查询指定类型的预警
     */
    List<Alarm> findByAlarmType(String alarmType);
    
    /**
     * 查询所有未读的超时预警
     */
    @Query("SELECT a FROM Alarm a WHERE a.isRead = false AND a.alarmType = 'timeout_repair'")
    List<Alarm> findUnreadTimeoutAlarms();
}
