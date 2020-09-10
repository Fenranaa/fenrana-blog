package cn.fenrana.blog.service.impl;

import cn.fenrana.blog.entity.Comment;
import cn.fenrana.blog.entity.param.CommentParam;
import cn.fenrana.blog.entity.param.PageParam;
import cn.fenrana.blog.mapper.CommentMapper;
import cn.fenrana.blog.service.ICommentService;
import cn.fenrana.blog.utils.ResultJson;
import cn.fenrana.blog.utils.TimeUtils;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author Fenrana
 * @since 2019-11-05
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Resource
    private CommentMapper commentMapper;

    @Override
    public ResultJson<Comment> addComment(Comment comment) {
        comment.setTime(TimeUtils.getTimestamp());
        comment.setStatus(0);
        int insert = commentMapper.insert(comment);
        if (insert == 1) {
            return ResultJson.ok();
        } else {
            return ResultJson.fail();
        }

    }

    @Override
    public ResultJson<Comment> deleteCommentById(Long id) {
        int i = commentMapper.deleteById(id);
        if (i == 1) {
            return ResultJson.ok();
        } else {
            return ResultJson.fail();
        }
    }

    @Override
    public ResultJson<Map<String, Object>> getCommentByParam(CommentParam commentParam, PageParam pageParam) {
        Page<Comment> page = new Page<>();
        page.setSize(pageParam.getSize());
        page.setCurrent(pageParam.getCurrent());
        QueryWrapper<Comment> queryWrapper = buildCommentQuery(commentParam);
        Page<Comment> commentPage = commentMapper.selectPage(page, queryWrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("total", commentPage.getTotal());
        map.put("comments", commentPage.getRecords());
        return ResultJson.ok(map);
    }

    @Override
    public ResultJson<List<Comment>> getCommentByArticleId(Long articleId) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("time");
        queryWrapper.eq("article_id", articleId);
        queryWrapper.eq("status", 1);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        return ResultJson.ok(comments);
    }

    @Override
    public ResultJson<Object> updateStatus(Long id, Integer status) {
        Comment comment = new Comment();
        comment.setStatus(status);
        comment.setId(id);
        commentMapper.updateById(comment);
        return ResultJson.ok();
    }

    /**
     * 构建comment的查询条件
     */
    private QueryWrapper<Comment> buildCommentQuery(CommentParam commentParam) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotEmpty(commentParam.getId())) {
            queryWrapper.eq("id", commentParam.getId());
        }
        if (ObjectUtil.isNotEmpty(commentParam.getArticleId())) {
            queryWrapper.eq("article_id", commentParam.getArticleId());
        }
        if (ObjectUtil.isNotEmpty(commentParam.getStatus())) {
            queryWrapper.eq("status", commentParam.getStatus());
        }
        if (ObjectUtil.isNotEmpty(commentParam.getEmail())) {
            queryWrapper.eq("email", commentParam.getEmail());
        }
        if (ObjectUtil.isNotEmpty(commentParam.getSort())) {
            queryWrapper.eq("sort", commentParam.getSort());
        }

        return queryWrapper;
    }

}
