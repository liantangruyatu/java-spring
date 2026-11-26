package nuc.edu.cn.bookmanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import nuc.edu.cn.bookmanager.entity.Book;
import nuc.edu.cn.bookmanager.entity.Cart;
import nuc.edu.cn.bookmanager.mapper.BookMapper;
import nuc.edu.cn.bookmanager.mapper.CartMapper;
import nuc.edu.cn.bookmanager.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 购物车服务实现类
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {
    
    @Autowired
    private BookMapper bookMapper;
    
    @Override
    public boolean addToCart(Long userId, Long bookId, Integer quantity) {
        // 检查库存
        Book book = bookMapper.selectById(bookId);
        if (book == null || book.getStock() < quantity) {
            return false;
        }
        
        // 检查购物车中是否已有该商品
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("book_id", bookId);
        Cart existingCart = this.getOne(wrapper);
        
        if (existingCart != null) {
            // 更新数量
            existingCart.setQuantity(existingCart.getQuantity() + quantity);
            return this.updateById(existingCart);
        } else {
            // 新增
            Cart cart = new Cart();
            cart.setUserId(userId);
            cart.setBookId(bookId);
            cart.setQuantity(quantity);
            return this.save(cart);
        }
    }
    
    @Override
    public List<Cart> getUserCart(Long userId) {
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return this.list(wrapper);
    }
    
    @Override
    public boolean updateCartQuantity(Long cartId, Integer quantity) {
        Cart cart = this.getById(cartId);
        if (cart == null) {
            return false;
        }
        
        // 检查库存
        Book book = bookMapper.selectById(cart.getBookId());
        if (book == null || book.getStock() < quantity) {
            return false;
        }
        
        cart.setQuantity(quantity);
        return this.updateById(cart);
    }
    
    @Override
    public boolean clearCart(Long userId) {
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return this.remove(wrapper);
    }
}

