package com.city.facility.controller;

import com.city.facility.dto.ApiResponse;
import com.city.facility.dto.RepairOrderCreateRequest;
import com.city.facility.dto.RepairOrderProcessRequest;
import com.city.facility.dto.RepairOrderResponse;
import com.city.facility.entity.RepairOrder;
import com.city.facility.service.RepairOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 报修单控制器
 */
@Slf4j
@RestController
@RequestMapping("/repair-order")
@Validated
public class RepairOrderController {
    
    @Autowired
    private RepairOrderService repairOrderService;
    
    /**
     * 创建报修单
     */
    @PostMapping("/create")
    public ApiResponse<RepairOrderResponse> createRepairOrder(@Valid @RequestBody RepairOrderCreateRequest request) {
        try {
            RepairOrder repairOrder = repairOrderService.createRepairOrder(
                    request.getFacilityId(),
                    request.getFacilityId(),  // 这里应该从token获取当前用户ID,这是示例
                    request.getProblemDescription(),
                    request.getImages());
            
            return ApiResponse.success("报修单创建成功", repairOrderService.toResponse(repairOrder));
        } catch (RuntimeException e) {
            log.error("创建报修单失败: {}", e.getMessage());
            return ApiResponse.error(400, e.getMessage());
        }
    }
    
    /**
     * 查询所有报修单(分页)
     */
    @GetMapping("/list")
    public ApiResponse<?> getRepairOrders(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<RepairOrder> orders = repairOrderService.getRepairOrders(pageable);
            
            return ApiResponse.success(orders.map(repairOrderService::toResponse));
        } catch (Exception e) {
            log.error("查询报修单失败: {}", e.getMessage());
            return ApiResponse.error(500, e.getMessage());
        }
    }
    
    /**
     * 查询用户的报修单
     */
    @GetMapping("/user/{userId}")
    public ApiResponse<?> getUserRepairOrders(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<RepairOrder> orders = repairOrderService.getUserRepairOrders(userId, pageable);
            
            return ApiResponse.success(orders.map(repairOrderService::toResponse));
        } catch (Exception e) {
            log.error("查询用户报修单失败: {}", e.getMessage());
            return ApiResponse.error(500, e.getMessage());
        }
    }
    
    /**
     * 查询维修工的报修单
     */
    @GetMapping("/worker/{workerId}")
    public ApiResponse<?> getWorkerRepairOrders(
            @PathVariable Long workerId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<RepairOrder> orders = repairOrderService.getWorkerRepairOrders(workerId, pageable);
            
            return ApiResponse.success(orders.map(repairOrderService::toResponse));
        } catch (Exception e) {
            log.error("查询维修工报修单失败: {}", e.getMessage());
            return ApiResponse.error(500, e.getMessage());
        }
    }
    
    /**
     * 查询指定报修单
     */
    @GetMapping("/{id}")
    public ApiResponse<RepairOrderResponse> getRepairOrderById(@PathVariable Long id) {
        try {
            Optional<RepairOrder> orderOpt = repairOrderService.getRepairOrderById(id);
            
            if (!orderOpt.isPresent()) {
                return ApiResponse.notFound("报修单不存在");
            }
            
            return ApiResponse.success(repairOrderService.toResponse(orderOpt.get()));
        } catch (Exception e) {
            log.error("查询报修单失败: {}", e.getMessage());
            return ApiResponse.error(500, e.getMessage());
        }
    }
    
    /**
     * 处理报修单(接受/维修中/完成)
     */
    @PostMapping("/process")
    public ApiResponse<RepairOrderResponse> processRepairOrder(@Valid @RequestBody RepairOrderProcessRequest request) {
        try {
            RepairOrder repairOrder = null;
            
            switch (request.getStatus()) {
                case "processing":
                    repairOrder = repairOrderService.startRepair(request.getRepairOrderId());
                    break;
                case "completed":
                    repairOrder = repairOrderService.completeRepair(request.getRepairOrderId(), request.getFeedback());
                    break;
                default:
                    return ApiResponse.badRequest("不支持的状态");
            }
            
            return ApiResponse.success("报修单已更新", repairOrderService.toResponse(repairOrder));
        } catch (RuntimeException e) {
            log.error("处理报修单失败: {}", e.getMessage());
            return ApiResponse.error(400, e.getMessage());
        }
    }
}
