package com.city.facility.service.impl;

import com.city.facility.entity.Worker;
import com.city.facility.entity.RepairOrder;
import com.city.facility.repository.WorkerRepository;
import com.city.facility.repository.RepairOrderRepository;
import com.city.facility.service.WorkerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 维修工服务实现类
 */
@Slf4j
@Service
@Transactional
public class WorkerServiceImpl implements WorkerService {
    
    @Autowired
    private WorkerRepository workerRepository;
    
    @Autowired
    private RepairOrderRepository repairOrderRepository;
    
    @Override
    public Worker createWorker(Long userId, String specialization, String phone) {
        Worker worker = Worker.builder()
                .userId(userId)
                .specialization(specialization)
                .phone(phone)
                .status(Worker.WorkerStatus.AVAILABLE)
                .pendingCount(0)
                .build();
        
        return workerRepository.save(worker);
    }
    
    @Override
    public Optional<Worker> getWorkerByUserId(Long userId) {
        return workerRepository.findByUserId(userId);
    }
    
    @Override
    public List<Worker> getAvailableWorkers() {
        return workerRepository.findByStatus(Worker.WorkerStatus.AVAILABLE);
    }
    
    @Override
    public Worker assignRepairOrder(Long repairOrderId) {
        // 查询待办任务最少的可用维修工
        Pageable pageable = PageRequest.of(0, 1);
        List<Worker> workers = workerRepository.findWorkerWithLeastPendingTasks(pageable);
        
        if (workers.isEmpty()) {
            throw new RuntimeException("没有可用的维修工");
        }
        
        Worker worker = workers.get(0);
        RepairOrder repairOrder = repairOrderRepository.findById(repairOrderId)
                .orElseThrow(() -> new RuntimeException("报修单不存在"));
        
        // 分配给该维修工
        repairOrder.setWorkerId(worker.getUserId());
        repairOrder.setStatus(RepairOrder.RepairStatus.ACCEPTED);
        repairOrderRepository.save(repairOrder);
        
        // 增加维修工的待办任务数
        worker.setPendingCount(worker.getPendingCount() + 1);
        if (worker.getPendingCount() > 0) {
            worker.setStatus(Worker.WorkerStatus.BUSY);
        }
        
        Worker savedWorker = workerRepository.save(worker);
        log.info("报修单自动分配给维修工: {}", savedWorker.getId());
        
        return savedWorker;
    }
    
    @Override
    public Worker updatePendingCount(Long workerId, int count) {
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("维修工不存在"));
        
        int newCount = Math.max(0, worker.getPendingCount() + count);
        worker.setPendingCount(newCount);
        
        // 根据任务数更新状态
        if (newCount == 0) {
            worker.setStatus(Worker.WorkerStatus.AVAILABLE);
        } else {
            worker.setStatus(Worker.WorkerStatus.BUSY);
        }
        
        return workerRepository.save(worker);
    }
    
    @Override
    public Worker updateWorkerStatus(Long workerId, String status) {
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("维修工不存在"));
        
        worker.setStatus(Worker.WorkerStatus.valueOf(status.toUpperCase()));
        return workerRepository.save(worker);
    }
}
