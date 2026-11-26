package com.city.facility.service.impl;

import com.city.facility.entity.Alarm;
import com.city.facility.entity.RepairOrder;
import com.city.facility.repository.AlarmRepository;
import com.city.facility.repository.RepairOrderRepository;
import com.city.facility.service.AlarmService;
import com.city.facility.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 预警服务实现类
 */
@Slf4j
@Service
@Transactional
public class AlarmServiceImpl implements AlarmService {
    
    @Autowired
    private AlarmRepository alarmRepository;
    
    @Autowired
    private RepairOrderRepository repairOrderRepository;
    
    @Value("${app.repair.timeout-hours:48}")
    private int timeoutHours;
    
    @Override
    public Alarm createAlarm(Long repairOrderId, String alarmType, String alarmContent, String alarmLevel) {
        Alarm alarm = Alarm.builder()
                .repairOrderId(repairOrderId)
                .alarmType(alarmType)
                .alarmContent(alarmContent)
                .alarmLevel(Alarm.AlarmLevel.valueOf(alarmLevel.toUpperCase()))
                .isRead(false)
                .build();
        
        Alarm saved = alarmRepository.save(alarm);
        log.info("预警创建成功: {} - {}", alarmType, alarmContent);
        return saved;
    }
    
    @Override
    public List<Alarm> getUnreadAlarms() {
        return alarmRepository.findByIsReadFalse();
    }
    
    @Override
    public List<Alarm> getUnreadTimeoutAlarms() {
        return alarmRepository.findUnreadTimeoutAlarms();
    }
    
    @Override
    public Alarm markAlarmAsRead(Long alarmId) {
        Alarm alarm = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new RuntimeException("预警不存在"));
        
        alarm.setIsRead(true);
        return alarmRepository.save(alarm);
    }
    
    @Override
    public List<Alarm> getAlarmsByRepairOrderId(Long repairOrderId) {
        return alarmRepository.findByRepairOrderId(repairOrderId);
    }
    
    @Override
    public void checkAndCreateTimeoutAlarms() {
        // 查询所有维修中的报修单
        List<RepairOrder> processingOrders = repairOrderRepository.findAll()
                .stream()
                .filter(ro -> ro.getStatus() == RepairOrder.RepairStatus.PROCESSING)
                .collect(java.util.stream.Collectors.toList());
        
        LocalDateTime threshold = LocalDateTime.now().minusHours(timeoutHours);
        
        for (RepairOrder order : processingOrders) {
            // 如果提交时间早于阈值，则创建超时预警
            if (order.getSubmitTime().isBefore(threshold)) {
                // 检查是否已存在预警
                List<Alarm> existingAlarms = alarmRepository.findByRepairOrderId(order.getId());
                boolean hasTimeoutAlarm = existingAlarms.stream()
                        .anyMatch(a -> "timeout_repair".equals(a.getAlarmType()));
                
                if (!hasTimeoutAlarm) {
                    createAlarm(order.getId(), "timeout_repair",
                            "设施 " + order.getFacilityId() + " 的报修单已超过 " + timeoutHours + " 小时未完成",
                            "HIGH");
                }
            }
        }
        
        log.info("超时预警检查完成");
    }
}
