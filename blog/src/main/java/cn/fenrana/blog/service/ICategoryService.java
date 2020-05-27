package cn.fenrana.blog.service;

import cn.fenrana.blog.entity.Category;
import cn.fenrana.blog.utils.ResultJson;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 分类表 服务类
 * </p>
 *
 * @author Fenrana
 * @since 2019-11-05
 */
public interface ICategoryService extends IService<Category> {

    ResultJson<List<Category>> getCategorys(String searchKey);
}
