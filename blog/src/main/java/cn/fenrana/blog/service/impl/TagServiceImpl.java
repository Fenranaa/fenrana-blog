package cn.fenrana.blog.service.impl;

import cn.fenrana.blog.entity.Tag;
import cn.fenrana.blog.mapper.TagMapper;
import cn.fenrana.blog.service.ITagService;
import cn.fenrana.blog.utils.ResultJson;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author Fenrana
 * @since 2019-11-05
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public ResultJson<List<Tag>> getTags(String searchKey) {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(searchKey)) {
            queryWrapper.like("name", searchKey);
        }

        List<Tag> tags = tagMapper.selectList(queryWrapper);
        return ResultJson.ok(tags);
    }
}
