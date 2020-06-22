package cn.fenrana.blog.service;

import cn.fenrana.blog.entity.Article;
import cn.fenrana.blog.utils.ResultJson;

import java.util.List;
import java.util.Map;

public interface IBlogService {
    ResultJson<Map<String, Object>> getAsideInfo();

    ResultJson<Map<String, Object>> getArticles(Long current);

    ResultJson<Article> getArticleById(Long id);
}
