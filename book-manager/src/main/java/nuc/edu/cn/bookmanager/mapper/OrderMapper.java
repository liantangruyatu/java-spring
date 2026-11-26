package nuc.edu.cn.bookmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import nuc.edu.cn.bookmanager.entity.Order;
import nuc.edu.cn.bookmanager.dto.OrderDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单Mapper接口
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    
    /**
     * 查询用户的订单列表（包含订单明细）
     */
    List<OrderDTO> selectOrdersByUserId(@Param("userId") Long userId);
    
    /**
     * 查询订单详情
     */
    OrderDTO selectOrderDetail(@Param("orderId") Long orderId);
}

