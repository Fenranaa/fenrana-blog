package cn.fenrana.blog.controller;


import cn.fenrana.blog.entity.Category;
import cn.fenrana.blog.service.ICategoryService;
import cn.fenrana.blog.utils.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/admin/addCategory")
    public ResultJson<Category> addCategory(@RequestBody Category category) {
        boolean b = iCategoryService.saveOrUpdate(category);
        if (b) {
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
    public ResultJson<Category> deleteCategoryById(@PathVariable Long id) {
        boolean b = iCategoryService.removeById(id);
        if (b) {
            return ResultJson.ok();
        } else {
            return ResultJson.fail();
        }

    }

}
