package com.city.facility.repository;

import com.city.facility.entity.PublicFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

/**
 * 公共设施数据访问层
 */
@Repository
public interface PublicFacilityRepository extends JpaRepository<PublicFacility, Long> {
    
    /**
     * 根据设施编号查询
     */
    Optional<PublicFacility> findByFacilityCode(String facilityCode);
    
    /**
     * 根据设施类型查询
     */
    List<PublicFacility> findByFacilityType(PublicFacility.FacilityType facilityType);
    
    /**
     * 根据状态查询
     */
    List<PublicFacility> findByStatus(PublicFacility.FacilityStatus status);
    
    /**
     * 查询指定类型和状态的设施
     */
    List<PublicFacility> findByFacilityTypeAndStatus(PublicFacility.FacilityType facilityType, 
                                                      PublicFacility.FacilityStatus status);
    
    /**
     * 统计设施数量
     */
    @Query("SELECT COUNT(pf) FROM PublicFacility pf WHERE pf.facilityType = :facilityType")
    Integer countByFacilityType(PublicFacility.FacilityType facilityType);
    
    /**
     * 统计故障设施数量
     */
    @Query("SELECT COUNT(pf) FROM PublicFacility pf WHERE pf.facilityType = :facilityType AND pf.status = 'BROKEN'")
    Integer countBrokenByFacilityType(PublicFacility.FacilityType facilityType);
}
