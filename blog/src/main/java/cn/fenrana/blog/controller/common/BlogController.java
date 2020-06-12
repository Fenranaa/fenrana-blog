package cn.fenrana.blog.controller.common;

import cn.fenrana.blog.utils.ResultJson;
import org.springframework.web.bind.annotation.RestController;

/**
 * 公共api，展示界面使用，不需要权限
 * */
@RestController
public class BlogController {
    public ResultJson<Object> getTas() {
        return null;
    }
}
