package cn.fenrana.blog.controller;


import cn.fenrana.blog.entity.Log;
import cn.fenrana.blog.entity.param.LogParam;
import cn.fenrana.blog.service.ILogService;
import cn.fenrana.blog.utils.ResultJson;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.util.List;

/**
 * <p>
 * 系统日志表 前端控制器
 * </p>
 *
 * @author Fenrana
 * @since 2019-11-05
 */
@RestController
@RequestMapping("admin/log")
public class LogController {

    @Resource
    private ILogService logService;

    /**
     * 根据条件返回所有的log表信息
     */
    @GetMapping
    public ResultJson<Page<Log>> getLogs(LogParam logParam) {
        try {
            return logService.getLogs(logParam);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultJson.fail();
        }
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public ResultJson<Object> deleteLogById(@PathVariable Long id) {
        try {
            return logService.deleteLogById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultJson.fail();
        }
    }

    /**
     * 删除全部的内容
     */
    @GetMapping("/deleteAll")
    public ResultJson<Object> deleteAll() {
        try {
            return logService.deleteAll();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultJson.fail();
        }
    }

}
