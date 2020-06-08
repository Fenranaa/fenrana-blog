package cn.fenrana.blog.service;

import cn.fenrana.blog.entity.Log;
import cn.fenrana.blog.entity.param.LogParam;
import cn.fenrana.blog.utils.ResultJson;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 系统日志表 服务类
 * </p>
 *
 * @author Fenrana
 * @since 2019-11-05
 */
public interface ILogService extends IService<Log> {

    ResultJson<Page<Log>> getLogs(LogParam logParam);

    ResultJson<Object> deleteLogById(Long id);

    ResultJson<Object> deleteAll();
}
