package cn.fenrana.blog.service.impl;

import cn.fenrana.blog.entity.User;
import cn.fenrana.blog.mapper.UserMapper;
import cn.fenrana.blog.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Fenrana
 * @since 2019-11-05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
