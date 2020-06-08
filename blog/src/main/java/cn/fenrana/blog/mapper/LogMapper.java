package cn.fenrana.blog.mapper;

import cn.fenrana.blog.entity.Log;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 系统日志表 Mapper 接口
 * </p>
 *
 * @author Fenrana
 * @since 2019-11-05
 */
public interface LogMapper extends BaseMapper<Log> {

    /**
     * qing
     * */
    @Update("truncate table log")
    void deleteAll();
}
