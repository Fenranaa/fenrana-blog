package cn.fenrana.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 文章表
 * </p>
 *
 * @author Fenrana
 * @since 2019-11-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 封面图片
     */
    private String cover;

    /**
     * 作者
     */
    private String author;

    /**
     * 内容
     */
    private String content;

    /**
     * 分类实体的ID
     */
    private String categoryId;

    /**
     * 状态
     * 0 正常发布 1 草稿 2 回收站
     */
    private String state;

    /**
     * 发布时间
     */
    private Long publishTime;

    /**
     * 上次修改时间
     */
    private Long editTime;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 类型， 0原创 1转载
     */
    private Integer type;

    /**
     * 浏览量
     */
    private Integer visits;

    /**
     * 文章概要
     */
    private String summary;

    /**
     * 是否开启评论
     * 0 关闭评论
     * 1 开启评论
     */
    private Integer disallowComment;
    /**
     * 是否指定
     * 0 关闭置顶
     * 1 开启置顶
     * */
    private Integer isTop;
}
