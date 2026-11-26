package nuc.edu.cn.bookmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import nuc.edu.cn.bookmanager.entity.OrderItem;
import nuc.edu.cn.bookmanager.dto.OrderItemDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单明细Mapper接口
 */
@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {
    
    /**
     * 查询订单的明细列表
     */
    List<OrderItemDTO> selectItemsByOrderId(@Param("orderId") Long orderId);
}

