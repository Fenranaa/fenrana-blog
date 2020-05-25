package cn.fenrana.blog.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AttachmentPageQuery {
    //关键字检索
    private String searchKey;

    /**
     * 当前页
     * */
    private Integer current;

    /**
     * 每页显示的条数
     * */
    private Integer size;
    /**
     * 文件类型
     * */
    private String mediaType;
    /**
     * 后缀
     * */
    private String suffix;

    /**
     * 文件储存位置
     * */
    private String saveLocation;

}
