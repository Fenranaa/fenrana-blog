package cn.fenrana.blog.controller;


import cn.fenrana.blog.entity.Category;
import cn.fenrana.blog.entity.Tag;
import cn.fenrana.blog.service.ITagService;
import cn.fenrana.blog.utils.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 标签表 前端控制器
 * </p>
 *
 * @author Fenrana
 * @since 2019-11-05
 */
@RestController
public class TagController {

    @Autowired
    private ITagService iTagService;

    /**
     * 添加标签
     * */
    @PostMapping("/admin/addTag")
    public ResultJson<Tag> addTag(@RequestBody Tag tag) {

        boolean b = iTagService.saveOrUpdate(tag);
        if (b){
            return ResultJson.ok();
        }else {
            return ResultJson.fail();
        }

    }
    /**
     * 获得所有标签
     * */
    @GetMapping("admin/tags")
    public ResultJson<List<Tag>> getTags(){
        try{
            List<Tag> tags = iTagService.list();
            return ResultJson.ok(tags);
        }catch (Exception e){
            e.printStackTrace();
            return ResultJson.fail();
        }

    }
    /**
     * 删除标签
     * */
    @GetMapping("/admin/deleteTag/{id}")
    public ResultJson<Category> deleteCategoryById(@PathVariable Long id){
        boolean b = iTagService.removeById(id);
        if (b){
            return ResultJson.ok();
        }else {
            return ResultJson.fail();
        }

    }
}
