package cn.fenrana.blog.service.impl;

import cn.fenrana.blog.entity.*;
import cn.fenrana.blog.entity.dto.ArticleDto;
import cn.fenrana.blog.entity.param.ArticlePageParam;
import cn.fenrana.blog.mapper.ArticleCategoryMapper;
import cn.fenrana.blog.mapper.ArticleMapper;
import cn.fenrana.blog.mapper.ArticleTagMapper;
import cn.fenrana.blog.service.IArticleService;
import cn.fenrana.blog.service.IArticleTagService;
import cn.fenrana.blog.service.ICategoryService;
import cn.fenrana.blog.service.ITagService;
import cn.fenrana.blog.utils.ResultJson;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author Fenrana
 * @since 2019-11-05
 */
@Service
@Transactional
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Autowired
    private ArticleCategoryMapper articleCategoryMapper;


    @Autowired
    private IArticleTagService articleTagService;

    @Autowired
    private ITagService tagService;
    @Autowired
    private ICategoryService categoryService;

    /**
     * 添加博客
     */
    @Override
    public ResultJson<Object> addArticle(Map<String, Object> map) {
        //保存文章
        Article article = BeanUtil.mapToBean(map, Article.class, false);
        article.setAuthor("Fenrana");
        article.setState("0");
        article.setVisits(0);
        //判断文章概要, 如果为空，截取内容的一部分 TODO 待完善
        if (StrUtil.isNotBlank(article.getSummary())) {

        }
        articleMapper.insert(article);
        //保存标签
        Long articleId = article.getId();
        ArrayList<Integer> tags = (ArrayList<Integer>) map.get("tag");
        addTags(tags, articleId);
        //保存分类
        Long categoryId = Long.valueOf(map.get("categoryId").toString());
        addArticleCategory(articleId, categoryId);
        return ResultJson.ok();
    }

    /**
     * 修改文章
     */
    @Override
    public ResultJson<Object> updateArticle(Map<String, Object> map) {
        //保存文章
        Article article = BeanUtil.mapToBean(map, Article.class, true);
        Instant now = Instant.now();
        article.setEditTime(now.toEpochMilli());
        articleMapper.updateById(article);
        //修改标签 1.先删除 2然后再插入
        QueryWrapper<ArticleTag> articleTagQueryWrapper = new QueryWrapper<>();
        articleTagQueryWrapper.eq("article_id", article.getId());
        articleTagMapper.delete(articleTagQueryWrapper);
        //保存标签
        Long articleId = article.getId();
        ArrayList<Integer> tags = (ArrayList<Integer>) map.get("tag");
        addTags(tags, articleId);
        //修改分类
        ArticleCategory articleCategory = new ArticleCategory();
        articleCategory.setCategoryId(Long.valueOf(article.getCategoryId()));
        QueryWrapper<ArticleCategory> articleCategoryQueryWrapper = new QueryWrapper<>();
        articleCategoryQueryWrapper.eq("article_id", article.getId());
        articleCategoryMapper.update(articleCategory, articleCategoryQueryWrapper);

        return ResultJson.ok();
    }

    /**
     * 彻底删除文章
     *
     * @param id 文章id
     */
    @Override
    public ResultJson<Object> articleDelete(Long id) {
        try {
            //删除文章
            articleMapper.deleteById(id);
            //删除标签
            QueryWrapper<ArticleTag> articleTagQueryWrapper = new QueryWrapper<>();
            articleTagQueryWrapper.eq("article_id", id);
            articleTagMapper.delete(articleTagQueryWrapper);
            //删除分类
            QueryWrapper<ArticleCategory> articleCategoryQueryWrapper = new QueryWrapper<>();
            articleCategoryQueryWrapper.eq("article_id", id);
            articleCategoryMapper.delete(articleCategoryQueryWrapper);
            return ResultJson.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultJson.fail();
        }
    }

    /**
     * 查询出一个年份的所有文章
     */
    @Override
    public ResultJson<List<Map<String, Object>>> selectArticleByYear() {
        List<Integer> years = articleMapper.selectArticleYear();
        List<Map<String, Object>> list = new ArrayList<>();
        for (int year : years) {
            Map<String, Object> map = new LinkedHashMap<>();
            List<Map<String, Object>> maps = articleMapper.selectArticleByYear(String.valueOf(year));
            map.put("year", year);
            map.put("data", maps);
            list.add(map);
        }
        return ResultJson.ok(list);
    }

    @Override
    public ResultJson<Map<String, Object>> selectArticleByParam(ArticlePageParam articlePageParam) {
        Page<Article> page = new Page<>();
        page.setCurrent(articlePageParam.getCurrent());
        page.setSize(articlePageParam.getSize());
        QueryWrapper<Article> articleQueryWrapper = buildQuery(articlePageParam);
        Page<Article> articles = articleMapper.selectPage(page, articleQueryWrapper);
        long total = articles.getTotal();
        List<Article> records = articles.getRecords();
        List<ArticleDto> articleDtoList = new ArrayList<>();
        // 标签查询
        records.forEach(
                item -> {
                    // 查标签
                    QueryWrapper<ArticleTag> articleTagQueryWrapper1 = new QueryWrapper<>();
                    articleTagQueryWrapper1.eq("article_id", item.getId());
                    List<ArticleTag> articleTags = articleTagService.list(articleTagQueryWrapper1);
                    List<Long> tagsId =
                            articleTags.stream().map(ArticleTag::getTagId).collect(Collectors.toList());
                    Collection<Tag> tags = tagService.listByIds(tagsId);

                    //查分类
                    QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
                    categoryQueryWrapper.eq("id", item.getCategoryId());
                    Category category = categoryService.getOne(categoryQueryWrapper);

                    //数据封装
                    ArticleDto articleDto = new ArticleDto();
                    BeanUtil.copyProperties(item, articleDto);
                    articleDto.setCategory(category);
                    articleDto.setTags((List<Tag>) tags);
                    articleDto.setTagsId(tagsId);
                    articleDtoList.add(articleDto);
                });
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("total", total);
        dataMap.put("articles", articleDtoList);
        return ResultJson.ok(dataMap);
    }

    /**
     * 把标签插入数据库
     *
     * @param tags      标签的id的列表
     * @param articleId 文章的id
     */
    private void addTags(List<Integer> tags, Long articleId) {
        tags.forEach(
                item -> {
                    ArticleTag articleTag = new ArticleTag();
                    articleTag.setArticleId(articleId);
                    articleTag.setTagId(Long.valueOf(item));
                    articleTagMapper.insert(articleTag);
                });
    }

    /**
     * 保存分类
     */
    private void addArticleCategory(Long articleId, Long categoryId) {
        ArticleCategory articleCategory = new ArticleCategory();
        articleCategory.setArticleId(articleId);
        articleCategory.setCategoryId(categoryId);
        articleCategoryMapper.insert(articleCategory);
    }

    /**
     * 构建查询参数
     */
    public QueryWrapper<Article> buildQuery(ArticlePageParam articlePageParam) {
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(articlePageParam.getSearchKey())) {
            articleQueryWrapper.like("title", articlePageParam.getSearchKey());
        }
        Map<String, String> map = new HashMap<>();
        if (articlePageParam.getCategoryId() != null) {
            map.put("category_id", articlePageParam.getCategoryId().toString());

        }
        if (StrUtil.isNotBlank(articlePageParam.getState())) {
            map.put("state", articlePageParam.getState());
        }
        articleQueryWrapper.allEq(map);
        return articleQueryWrapper;
    }

}
