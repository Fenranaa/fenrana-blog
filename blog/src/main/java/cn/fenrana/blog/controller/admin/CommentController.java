package cn.fenrana.blog.controller.admin;


import cn.fenrana.blog.entity.Comment;
import cn.fenrana.blog.entity.param.CommentParam;
import cn.fenrana.blog.entity.param.PageParam;
import cn.fenrana.blog.service.ICommentService;
import cn.fenrana.blog.utils.ResultJson;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论表 前端控制器
 * </p>
 *
 * @author Fenrana
 * @since 2019-11-05
 */
@RestController
public class CommentController {

    @Autowired
    private ICommentService commentService;

    /**
     * 添加评论
     * */
    @ApiOperation("添加评论")
    @PostMapping("admin/comment")
    public ResultJson<Comment> addComment(@RequestBody Comment comment) {
        return commentService.addComment(comment);
    }

    /**
     * 删除评论
     * */
    @ApiOperation("删除评论")
    @DeleteMapping("admin/commenet/{id}")
    public ResultJson<Comment> deleteCommentByid(@PathVariable("id") Long id) {
        return  commentService.deleteCommentById(id);
    }
    /**
     * 根据id查询评论
     * */
    @ApiOperation("根据id查询评论")
    @GetMapping("admin/comment/{id}")
    public ResultJson<Comment> getCommentById(@PathVariable("id") Long id) {
        Comment comment = commentService.getById(id);
        return ResultJson.ok(comment);
    }

    /**
     * 根据文章的id查询评论
     * */
    @ApiOperation("根据articleId查询评论")
    @GetMapping("admin/comments/article-id/{id}")
    public ResultJson<List<Comment>> getCommentByArticleId(@PathVariable("id") Long id) {

        return commentService.getCommentByArticleId(id);
    }

    /**
     * 根据查询条件来查询评论
     * */
    @ApiOperation("根据参数查询评论")
    @GetMapping("admin/comments")
    public ResultJson<Map<String, Object>> getCommentByParam(CommentParam commentParam, PageParam pageParam) {
        return commentService.getCommentByParam(commentParam, pageParam);
    }

    @ApiOperation("修改评论")
    @PutMapping("admin/comment")
    public ResultJson<Object> updateComment(@RequestBody Comment comment) {
        boolean b = commentService.updateById(comment);
        if(b) {
            return ResultJson.ok();
        }else {
            return ResultJson.fail();
        }
    }

    @ApiOperation("修改评论的状态")
    @PutMapping("admin/comment/{id:\\d+}/status/{status}")
    public ResultJson<Object> updateStatusBy(@PathVariable(value = "id") Long id, @PathVariable("status") Integer status) {
        return  commentService.updateStatus(id, status);
    }

}
