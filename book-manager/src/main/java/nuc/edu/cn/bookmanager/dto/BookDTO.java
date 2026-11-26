package nuc.edu.cn.bookmanager.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 图书DTO（包含类别名称）
 */
@Data
public class BookDTO {
    private Long bookId;
    private String bookName;
    private String author;
    private String press;
    private LocalDate publishDate;
    private String isbn;
    private Integer categoryId;
    private String categoryName;
    private BigDecimal price;
    private Integer stock;
    private String coverUrl;
    private String description;
    private Integer salesVolume;
}

