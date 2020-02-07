package cn.fenrana.blog.service.impl;

import cn.fenrana.blog.entity.Article;
import cn.fenrana.blog.mapper.ArticleMapper;
import cn.fenrana.blog.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author Fenrana
 * @since 2019-11-05
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

}
