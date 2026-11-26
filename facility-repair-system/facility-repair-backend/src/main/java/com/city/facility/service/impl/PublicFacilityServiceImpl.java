package com.city.facility.service.impl;

import com.city.facility.entity.PublicFacility;
import com.city.facility.repository.PublicFacilityRepository;
import com.city.facility.service.PublicFacilityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * 公共设施服务实现类
 */
@Slf4j
@Service
@Transactional
public class PublicFacilityServiceImpl implements PublicFacilityService {
    
    @Autowired
    private PublicFacilityRepository publicFacilityRepository;
    
    @Override
    public PublicFacility createFacility(String facilityCode, String facilityType, String location, 
                                         Double latitude, Double longitude, String installDate) {
        PublicFacility facility = PublicFacility.builder()
                .facilityCode(facilityCode)
                .facilityType(PublicFacility.FacilityType.valueOf(facilityType.toUpperCase()))
                .location(location)
                .latitude(latitude)
                .longitude(longitude)
                .status(PublicFacility.FacilityStatus.NORMAL)
                .installDate(installDate != null ? LocalDate.parse(installDate) : null)
                .build();
        
        PublicFacility saved = publicFacilityRepository.save(facility);
        log.info("设施创建成功: {}", saved.getFacilityCode());
        return saved;
    }
    
    @Override
    public Page<PublicFacility> getAllFacilities(Pageable pageable) {
        return publicFacilityRepository.findAll(pageable);
    }
    
    @Override
    public List<PublicFacility> getFacilitiesByType(String facilityType) {
        return publicFacilityRepository.findByFacilityType(
                PublicFacility.FacilityType.valueOf(facilityType.toUpperCase())
        );
    }
    
    @Override
    public List<PublicFacility> getFacilitiesByStatus(String status) {
        return publicFacilityRepository.findByStatus(
                PublicFacility.FacilityStatus.valueOf(status.toUpperCase())
        );
    }
    
    @Override
    public Optional<PublicFacility> getFacilityById(Long id) {
        return publicFacilityRepository.findById(id);
    }
    
    @Override
    public PublicFacility updateFacilityStatus(Long facilityId, String status) {
        PublicFacility facility = publicFacilityRepository.findById(facilityId)
                .orElseThrow(() -> new RuntimeException("设施不存在"));
        
        facility.setStatus(PublicFacility.FacilityStatus.valueOf(status.toUpperCase()));
        PublicFacility saved = publicFacilityRepository.save(facility);
        log.info("设施状态已更新: {} -> {}", facilityId, status);
        return saved;
    }
    
    @Override
    public PublicFacility updateFacility(Long facilityId, String location, Double latitude, Double longitude) {
        PublicFacility facility = publicFacilityRepository.findById(facilityId)
                .orElseThrow(() -> new RuntimeException("设施不存在"));
        
        if (location != null) {
            facility.setLocation(location);
        }
        if (latitude != null) {
            facility.setLatitude(latitude);
        }
        if (longitude != null) {
            facility.setLongitude(longitude);
        }
        
        return publicFacilityRepository.save(facility);
    }
    
    @Override
    public int getTotalCount(String facilityType) {
        Integer count = publicFacilityRepository.countByFacilityType(
                PublicFacility.FacilityType.valueOf(facilityType.toUpperCase())
        );
        return count != null ? count : 0;
    }
    
    @Override
    public int getBrokenCount(String facilityType) {
        Integer count = publicFacilityRepository.countBrokenByFacilityType(
                PublicFacility.FacilityType.valueOf(facilityType.toUpperCase())
        );
        return count != null ? count : 0;
    }
}
