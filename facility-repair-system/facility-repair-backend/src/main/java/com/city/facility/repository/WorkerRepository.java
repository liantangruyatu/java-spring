package com.city.facility.repository;

import com.city.facility.entity.Worker;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

/**
 * 维修工数据访问层
 */
@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {
    
    /**
     * 根据用户ID查询
     */
    Optional<Worker> findByUserId(Long userId);
    
    /**
     * 根据状态查询
     */
    List<Worker> findByStatus(Worker.WorkerStatus status);
    
    /**
     * 查询所有可用的维修工，按待办任务数排序
     */
    @Query("SELECT w FROM Worker w WHERE w.status = 'AVAILABLE' ORDER BY w.pendingCount ASC")
    List<Worker> findAvailableWorkersOrderByPendingCount();
    
    /**
     * 查询待办任务最少的维修工
     */
    @Query("SELECT w FROM Worker w WHERE w.status = 'AVAILABLE' ORDER BY w.pendingCount ASC")
    List<Worker> findWorkerWithLeastPendingTasks(Pageable pageable);
}
