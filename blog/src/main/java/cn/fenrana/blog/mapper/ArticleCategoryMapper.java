package cn.fenrana.blog.mapper;

import cn.fenrana.blog.entity.ArticleCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文章&&分类关联表 Mapper 接口
 * </p>
 *
 * @author Fenrana
 * @since 2019-11-05
 */
public interface ArticleCategoryMapper extends BaseMapper<ArticleCategory> {


    @Select("SELECT COUNT(ac.category_id) as category_count, ac.category_id, c.name\n" +
            "FROM article_category as ac\n" +
            "INNER JOIN category as c\n" +
            "on ac.category_id = c.id\n" +
            "GROUP BY category_id;")
    @Results({
            @Result(column = "category_id", property = "categoryId"),
            @Result(column = "category_count", property = "count"),
            @Result(column = "name", property = "name")
    })
    List<Map<String, Object>> selectCategoryCount();

}
