package cn.fenrana.blog.service.impl;

import cn.fenrana.blog.entity.ArticleTag;
import cn.fenrana.blog.mapper.ArticleTagMapper;
import cn.fenrana.blog.service.IArticleTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章&&标签关联表 服务实现类
 * </p>
 *
 * @author Fenrana
 * @since 2019-11-05
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements IArticleTagService {

}
