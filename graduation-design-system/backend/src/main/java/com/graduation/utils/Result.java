package com.graduation.utils;

import lombok.Data;

/**
 * 统一响应结果类
 */
@Data
public class Result<T> {
    private Integer code; // 响应码，1表示成功，0表示失败
    private String message; // 响应信息
    private T data; // 响应数据
    
    /**
     * 成功响应
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(1);
        result.setMessage("success");
        result.setData(data);
        return result;
    }
    
    public static <T> Result<T> success() {
        return success(null);
    }
    
    /**
     * 失败响应
     */
    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<>();
        result.setCode(0);
        result.setMessage(message);
        result.setData(null);
        return result;
    }
    
    public static <T> Result<T> error() {
        return error("操作失败");
    }
}
