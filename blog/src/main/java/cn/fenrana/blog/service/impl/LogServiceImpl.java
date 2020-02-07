package cn.fenrana.blog.service.impl;

import cn.fenrana.blog.entity.Log;
import cn.fenrana.blog.mapper.LogMapper;
import cn.fenrana.blog.service.ILogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统日志表 服务实现类
 * </p>
 *
 * @author Fenrana
 * @since 2019-11-05
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements ILogService {

}
