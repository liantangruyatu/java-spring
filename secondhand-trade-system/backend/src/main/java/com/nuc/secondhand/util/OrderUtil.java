package com.nuc.secondhand.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class OrderUtil {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public static String generateOrderNumber() {
        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(formatter);
        String random = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return "ORD" + timestamp + random;
    }
}
