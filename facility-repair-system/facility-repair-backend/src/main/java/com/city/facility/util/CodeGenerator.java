package com.city.facility.util;

import java.util.UUID;

/**
 * 编号生成工具类
 */
public class CodeGenerator {
    
    /**
     * 生成报修单编号
     * 格式: RP-YYYYMMDD-xxxxxx
     */
    public static String generateRepairCode() {
        String datePrefix = java.time.LocalDate.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"));
        String randomSuffix = UUID.randomUUID().toString()
                .substring(0, 6).toUpperCase();
        return "RP-" + datePrefix + "-" + randomSuffix;
    }
    
    /**
     * 生成设施编号前缀
     */
    public static String generateFacilityCode(String typePrefix) {
        // typePrefix 示例: SL(路灯), PB(公园长椅), PT(公共厕所), FE(健身器材)
        long timestamp = System.currentTimeMillis() % 10000;
        return typePrefix + "-" + String.format("%06d", timestamp);
    }
}
