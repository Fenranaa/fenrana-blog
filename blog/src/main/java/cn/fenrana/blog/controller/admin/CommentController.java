package cn.fenrana.blog.controller.admin;


import cn.fenrana.blog.entity.Comment;
import cn.fenrana.blog.utils.ResultJson;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("admin/addComment")
    public ResultJson addComment(Comment comment) {
        return null;
    }

}
