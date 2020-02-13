package cn.fenrana.blog.entity.dto;

import cn.fenrana.blog.entity.Article;
import cn.fenrana.blog.entity.Category;
import cn.fenrana.blog.entity.Tag;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ArticleDto extends Article {
    private List<Tag> tags;
    private Category category;
    private List<Long> tagsId;
}
