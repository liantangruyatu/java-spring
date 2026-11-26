package com.city.facility.service;

import com.city.facility.dto.RepairOrderResponse;
import com.city.facility.entity.RepairOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * 报修单服务接口
 */
public interface RepairOrderService {
    
    /**
     * 创建报修单
     */
    RepairOrder createRepairOrder(Long facilityId, Long userId, String problemDescription, String images);
    
    /**
     * 查询报修单(分页)
     */
    Page<RepairOrder> getRepairOrders(Pageable pageable);
    
    /**
     * 查询用户的报修单
     */
    Page<RepairOrder> getUserRepairOrders(Long userId, Pageable pageable);
    
    /**
     * 查询维修工的报修单
     */
    Page<RepairOrder> getWorkerRepairOrders(Long workerId, Pageable pageable);
    
    /**
     * 查询指定设施的所有报修单
     */
    List<RepairOrder> getFacilityRepairOrders(Long facilityId);
    
    /**
     * 接受报修单(维修工)
     */
    RepairOrder acceptRepairOrder(Long repairOrderId, Long workerId);
    
    /**
     * 开始维修
     */
    RepairOrder startRepair(Long repairOrderId);
    
    /**
     * 完成维修
     */
    RepairOrder completeRepair(Long repairOrderId, String feedback);
    
    /**
     * 根据ID查询
     */
    Optional<RepairOrder> getRepairOrderById(Long id);
    
    /**
     * 转换为响应对象
     */
    RepairOrderResponse toResponse(RepairOrder repairOrder);
}
