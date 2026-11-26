package nuc.edu.cn.bookmanager.controller;

import nuc.edu.cn.bookmanager.dto.OrderDTO;
import nuc.edu.cn.bookmanager.entity.User;
import nuc.edu.cn.bookmanager.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 订单控制器
 */
@Controller
@RequestMapping("/user")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    /**
     * 订单列表
     */
    @GetMapping("/orders")
    public String orders(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        List<OrderDTO> orders = orderService.getUserOrders(user.getUserId());
        model.addAttribute("orders", orders);
        return "user/orders";
    }
    
    /**
     * 订单详情
     */
    @GetMapping("/order/{id}")
    public String orderDetail(@PathVariable Long id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        OrderDTO order = orderService.getOrderDetail(id);
        if (order == null || !order.getUserId().equals(user.getUserId())) {
            return "redirect:/user/orders";
        }
        
        model.addAttribute("order", order);
        return "user/order-detail";
    }
    
    /**
     * 创建订单
     */
    @PostMapping("/order/create")
    public String createOrder(@RequestParam String receiverName,
                             @RequestParam String receiverPhone,
                             @RequestParam String receiverAddr,
                             HttpSession session,
                             Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        try {
            String orderNo = orderService.createOrder(user.getUserId(), receiverName, receiverPhone, receiverAddr);
            model.addAttribute("message", "订单创建成功，订单号：" + orderNo);
            return "redirect:/user/orders";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/user/cart";
        }
    }
    
    /**
     * 支付订单
     */
    @GetMapping("/order/pay/{id}")
    public String payOrder(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        orderService.payOrder(id);
        return "redirect:/user/orders";
    }
}

