package com.city.facility.service;

import com.city.facility.entity.PublicFacility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * 公共设施服务接口
 */
public interface PublicFacilityService {
    
    /**
     * 创建设施
     */
    PublicFacility createFacility(String facilityCode, String facilityType, String location, 
                                  Double latitude, Double longitude, String installDate);
    
    /**
     * 查询所有设施
     */
    Page<PublicFacility> getAllFacilities(Pageable pageable);
    
    /**
     * 根据类型查询设施
     */
    List<PublicFacility> getFacilitiesByType(String facilityType);
    
    /**
     * 根据状态查询设施
     */
    List<PublicFacility> getFacilitiesByStatus(String status);
    
    /**
     * 根据ID查询设施
     */
    Optional<PublicFacility> getFacilityById(Long id);
    
    /**
     * 更新设施状态
     */
    PublicFacility updateFacilityStatus(Long facilityId, String status);
    
    /**
     * 更新设施信息
     */
    PublicFacility updateFacility(Long facilityId, String location, Double latitude, Double longitude);
    
    /**
     * 获取设施统计信息
     */
    int getTotalCount(String facilityType);
    
    int getBrokenCount(String facilityType);
}
