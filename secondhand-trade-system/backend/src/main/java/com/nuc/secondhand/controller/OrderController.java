package com.nuc.secondhand.controller;

import com.nuc.secondhand.dto.ApiResponse;
import com.nuc.secondhand.dto.OrderDTO;
import com.nuc.secondhand.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000", "http://localhost:5173"})
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ApiResponse<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        try {
            OrderDTO createdOrder = orderService.createOrder(orderDTO);
            return ApiResponse.success("订单创建成功", createdOrder);
        } catch (Exception e) {
            return ApiResponse.error(400, e.getMessage());
        }
    }

    @GetMapping("/{orderId}")
    public ApiResponse<OrderDTO> getOrderById(@PathVariable Integer orderId) {
        try {
            OrderDTO orderDTO = orderService.getOrderById(orderId);
            return ApiResponse.success(orderDTO);
        } catch (Exception e) {
            return ApiResponse.error(404, e.getMessage());
        }
    }

    @GetMapping("/buyer/{buyerId}")
    public ApiResponse<Page<OrderDTO>> getOrdersByBuyer(
            @PathVariable Integer buyerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<OrderDTO> orders = orderService.getOrdersByBuyer(buyerId, pageable);
            return ApiResponse.success(orders);
        } catch (Exception e) {
            return ApiResponse.error(500, e.getMessage());
        }
    }

    @GetMapping("/seller/{sellerId}")
    public ApiResponse<Page<OrderDTO>> getOrdersBySeller(
            @PathVariable Integer sellerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<OrderDTO> orders = orderService.getOrdersBySeller(sellerId, pageable);
            return ApiResponse.success(orders);
        } catch (Exception e) {
            return ApiResponse.error(500, e.getMessage());
        }
    }

    @GetMapping("/status/{status}")
    public ApiResponse<Page<OrderDTO>> getOrdersByStatus(
            @PathVariable String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<OrderDTO> orders = orderService.getOrdersByStatus(status, pageable);
            return ApiResponse.success(orders);
        } catch (Exception e) {
            return ApiResponse.error(500, e.getMessage());
        }
    }

    @PutMapping("/{orderId}/status")
    public ApiResponse<OrderDTO> updateOrderStatus(
            @PathVariable Integer orderId,
            @RequestParam String status) {
        try {
            OrderDTO updatedOrder = orderService.updateOrderStatus(orderId, status);
            return ApiResponse.success("订单状态更新成功", updatedOrder);
        } catch (Exception e) {
            return ApiResponse.error(400, e.getMessage());
        }
    }

    @PutMapping("/{orderId}/rate")
    public ApiResponse<OrderDTO> rateOrder(
            @PathVariable Integer orderId,
            @RequestParam Integer score,
            @RequestParam String comment,
            @RequestParam String rateType) {
        try {
            OrderDTO ratedOrder = orderService.rateOrder(orderId, score, comment, rateType);
            return ApiResponse.success("评价成功", ratedOrder);
        } catch (Exception e) {
            return ApiResponse.error(400, e.getMessage());
        }
    }

    @DeleteMapping("/{orderId}")
    public ApiResponse<String> deleteOrder(@PathVariable Integer orderId) {
        try {
            orderService.deleteOrder(orderId);
            return ApiResponse.success("订单删除成功");
        } catch (Exception e) {
            return ApiResponse.error(400, e.getMessage());
        }
    }
}
