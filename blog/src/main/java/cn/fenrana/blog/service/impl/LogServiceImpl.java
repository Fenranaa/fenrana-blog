package cn.fenrana.blog.service.impl;

import cn.fenrana.blog.entity.Log;
import cn.fenrana.blog.entity.param.LogParam;
import cn.fenrana.blog.mapper.LogMapper;
import cn.fenrana.blog.service.ILogService;
import cn.fenrana.blog.utils.ResultJson;
import cn.hutool.core.util.StrUtil;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    @Resource
    private LogMapper logMapper;

    @Override
    public ResultJson<Page<Log>> getLogs(LogParam logParam) {

        QueryWrapper<Log> queryWrapper = new QueryWrapper<>();
        Map<String, String> map = new HashMap<>();

        if (StrUtil.isNotBlank(logParam.getIp())) {
            map.put("ip", logParam.getIp());
        }
        if (StrUtil.isNotBlank(logParam.getBrowserName())) {
            map.put("browser_name", logParam.getBrowserName());
        }
        if (StrUtil.isNotBlank(logParam.getDevice())) {
            map.put("device", logParam.getDevice());
        }
        if (logParam.getCreateTime() != null) {
            String createTime = logParam.getCreateTime().toString();
            String[] split = createTime.split("-");
            queryWrapper.between("create_time", split[0], split[1]);
        }

        queryWrapper.allEq(map);
        Page<Log> page = new Page<>();
        page.setSize(logParam.getSize());
        page.setCurrent(logParam.getCurrent());
        Page<Log> logPage = logMapper.selectPage(page, queryWrapper);
        return ResultJson.ok(logPage);
    }

    @Override
    public ResultJson<Object> deleteLogById(Long id) {
        logMapper.deleteById(id);
        return ResultJson.ok();
    }

    @Override
    public ResultJson<Object> deleteAll() {
        logMapper.deleteAll();
        return ResultJson.ok();
    }
}
