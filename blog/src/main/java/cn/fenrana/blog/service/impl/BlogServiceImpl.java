package cn.fenrana.blog.service.impl;

import cn.fenrana.blog.entity.Article;
import cn.fenrana.blog.entity.ArticleTag;
import cn.fenrana.blog.entity.Category;
import cn.fenrana.blog.entity.Tag;
import cn.fenrana.blog.entity.dto.ArticleDto;
import cn.fenrana.blog.mapper.*;
import cn.fenrana.blog.service.IBlogService;
import cn.fenrana.blog.utils.ResultJson;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BlogServiceImpl implements IBlogService {

    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private ArticleTagMapper articleTagMapper;
    @Resource
    private TagMapper tagMapper;
    @Resource
    private ArticleCategoryMapper articleCategoryMapper;
    @Resource
    private CategoryMapper categoryMapper;

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
        articleQueryWrapper.select("id", "title", "author", "cover", "category_id", "state", "publish_time", "create_time", "type", "visits", "summary", "disallow_comment", "is_top");
        Page<Article> articlePage = articleMapper.selectPage(page, articleQueryWrapper);
        List<Article> records = articlePage.getRecords();
        List<ArticleDto> articleDtos = new ArrayList<>();
        //封装标签
        records.forEach(item -> {
            List<Tag> tags = articleTagMapper.selectTagByArticleId(item.getId());
            ArticleDto articleDto = new ArticleDto();
            BeanUtil.copyProperties(item, articleDto);
            articleDto.setTags(tags);
            articleDtos.add(articleDto);
            item = null;
        });
        Map<String, Object> map = new HashMap<>();
        map.put("total", articlePage.getTotal());
        map.put("articles", articleDtos);
        return ResultJson.ok(map);
    }

    @Override
    public ResultJson<Article> getArticleById(Long id) {
        List<Tag> tags = articleTagMapper.selectTagByArticleId(id);
        Article article = articleMapper.selectById(id);
        Category category = categoryMapper.selectById(article.getCategoryId());
        ArticleDto articleDto = new ArticleDto();
        BeanUtil.copyProperties(article, articleDto);
        articleDto.setTags(tags);
        articleDto.setCategory(category);
        return ResultJson.ok(articleDto);
    }
}
