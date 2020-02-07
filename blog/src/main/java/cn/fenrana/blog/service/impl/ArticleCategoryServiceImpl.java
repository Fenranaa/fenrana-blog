package cn.fenrana.blog.service.impl;

import cn.fenrana.blog.entity.ArticleCategory;
import cn.fenrana.blog.mapper.ArticleCategoryMapper;
import cn.fenrana.blog.service.IArticleCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章&&分类关联表 服务实现类
 * </p>
 *
 * @author Fenrana
 * @since 2019-11-05
 */
@Service
public class ArticleCategoryServiceImpl extends ServiceImpl<ArticleCategoryMapper, ArticleCategory> implements IArticleCategoryService {

}
