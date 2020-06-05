package cn.fenrana.blog.controller;


import cn.fenrana.blog.entity.Category;
import cn.fenrana.blog.entity.Log;
import cn.fenrana.blog.entity.Tag;
import cn.fenrana.blog.entity.enums.LogType;
import cn.fenrana.blog.event.LogEvent;
import cn.fenrana.blog.service.ITagService;
import cn.fenrana.blog.utils.ResultJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    ObjectMapper objectMapper = new ObjectMapper();


    private final ApplicationEventPublisher applicationEventPublisher;

    public TagController(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * 添加标签
     */
    @PostMapping("/admin/addTag")
    public ResultJson<Tag> addTag(@RequestBody Tag tag) throws JsonProcessingException {
        boolean b = iTagService.save(tag);
        //记录日志
        applicationEventPublisher.publishEvent(new LogEvent(this, "添加标签:" + tag.getName(), LogType.TAG_INSERT.value(), objectMapper.writeValueAsString(tag)));
        if (b) {
            return ResultJson.ok();
        } else {
            return ResultJson.fail();
        }

    }

    /**
     * 修改标签
     */
    @PostMapping("/admin/tag/update")
    public ResultJson<Object> updateTag(@RequestBody Tag tag) throws JsonProcessingException {
        boolean b = iTagService.updateById(tag);
        //记录日志
        applicationEventPublisher.publishEvent(new LogEvent(this, "修改标签为:" + tag.getName(),
                LogType.TAG_UPDATE.value(), objectMapper.writeValueAsString(tag)));

        if (b) {
            return ResultJson.ok();
        } else {
            return ResultJson.fail();
        }
    }

    /**
     * 根据关键词搜索标签
     *
     * @param searchKey 关键字
     */
    @GetMapping("admin/tags")
    public ResultJson<List<Tag>> getTags(@RequestParam(value = "searchKey", required = false) String searchKey) {
        try {
            return iTagService.getTags(searchKey);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultJson.fail();
        }
    }

    /**
     * 删除标签
     */
    @GetMapping("/admin/deleteTag/{id}")
    public ResultJson<Category> deleteCategoryById(@PathVariable Long id) throws JsonProcessingException {
        boolean b = iTagService.removeById(id);
        applicationEventPublisher.publishEvent(new LogEvent(this, "删除的标签的id:" + id,
                LogType.TAG_DELETE.value(), objectMapper.writeValueAsString(id)));
        if (b) {
            return ResultJson.ok();
        } else {
            return ResultJson.fail();
        }

    }
}
