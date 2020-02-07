package cn.fenrana.blog.service.impl;

import cn.fenrana.blog.entity.LoginLog;
import cn.fenrana.blog.mapper.LoginLogMapper;
import cn.fenrana.blog.service.ILoginLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Fenrana
 * @since 2019-11-05
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements ILoginLogService {

}
