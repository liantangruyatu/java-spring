package com.city.facility.service;

import com.city.facility.entity.Alarm;

import java.util.List;

/**
 * 预警服务接口
 */
public interface AlarmService {
    
    /**
     * 创建预警
     */
    Alarm createAlarm(Long repairOrderId, String alarmType, String alarmContent, String alarmLevel);
    
    /**
     * 查询未读预警
     */
    List<Alarm> getUnreadAlarms();
    
    /**
     * 查询未读的超时预警
     */
    List<Alarm> getUnreadTimeoutAlarms();
    
    /**
     * 标记预警为已读
     */
    Alarm markAlarmAsRead(Long alarmId);
    
    /**
     * 查询指定报修单的预警
     */
    List<Alarm> getAlarmsByRepairOrderId(Long repairOrderId);
    
    /**
     * 检查并创建超时预警
     */
    void checkAndCreateTimeoutAlarms();
}
