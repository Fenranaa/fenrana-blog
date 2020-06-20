package cn.fenrana.blog.mapper;

import cn.fenrana.blog.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文章表 Mapper 接口
 * </p>
 *
 * @author Fenrana
 * @since 2019-11-05
 */
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 查询每月写了多少篇文章
     * */
    @Select("SELECT COUNT(create_time) as count, YEAR(FROM_UNIXTIME(create_time/1000)) as year, MONTH(FROM_UNIXTIME(create_time/1000)) as month\n" +
            "FROM article\n" +
            "GROUP BY month\n" +
            "ORDER BY year, month;")
    List<Map<String, Object>> selectArchiveCount();

}
