package com.city.facility.repository;

import com.city.facility.entity.RepairOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

/**
 * 报修单数据访问层
 */
@Repository
public interface RepairOrderRepository extends JpaRepository<RepairOrder, Long> {
    
    /**
     * 根据报修单编号查询
     */
    Optional<RepairOrder> findByRepairCode(String repairCode);
    
    /**
     * 根据状态查询
     */
    List<RepairOrder> findByStatus(RepairOrder.RepairStatus status);
    Page<RepairOrder> findByStatus(RepairOrder.RepairStatus status, Pageable pageable);
    
    /**
     * 查询用户的报修单
     */
    List<RepairOrder> findByUserId(Long userId);
    Page<RepairOrder> findByUserId(Long userId, Pageable pageable);
    
    /**
     * 查询维修工的报修单
     */
    List<RepairOrder> findByWorkerId(Long workerId);
    Page<RepairOrder> findByWorkerId(Long workerId, Pageable pageable);
    
    /**
     * 查询指定设施的报修单
     */
    List<RepairOrder> findByFacilityId(Long facilityId);
    
    /**
     * 查询维修中且超时的报修单
     */
    @Query("SELECT ro FROM RepairOrder ro WHERE ro.status = 'PROCESSING' AND ro.submitTime < :timeThreshold")
    List<RepairOrder> findTimeoutRepairs(LocalDateTime timeThreshold);
    
    /**
     * 查询已完成的报修单，用于统计平均维修时间
     */
    @Query("SELECT ro FROM RepairOrder ro WHERE ro.status = 'COMPLETED' AND ro.facilityId = :facilityId")
    List<RepairOrder> findCompletedByFacilityId(Long facilityId);
}
