package cn.fenrana.blog.controller.common;

import cn.fenrana.blog.entity.Article;
import cn.fenrana.blog.entity.Comment;
import cn.fenrana.blog.entity.param.ArticlePageParam;
import cn.fenrana.blog.service.IArticleService;
import cn.fenrana.blog.service.IBlogService;
import cn.fenrana.blog.service.ICommentService;
import cn.fenrana.blog.utils.ResultJson;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

/**
 * 公共api，展示界面使用，不需要权限
 */
@RestController
@RequestMapping("/common")
public class BlogController {

    @Autowired
    private IBlogService blogService;

    @Autowired
    private ICommentService commentService;

    @Autowired
    private IArticleService articleService;


    /**
     * 获取侧边栏的各种信息
     */
    @GetMapping("/asideInfo")
    public ResultJson<Map<String, Object>> getAsideInfo() {
        return blogService.getAsideInfo();
    }

    /**
     * 查询首页的文章
     */
    @ApiOperation("查询首页的文章")
    @GetMapping("/articles")
    public ResultJson<Map<String, Object>> getArticles(ArticlePageParam articlePageParam) {
        return articleService.selectArticleByParam(articlePageParam);
    }

    /**
     * 根据id查询文章信息
     */
    @GetMapping("/article/{id}")
    public ResultJson<Article> getArticleById(@PathVariable(value = "id") Long id) {
        return blogService.getArticleById(id);
    }

    /**
     * 根据文章id查询审核通过的评论
     */
    @ApiOperation("根据文章id查询审核通过的评论")
    @GetMapping("/comments/article-id/{id}")
    public ResultJson<List<Comment>> getCommentByArticleId(@PathVariable("id") Long id) {
        return commentService.getCommentByArticleId(id);
    }

    /**
     * 添加评论
     */
    @ApiOperation("添加评论")
    @PostMapping("/comment")
    public ResultJson<Comment> addComment(@RequestBody Comment comment) {
        return commentService.addComment(comment);
    }

    /**
     * 分年查询文章
     */
    @ApiOperation("")
    @GetMapping("/article/year")
    public ResultJson<List<Map<String, Object>>> selectArticleByYear() {
        return articleService.selectArticleByYear();
    }
}
