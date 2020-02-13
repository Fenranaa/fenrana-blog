package cn.fenrana.blog.service;

import cn.fenrana.blog.entity.Article;
import cn.fenrana.blog.utils.ResultJson;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 文章表 服务类
 * </p>
 *
 * @author Fenrana
 * @since 2019-11-05
 */
public interface IArticleService extends IService<Article> {
    /**
     * 添加博客
     * */
    ResultJson<Object> addArticle(Map<String, Object> map);

    /**
     * 博客的修改
     * */
    ResultJson<Object> updateArticle(Map<String, Object> map);

    ResultJson<Object> articleDelete(Long id);
}
