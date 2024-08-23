package org.example.librarysystem.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
@Data
public class BookInfoDTO extends BaseTDO{
    /*
     * @Author cms
     * @Description //TODO $
     * @Date $ $
     * @Param $
     * @return $
     **/
    @Schema(name = "书籍的id")
    private String id;
    @Schema(name = "书籍的标题")
    private String title ;
    @Schema(name = "书籍的作者")
    private String author ;
    @Schema(name = "国际标准书号，用于唯一标识一本书")
    private String isbn ;
    @Schema(name = "书籍的出版日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publicationDate ;
    @Schema(name = "书籍所属的类别，如小说、科幻、历史等")
    private String category ;
    @Schema(name = "书籍的价格")
    private Double price ;
    @Schema(name = "出版这本书的出版社")
    private String publisher ;
    @Schema(name = "书店当前的库存数量")
    private Integer stockQuantity ;
    @Schema(name = "书籍的简短介绍或摘要")
    private String description ;
    @Schema(name = "书籍封面的图片链接")
    private String coverimageUrl ;
    @Schema(name = "书籍的平均评分")
    private Double rating ;
    @Schema(name = "书籍收到的评论数量")
    private String numberOfReviews ;
    @Schema(name = "书籍位置")
    private String shelfLocation ;
    @Schema(name = "状态标志:默认0可出售，1不可出售")
    private String status = "0";
}
