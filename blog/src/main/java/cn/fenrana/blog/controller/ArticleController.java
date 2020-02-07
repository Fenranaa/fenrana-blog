package cn.fenrana.blog.controller;


import cn.fenrana.blog.entity.*;
import cn.fenrana.blog.entity.dto.ArticleDto;
import cn.fenrana.blog.utils.ResultJson;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.fenrana.blog.service.IArticleService;
import cn.fenrana.blog.service.IArticleTagService;
import cn.fenrana.blog.service.IArticleCategoryService;
import cn.fenrana.blog.service.ITagService;
import cn.fenrana.blog.service.ICategoryService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author Fenrana
 * @since 2019-11-05
 */
@RestController
public class ArticleController {

    @Autowired
    IArticleService iArticleService;

    @Autowired
    IArticleTagService iArticleTagService;

    @Autowired
    IArticleCategoryService iArticleCategoryService;

    @Autowired
    ICategoryService iCategoryService;

    @Autowired
    ITagService iTagService;

    /**
     * 保存文章
     */
    @PostMapping(value = "/admin/addArticle")
    public ResultJson addArticle(@RequestBody Map<String, Object> map) {
        try {
            //保存文章
            Article article = BeanUtil.mapToBean(map, Article.class, false);
            article.setAuthor("Fenrana");
            article.setState("0");
            article.setVisits(0);
            //判断文章概要, 如果为空，截取内容的一部分 TODO 待完善
            if (StrUtil.isNotBlank(article.getSummary())) {

            }
            iArticleService.save(article);
            //保存标签
            Long articleId = article.getId();
            ArrayList<Integer> tags = (ArrayList) map.get("tag");
            tags.forEach(
                    item -> {
                        ArticleTag articleTag = new ArticleTag();
                        articleTag.setArticleId(articleId);
                        articleTag.setTagId(Long.valueOf(item));
                        iArticleTagService.save(articleTag);
                    });
            //保存分类
            Integer categoryId = (Integer) map.get("category");
            ArticleCategory articleCategory = new ArticleCategory();
            articleCategory.setArticleId(articleId);
            articleCategory.setCategoryId(Long.valueOf(categoryId));
            return ResultJson.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultJson.fail();
        }

    }

    /*×
     * 查询文章
     * */
    @PostMapping("admin/articles")
    public ResultJson<Map> articleList(@RequestBody PageQuery pageQuery) {
        Page<Article> page = new Page<>();
        page.setCurrent(pageQuery.getCurrent());
        page.setSize(pageQuery.getSize());
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        //要查询的列
        queryWrapper.select("id", "title", "cover", "category", "state", "publish_time", "create_time", "type", "visits", "summary", "disallowComment", "is_top");
        //根据标题模糊查询调价
        if (StrUtil.isNotBlank(pageQuery.getSearchKey())) {
            queryWrapper.like("title", pageQuery.getSearchKey());
        }
        Map<String, String> map = new HashMap<>();
        if (pageQuery.getCategoryId() != null) {
            map.put("category", pageQuery.getCategoryId().toString());

        }
        if (StrUtil.isNotBlank(pageQuery.getState())) {
            map.put("state", pageQuery.getState());
        }
        queryWrapper.allEq(map, false);
        IPage<Article> articles = iArticleService.page(page, queryWrapper);
        long total = articles.getTotal();
        List<Article> records = articles.getRecords();
        List<ArticleDto> articleDtoList = new ArrayList<>();
        // 标签查询
        records.forEach(
                item -> {
                    // 查标签
                    QueryWrapper<ArticleTag> articleTagQueryWrapper1 = new QueryWrapper<>();
                    articleTagQueryWrapper1.eq("article_id", item.getId());
                    List<ArticleTag> articleTags = iArticleTagService.list(articleTagQueryWrapper1);
                    List<Long> collect =
                            articleTags.stream().map(ArticleTag::getTagId).collect(Collectors.toList());
                    Collection<Tag> tags = iTagService.listByIds(collect);

                    //查分类
                    QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
                    categoryQueryWrapper.eq("id", item.getCategory());
                    Category category = iCategoryService.getOne(categoryQueryWrapper);
                    item.setCategory(category.getName());

                    //数据封装
                    ArticleDto articleDto = new ArticleDto();
                    BeanUtil.copyProperties(item, articleDto);
                    articleDto.setTags((List<Tag>) tags);
                    articleDtoList.add(articleDto);
                });
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("total", total);
        dataMap.put("data", articleDtoList);
        return ResultJson.ok(dataMap);
    }

    /**
     * 把文章加入回收站
     */
    @GetMapping("admin/articlesRecycle/{id}")
    public ResultJson articleRecycle(@PathVariable Long id) {
        try {
            return updateArticleState(id, "2");

        } catch (Exception e) {
            return ResultJson.fail();
        }
    }

    /**
     * 文章恢复正常模式
     */
    @GetMapping("admin/articleRestore/{id}")
    public ResultJson articleRestore(@PathVariable Long id) {
        try {
            return updateArticleState(id, "0");
        } catch (Exception e) {
            return ResultJson.fail();
        }
    }
    /**
     * 文章的删除
     * */
    @GetMapping("admin/articleDelete/{id}")
    public ResultJson articleDelete(@PathVariable Long id){
        if(iArticleService.removeById(id)){
            return ResultJson.ok();
        }else {
            return ResultJson.fail();
        }
    }
    /**
     * 修改文章状态的封装
     */
    private ResultJson updateArticleState(Long id, String state) {
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        articleQueryWrapper.eq("id", id);
        Article article = new Article();
        article.setState(state);
        if (iArticleService.update(article, articleQueryWrapper)) {
            return ResultJson.ok();
        } else {
            return ResultJson.fail();
        }
    }
}
