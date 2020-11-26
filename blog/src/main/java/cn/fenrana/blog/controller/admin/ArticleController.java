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
    //TODO 去掉接受参数的map
    @PostMapping(value = "/admin/addArticle")
    public ResultJson<Object> addArticle(@RequestBody Map<String, Object> map) {
        try {
            if (ObjectUtil.isEmpty(map.get("id"))) {
                return iArticleService.addArticle(map);
            } else {
                return iArticleService.updateArticle(map);
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

        return iArticleService.selectArticleByParam(pageParam);
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
     */
    @GetMapping("admin/articleDelete/{id}")
    public ResultJson<Object> articleDelete(@PathVariable Long id) {
        try {
            return iArticleService.articleDelete(id);
        } catch (Exception e) {
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
