package com.city.facility.service;

import com.city.facility.entity.Worker;

import java.util.List;
import java.util.Optional;

/**
 * 维修工服务接口
 */
public interface WorkerService {
    
    /**
     * 创建维修工
     */
    Worker createWorker(Long userId, String specialization, String phone);
    
    /**
     * 根据用户ID查询维修工
     */
    Optional<Worker> getWorkerByUserId(Long userId);
    
    /**
     * 查询所有可用的维修工
     */
    List<Worker> getAvailableWorkers();
    
    /**
     * 分配报修单到维修工(自动选择任务最少的)
     */
    Worker assignRepairOrder(Long repairOrderId);
    
    /**
     * 更新维修工待办任务数
     */
    Worker updatePendingCount(Long workerId, int count);
    
    /**
     * 更新维修工状态
     */
    Worker updateWorkerStatus(Long workerId, String status);
}
