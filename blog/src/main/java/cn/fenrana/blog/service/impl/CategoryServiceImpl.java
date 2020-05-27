package cn.fenrana.blog.service.impl;

import cn.fenrana.blog.entity.Category;
import cn.fenrana.blog.mapper.CategoryMapper;
import cn.fenrana.blog.service.ICategoryService;
import cn.fenrana.blog.utils.ResultJson;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 分类表 服务实现类
 * </p>
 *
 * @author Fenrana
 * @since 2019-11-05
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResultJson<List<Category>> getCategorys(String searchKey) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(searchKey)) {
            queryWrapper.like("name", searchKey);
        }
        return ResultJson.ok(categoryMapper.selectList(queryWrapper));


    }
}
