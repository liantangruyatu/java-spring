package com.city.facility.service.impl;

import com.city.facility.dto.RepairOrderResponse;
import com.city.facility.entity.RepairOrder;
import com.city.facility.entity.User;
import com.city.facility.entity.PublicFacility;
import com.city.facility.repository.RepairOrderRepository;
import com.city.facility.repository.UserRepository;
import com.city.facility.repository.PublicFacilityRepository;
import com.city.facility.service.RepairOrderService;
import com.city.facility.service.WorkerService;
import com.city.facility.util.CodeGenerator;
import com.city.facility.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 报修单服务实现类
 */
@Slf4j
@Service
@Transactional
public class RepairOrderServiceImpl implements RepairOrderService {
    
    @Autowired
    private RepairOrderRepository repairOrderRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PublicFacilityRepository publicFacilityRepository;
    
    @Autowired
    private WorkerService workerService;
    
    @Override
    public RepairOrder createRepairOrder(Long facilityId, Long userId, String problemDescription, String images) {
        // 验证设施是否存在
        PublicFacility facility = publicFacilityRepository.findById(facilityId)
                .orElseThrow(() -> new RuntimeException("设施不存在"));
        
        // 验证用户是否存在
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        
        // 创建报修单
        RepairOrder repairOrder = RepairOrder.builder()
                .repairCode(CodeGenerator.generateRepairCode())
                .facilityId(facilityId)
                .userId(userId)
                .problemDescription(problemDescription)
                .images(images)
                .status(RepairOrder.RepairStatus.SUBMITTED)
                .build();
        
        RepairOrder savedOrder = repairOrderRepository.save(repairOrder);
        
        // 自动分配给任务最少的维修工
        try {
            workerService.assignRepairOrder(savedOrder.getId());
        } catch (Exception e) {
            log.warn("自动分配维修工失败: {}", e.getMessage());
        }
        
        log.info("报修单创建成功: {}", savedOrder.getRepairCode());
        return savedOrder;
    }
    
    @Override
    public Page<RepairOrder> getRepairOrders(Pageable pageable) {
        return repairOrderRepository.findAll(pageable);
    }
    
    @Override
    public Page<RepairOrder> getUserRepairOrders(Long userId, Pageable pageable) {
        return repairOrderRepository.findByUserId(userId, pageable);
    }
    
    @Override
    public Page<RepairOrder> getWorkerRepairOrders(Long workerId, Pageable pageable) {
        return repairOrderRepository.findByWorkerId(workerId, pageable);
    }
    
    @Override
    public List<RepairOrder> getFacilityRepairOrders(Long facilityId) {
        return repairOrderRepository.findByFacilityId(facilityId);
    }
    
    @Override
    public RepairOrder acceptRepairOrder(Long repairOrderId, Long workerId) {
        RepairOrder repairOrder = repairOrderRepository.findById(repairOrderId)
                .orElseThrow(() -> new RuntimeException("报修单不存在"));
        
        User worker = userRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("维修工不存在"));
        
        repairOrder.setWorkerId(workerId);
        repairOrder.setStatus(RepairOrder.RepairStatus.ACCEPTED);
        repairOrder.setAcceptTime(LocalDateTime.now());
        
        return repairOrderRepository.save(repairOrder);
    }
    
    @Override
    public RepairOrder startRepair(Long repairOrderId) {
        RepairOrder repairOrder = repairOrderRepository.findById(repairOrderId)
                .orElseThrow(() -> new RuntimeException("报修单不存在"));
        
        repairOrder.setStatus(RepairOrder.RepairStatus.PROCESSING);
        
        // 同时更新设施状态为维修中
        PublicFacility facility = publicFacilityRepository.findById(repairOrder.getFacilityId())
                .orElseThrow(() -> new RuntimeException("设施不存在"));
        
        facility.setStatus(PublicFacility.FacilityStatus.REPAIRING);
        publicFacilityRepository.save(facility);
        
        return repairOrderRepository.save(repairOrder);
    }
    
    @Override
    public RepairOrder completeRepair(Long repairOrderId, String feedback) {
        RepairOrder repairOrder = repairOrderRepository.findById(repairOrderId)
                .orElseThrow(() -> new RuntimeException("报修单不存在"));
        
        repairOrder.setStatus(RepairOrder.RepairStatus.COMPLETED);
        repairOrder.setCompleteTime(LocalDateTime.now());
        repairOrder.setFeedback(feedback);
        
        // 同时更新设施状态为正常
        PublicFacility facility = publicFacilityRepository.findById(repairOrder.getFacilityId())
                .orElseThrow(() -> new RuntimeException("设施不存在"));
        
        facility.setStatus(PublicFacility.FacilityStatus.NORMAL);
        publicFacilityRepository.save(facility);
        
        // 更新维修工的待办任务数
        if (repairOrder.getWorkerId() != null) {
            workerService.updatePendingCount(repairOrder.getWorkerId(), -1);
        }
        
        log.info("报修单完成: {}", repairOrder.getRepairCode());
        return repairOrderRepository.save(repairOrder);
    }
    
    @Override
    public Optional<RepairOrder> getRepairOrderById(Long id) {
        return repairOrderRepository.findById(id);
    }
    
    @Override
    public RepairOrderResponse toResponse(RepairOrder repairOrder) {
        if (repairOrder == null) {
            return null;
        }
        
        PublicFacility facility = publicFacilityRepository.findById(repairOrder.getFacilityId()).orElse(null);
        User citizen = userRepository.findById(repairOrder.getUserId()).orElse(null);
        User worker = repairOrder.getWorkerId() != null ? 
                userRepository.findById(repairOrder.getWorkerId()).orElse(null) : null;
        
        long repairHours = 0;
        if (repairOrder.getCompleteTime() != null) {
            repairHours = DateTimeUtil.getHoursBetween(repairOrder.getSubmitTime(), repairOrder.getCompleteTime());
        } else if (repairOrder.getAcceptTime() != null) {
            repairHours = DateTimeUtil.getHoursBetween(repairOrder.getSubmitTime(), LocalDateTime.now());
        }
        
        return RepairOrderResponse.builder()
                .id(repairOrder.getId())
                .repairCode(repairOrder.getRepairCode())
                .facilityId(repairOrder.getFacilityId())
                .facilityCode(facility != null ? facility.getFacilityCode() : null)
                .facilityType(facility != null ? facility.getFacilityType().toString() : null)
                .userId(repairOrder.getUserId())
                .citizenName(citizen != null ? citizen.getRealName() : null)
                .workerId(repairOrder.getWorkerId())
                .workerName(worker != null ? worker.getRealName() : null)
                .problemDescription(repairOrder.getProblemDescription())
                .images(repairOrder.getImages())
                .status(repairOrder.getStatus().toString())
                .submitTime(DateTimeUtil.formatDateTime(repairOrder.getSubmitTime()))
                .acceptTime(DateTimeUtil.formatDateTime(repairOrder.getAcceptTime()))
                .completeTime(DateTimeUtil.formatDateTime(repairOrder.getCompleteTime()))
                .feedback(repairOrder.getFeedback())
                .repairHours(repairHours)
                .build();
    }
}
