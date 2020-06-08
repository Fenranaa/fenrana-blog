package cn.fenrana.blog.entity.param;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PageParam {
    /**
     * 搜索关键字
     * */
    private String searchKey;
    /**
     * 文章状态
     * */
    private String state;
    /**
     * 文章分类
     * */
    private Long categoryId;

    /**
     * 当前页
     * */
    private Integer current;

    /**
     * 每页显示的条数
     * */
    private Integer size;


}
