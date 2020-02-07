package cn.fenrana.blog.service.impl;

import cn.fenrana.blog.entity.Comment;
import cn.fenrana.blog.mapper.CommentMapper;
import cn.fenrana.blog.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
