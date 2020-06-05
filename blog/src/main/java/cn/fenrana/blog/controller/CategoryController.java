package cn.fenrana.blog.controller;


import cn.fenrana.blog.entity.Category;
import cn.fenrana.blog.entity.enums.LogType;
import cn.fenrana.blog.event.LogEvent;
import cn.fenrana.blog.service.ICategoryService;
import cn.fenrana.blog.utils.ResultJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 分类表 前端控制器
 * </p>
 *
 * @author Fenrana
 * @since 2019-11-05
 */
@RestController
@CrossOrigin
public class CategoryController {

    @Autowired
    ICategoryService iCategoryService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 添加分类目录
     *
     * @param category
     */
    @PostMapping("/admin/addCategory")
    public ResultJson<Category> addCategory(@RequestBody Category category) throws JsonProcessingException {
        boolean b = iCategoryService.save(category);
        if (b) {
            applicationEventPublisher.publishEvent(new LogEvent(this,  "添加分类目录:" + category.getName(),
                    LogType.TAG_UPDATE.value(), objectMapper.writeValueAsString(category)));
            return ResultJson.ok();
        } else {
            return ResultJson.fail();
        }
    }

    /**
     * 修改分类目录
     */
    @PostMapping("/admin/updateCategory")
    public ResultJson<Object> updateCategory(@RequestBody Category category) throws JsonProcessingException {
        boolean b = iCategoryService.updateById(category);
        if (b) {
            applicationEventPublisher.publishEvent(new LogEvent(this, "修改分类目录:" + category.getName(),
                    LogType.TAG_UPDATE.value(), objectMapper.writeValueAsString(category)));
            return ResultJson.ok();
        } else {
            return ResultJson.fail();
        }

    }

    /**
     * 根据关键返回所有分类的集合, 关键词为空时，返回全部的集合
     *
     * @param searchKey 关键字
     */
    @GetMapping(value = "/admin/categorys")
    public ResultJson<List<Category>> categorys(@RequestParam(value = "searchKey", required = false) String searchKey) {
        try {
            return iCategoryService.getCategorys(searchKey);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultJson.fail();
        }

    }

    /**
     * 删除分类
     */
    @GetMapping("/admin/deleteCategory/{id}")
    public ResultJson<Category> deleteCategoryById(@PathVariable Long id) throws JsonProcessingException {
        boolean b = iCategoryService.removeById(id);
        if (b) {
            applicationEventPublisher.publishEvent(new LogEvent(this, "删除的标签的id:" + id,
                    LogType.TAG_DELETE.value(), objectMapper.writeValueAsString(id)));
            return ResultJson.ok();
        } else {
            return ResultJson.fail();
        }

    }

}
