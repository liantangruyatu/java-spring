package nuc.edu.cn.bookmanager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 图书实体类
 */
@Data
@TableName("t_book")
public class Book {
    @TableId(type = IdType.AUTO)
    private Long bookId;
    
    private String bookName;
    
    private String author;
    
    private String press;
    
    private LocalDate publishDate;
    
    private String isbn;
    
    private Integer categoryId;
    
    private BigDecimal price;
    
    private Integer stock;
    
    private String coverUrl;
    
    private String description;
    
    private Integer salesVolume;
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
}

