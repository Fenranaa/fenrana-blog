package cn.fenrana.blog.controller;


import cn.fenrana.blog.entity.Attachment;
import cn.fenrana.blog.entity.param.AttachmentPageParam;
import cn.fenrana.blog.service.IAttachmentService;
import cn.fenrana.blog.utils.ResultJson;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 附件
 * </p>
 *
 * @author Fenrana
 * @since 2020-01-11
 */
@RestController
@RequestMapping("admin/file")
public class AttachmentController {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private IAttachmentService attachmentService;


    /**
     * 根据条件查询全部的附件
     */
    @PostMapping("/filesByQuery")
    public ResultJson<IPage<Attachment>> filesByQuery(@RequestBody AttachmentPageParam attachmentPageParam) {
        try {
            return attachmentService.filesByQuery(attachmentPageParam);
        }catch (Exception e) {
            e.printStackTrace();
            return ResultJson.ok();
        }
    }

    /**
     * 分页返回所有的图片
     *
     * @param current 当前页
     * @param size    查询的条数
     */
    @GetMapping("/images")
    public ResultJson<Map<String, Object>> images(@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "8") Integer size) {
        try {
            return attachmentService.images(current, size);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultJson.fail();
        }
    }

    /**
     * 删除附件
     */
    @DeleteMapping("/{id}")
    public ResultJson<String> delete(@PathVariable Long id, @RequestParam(value = "path", required = false) String path) {
        return attachmentService.delete(id, path);
    }
    
    /**
     * 附件上传
     */
    @RequestMapping("/upload")
    public ResultJson<List<Attachment>> upload(@RequestParam("files") MultipartFile[] files) {
        return attachmentService.upload(files);
    }

    /**
     * 返后文件的所有种类，以后缀名区分
     *
     * @return List<String> 后缀名集合
     */
    @GetMapping("/suffix")
    public ResultJson<List<String>> getAllSuffix() {
        return attachmentService.getAllSuffix();
    }

    /**
     * 返回文件的储存位置
     * */
    @GetMapping("/getFileSaveLocation")
    public ResultJson<List<String>> getSaveLocation() {
        try {
            return attachmentService.getFileSaveLocation();
        }catch (Exception e) {
            e.printStackTrace();
            return ResultJson.fail();
        }

    }
}
