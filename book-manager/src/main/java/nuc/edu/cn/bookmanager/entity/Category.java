package nuc.edu.cn.bookmanager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 图书类别实体类
 */
@Data
@TableName("t_category")
public class Category {
    @TableId(type = IdType.AUTO)
    private Integer categoryId;
    
    private String categoryName;
    
    /**
     * 0表示一级类别；非0时指向本表category_id
     */
    private Integer parentId;
    
    private String path;
    
    private LocalDateTime createTime;
}

