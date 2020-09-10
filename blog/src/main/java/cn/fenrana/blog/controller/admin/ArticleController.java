package cn.fenrana.blog.controller.admin;


import cn.fenrana.blog.entity.*;
import cn.fenrana.blog.entity.dto.ArticleDto;
import cn.fenrana.blog.entity.param.ArticlePageParam;
import cn.fenrana.blog.utils.ResultJson;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.fenrana.blog.service.IArticleService;
import cn.fenrana.blog.service.IArticleTagService;
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
    ICategoryService iCategoryService;

    @Autowired
    ITagService iTagService;

    /**
     * 保存文章
     */
    @PostMapping(value = "/admin/addArticle")
    public ResultJson<Object> addArticle(@RequestBody Map<String, Object> map) {
        try {
            if (ObjectUtil.isEmpty(map.get("id"))){
                return iArticleService.addArticle(map);
            }else {
                return  iArticleService.updateArticle(map);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResultJson.fail();
        }

    }

    /*×
     * 查询文章
     * */
    @PostMapping("admin/articles")
    public ResultJson<Map<String, Object>> articleList(@RequestBody ArticlePageParam pageParam) {
        Page<Article> page = new Page<>();
        page.setCurrent(pageParam.getCurrent());
        page.setSize(pageParam.getSize());
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        //要查询的列
        queryWrapper.select("id", "title", "cover","content", "category_id", "state", "publish_time", "create_time", "type", "visits", "summary", "disallow_comment", "is_top");
        //根据标题模糊查询调价
        if (StrUtil.isNotBlank(pageParam.getSearchKey())) {
            queryWrapper.like("title", pageParam.getSearchKey());
        }
        Map<String, String> map = new HashMap<>();
        if (pageParam.getCategoryId() != null) {
            map.put("category_id", pageParam.getCategoryId().toString());

        }
        if (StrUtil.isNotBlank(pageParam.getState())) {
            map.put("state", pageParam.getState());
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
                    List<Long> tagsId =
                            articleTags.stream().map(ArticleTag::getTagId).collect(Collectors.toList());
                    Collection<Tag> tags = iTagService.listByIds(tagsId);

                    //查分类
                    QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
                    categoryQueryWrapper.eq("id", item.getCategoryId());
                    Category category = iCategoryService.getOne(categoryQueryWrapper);
//                    item.setCategory(category.getName());

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
    public ResultJson<Object> articleDelete(@PathVariable Long id){
        try {
            return iArticleService.articleDelete(id);
        }catch (Exception e){
            e.printStackTrace();
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
