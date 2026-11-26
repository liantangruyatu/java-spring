package nuc.edu.cn.bookmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import nuc.edu.cn.bookmanager.entity.Order;
import nuc.edu.cn.bookmanager.dto.OrderDTO;

import java.util.List;
import java.util.Map;

/**
 * 订单服务接口
 */
public interface OrderService extends IService<Order> {
    
    /**
     * 创建订单
     */
    String createOrder(Long userId, String receiverName, String receiverPhone, String receiverAddr);
    
    /**
     * 获取用户的订单列表
     */
    List<OrderDTO> getUserOrders(Long userId);
    
    /**
     * 获取订单详情
     */
    OrderDTO getOrderDetail(Long orderId);
    
    /**
     * 支付订单
     */
    boolean payOrder(Long orderId);
    
    /**
     * 月度销售额统计
     */
    List<Map<String, Object>> getMonthlySales();
}

