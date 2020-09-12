package cn.fenrana.blog.mapper;

import cn.fenrana.blog.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
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
     */
    @Select("SELECT COUNT(create_time) as count, YEAR(FROM_UNIXTIME(create_time/1000)) as year, MONTH(FROM_UNIXTIME(create_time/1000)) as month\n" +
            "FROM article\n" +
            "GROUP BY month\n" +
            "ORDER BY year, month;")
    List<Map<String, Object>> selectArchiveCount();

    /**
     * 查询文章都是在什么年份写的
     */
    @Select("SELECT YEAR(FROM_UNIXTIME(create_time / 1000)) as year\n" +
            "FROM article\n" +
            "GROUP BY year")
    List<Integer> selectArticleYear();

    /**
     * 查出一整年的文章
     */
    @Select("select id, title, create_time, YEAR(FROM_UNIXTIME(create_time / 1000)) as year\n" +
            "from article\n" +
            "where date_format(FROM_UNIXTIME(create_time / 1000), '%Y') = #{year}" +
            "ORDER BY create_time DESC")
    List<Map<String, Object>> selectArticleByYear(@Param("year") String year);

}
