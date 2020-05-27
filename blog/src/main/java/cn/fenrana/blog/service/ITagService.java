package cn.fenrana.blog.service;

import cn.fenrana.blog.entity.Tag;
import cn.fenrana.blog.utils.ResultJson;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 标签表 服务类
 * </p>
 *
 * @author Fenrana
 * @since 2019-11-05
 */
public interface ITagService extends IService<Tag> {

    ResultJson<List<Tag>> getTags(String searchKey);
}
