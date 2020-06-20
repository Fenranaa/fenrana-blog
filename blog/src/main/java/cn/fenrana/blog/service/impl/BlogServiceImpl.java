package cn.fenrana.blog.service.impl;

import cn.fenrana.blog.entity.Article;
import cn.fenrana.blog.entity.ArticleTag;
import cn.fenrana.blog.entity.Tag;
import cn.fenrana.blog.mapper.ArticleCategoryMapper;
import cn.fenrana.blog.mapper.ArticleMapper;
import cn.fenrana.blog.mapper.ArticleTagMapper;
import cn.fenrana.blog.mapper.TagMapper;
import cn.fenrana.blog.service.IBlogService;
import cn.fenrana.blog.utils.ResultJson;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BlogServiceImpl implements IBlogService {

    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private ArticleTagMapper articleTagMapper;
    @Resource
    private ArticleCategoryMapper articleCategoryMapper;

    @Override
    public ResultJson<Map<String, Object>> getAsideInfo() {
        //TODO 可以加入Redis缓存，以后再说
        Map<String, Object> resMap = new HashMap<>();

        List<Map<String, Object>> categoryCount = articleCategoryMapper.selectCategoryCount();
        List<Map<String, Object>> tagCount = articleTagMapper.selectTagCount();
        List<Map<String, Object>> archiveCount = articleMapper.selectArchiveCount();

        resMap.put("tagCount", tagCount);
        resMap.put("categoryCount", categoryCount);
        resMap.put("archiveCount", archiveCount);
        return ResultJson.ok(resMap);
    }

    @Override
    public ResultJson<Map<String, Object>> getArticles(Long current) {
        Page<Article> page = new Page<>();
        page.setSize(10L);
        page.setCurrent(current);
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        //要查询的列, 由于content的数据量太大
        articleQueryWrapper.select("id", "title","author", "cover", "category_id", "state", "publish_time", "create_time", "type", "visits", "summary", "disallow_comment", "is_top");

        Page<Article> articlePage = articleMapper.selectPage(page, articleQueryWrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("total", articlePage.getTotal());
        map.put("articles", articlePage.getRecords());
        return ResultJson.ok(map);
    }

}
