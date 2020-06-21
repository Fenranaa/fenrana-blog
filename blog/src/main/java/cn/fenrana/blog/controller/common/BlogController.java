package cn.fenrana.blog.controller.common;

import cn.fenrana.blog.service.IBlogService;
import cn.fenrana.blog.utils.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 公共api，展示界面使用，不需要权限
 */
@RestController
@RequestMapping("/common")
public class BlogController {

    @Autowired
    private IBlogService blogService;

    /**
     * 获取侧边栏的各种信息
     */
    @GetMapping("/asideInfo")
    public ResultJson<Map<String, Object>> getAsideInfo() {
        return blogService.getAsideInfo();
    }

    /**
     * 查询首页的文章
     */
    @GetMapping("/articles")
    public ResultJson<Map<String, Object>> getArticles(@RequestParam("current") Long current) {
        return blogService.getArticles(current);
    }
}
