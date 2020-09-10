package cn.fenrana.blog.service;

import cn.fenrana.blog.entity.Comment;
import cn.fenrana.blog.entity.param.CommentParam;
import cn.fenrana.blog.entity.param.PageParam;
import cn.fenrana.blog.utils.ResultJson;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论表 服务类
 * </p>
 *
 * @author Fenrana
 * @since 2019-11-05
 */
public interface ICommentService extends IService<Comment> {

    ResultJson<Comment> addComment(Comment comment);

    ResultJson<Comment> deleteCommentById(Long id);

    ResultJson<Object> updateStatus(Long id, Integer status);

    ResultJson<Map<String, Object>> getCommentByParam(CommentParam commentParam, PageParam pageParam);

    ResultJson<List<Comment>> getCommentByArticleId(Long id);
}
