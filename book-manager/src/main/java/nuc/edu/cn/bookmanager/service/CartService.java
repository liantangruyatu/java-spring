package nuc.edu.cn.bookmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import nuc.edu.cn.bookmanager.entity.Cart;

import java.util.List;

/**
 * 购物车服务接口
 */
public interface CartService extends IService<Cart> {
    
    /**
     * 添加到购物车
     */
    boolean addToCart(Long userId, Long bookId, Integer quantity);
    
    /**
     * 获取用户的购物车列表（包含图书信息）
     */
    List<Cart> getUserCart(Long userId);
    
    /**
     * 更新购物车数量
     */
    boolean updateCartQuantity(Long cartId, Integer quantity);
    
    /**
     * 清空购物车
     */
    boolean clearCart(Long userId);
}

