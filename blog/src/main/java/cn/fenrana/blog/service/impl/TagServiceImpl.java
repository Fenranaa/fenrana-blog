package cn.fenrana.blog.service.impl;

import cn.fenrana.blog.entity.Tag;
import cn.fenrana.blog.mapper.TagMapper;
import cn.fenrana.blog.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
