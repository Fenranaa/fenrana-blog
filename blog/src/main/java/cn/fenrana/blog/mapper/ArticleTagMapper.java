package cn.fenrana.blog.mapper;

import cn.fenrana.blog.entity.ArticleTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文章&&标签关联表 Mapper 接口
 * </p>
 *
 * @author Fenrana
 * @since 2019-11-05
 */
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {
    @Select("SELECT COUNT(at.tag_id) as tag_count, t.name, at.tag_id\n" +
            "FROM article_tag as at\n" +
            "INNER JOIN tag as t\n" +
            "on at.tag_id = t.id\n" +
            "GROUP BY at.tag_id")
    @Result(column = "tag_count", property = "count")
    @Result(column = "name", property = "name")
    @Result(column = "tag_id", property = "tagId")
    List<Map<String, Object>> selectTagCount();

}
