package com.city.facility.controller;

import com.city.facility.dto.ApiResponse;
import com.city.facility.dto.PublicFacilityRequest;
import com.city.facility.entity.PublicFacility;
import com.city.facility.service.PublicFacilityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 公共设施控制器
 */
@Slf4j
@RestController
@RequestMapping("/facility")
@Validated
public class PublicFacilityController {
    
    @Autowired
    private PublicFacilityService publicFacilityService;
    
    /**
     * 创建设施
     */
    @PostMapping("/create")
    public ApiResponse<PublicFacility> createFacility(@Valid @RequestBody PublicFacilityRequest request) {
        try {
            PublicFacility facility = publicFacilityService.createFacility(
                    request.getFacilityCode(),
                    request.getFacilityType(),
                    request.getLocation(),
                    request.getLatitude(),
                    request.getLongitude(),
                    request.getInstallDate());
            
            return ApiResponse.success("设施创建成功", facility);
        } catch (RuntimeException e) {
            log.error("创建设施失败: {}", e.getMessage());
            return ApiResponse.error(400, e.getMessage());
        }
    }
    
    /**
     * 查询所有设施(分页)
     */
    @GetMapping("/list")
    public ApiResponse<?> getAllFacilities(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<PublicFacility> facilities = publicFacilityService.getAllFacilities(pageable);
            
            return ApiResponse.success(facilities);
        } catch (Exception e) {
            log.error("查询设施失败: {}", e.getMessage());
            return ApiResponse.error(500, e.getMessage());
        }
    }
    
    /**
     * 根据类型查询设施
     */
    @GetMapping("/type/{facilityType}")
    public ApiResponse<List<PublicFacility>> getFacilitiesByType(@PathVariable String facilityType) {
        try {
            List<PublicFacility> facilities = publicFacilityService.getFacilitiesByType(facilityType);
            return ApiResponse.success(facilities);
        } catch (Exception e) {
            log.error("查询设施失败: {}", e.getMessage());
            return ApiResponse.error(500, e.getMessage());
        }
    }
    
    /**
     * 根据状态查询设施
     */
    @GetMapping("/status/{status}")
    public ApiResponse<List<PublicFacility>> getFacilitiesByStatus(@PathVariable String status) {
        try {
            List<PublicFacility> facilities = publicFacilityService.getFacilitiesByStatus(status);
            return ApiResponse.success(facilities);
        } catch (Exception e) {
            log.error("查询设施失败: {}", e.getMessage());
            return ApiResponse.error(500, e.getMessage());
        }
    }
    
    /**
     * 查询指定设施
     */
    @GetMapping("/{id}")
    public ApiResponse<PublicFacility> getFacilityById(@PathVariable Long id) {
        try {
            Optional<PublicFacility> facilityOpt = publicFacilityService.getFacilityById(id);
            
            if (!facilityOpt.isPresent()) {
                return ApiResponse.notFound("设施不存在");
            }
            
            return ApiResponse.success(facilityOpt.get());
        } catch (Exception e) {
            log.error("查询设施失败: {}", e.getMessage());
            return ApiResponse.error(500, e.getMessage());
        }
    }
    
    /**
     * 更新设施状态
     */
    @PutMapping("/{id}/status")
    public ApiResponse<PublicFacility> updateFacilityStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        try {
            PublicFacility facility = publicFacilityService.updateFacilityStatus(id, status);
            return ApiResponse.success("更新成功", facility);
        } catch (Exception e) {
            log.error("更新设施状态失败: {}", e.getMessage());
            return ApiResponse.error(500, e.getMessage());
        }
    }
    
    /**
     * 获取设施统计信息
     */
    @GetMapping("/stats/summary")
    public ApiResponse<Map<String, Object>> getFacilityStats() {
        try {
            Map<String, Object> stats = new HashMap<>();
            
            String[] facilityTypes = {"street_light", "park_bench", "public_toilet", "fitness_equipment"};
            
            for (String facilityType : facilityTypes) {
                int totalCount = publicFacilityService.getTotalCount(facilityType);
                int brokenCount = publicFacilityService.getBrokenCount(facilityType);
                double faultRate = totalCount > 0 ? (double) brokenCount / totalCount * 100 : 0;
                
                Map<String, Object> typeStats = new HashMap<>();
                typeStats.put("total", totalCount);
                typeStats.put("broken", brokenCount);
                typeStats.put("faultRate", String.format("%.2f", faultRate) + "%");
                
                stats.put(facilityType, typeStats);
            }
            
            return ApiResponse.success(stats);
        } catch (Exception e) {
            log.error("获取统计信息失败: {}", e.getMessage());
            return ApiResponse.error(500, e.getMessage());
        }
    }
}
