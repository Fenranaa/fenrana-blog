package cn.fenrana.blog.controller;


import cn.fenrana.blog.entity.Article;
import cn.fenrana.blog.entity.Attachment;
import cn.fenrana.blog.service.IAttachmentService;
import cn.fenrana.blog.utils.ResultJson;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

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
    private IAttachmentService attachmentService;

    /**
     * 分页返回附件数据信息
     * @return currnt 当前页
     * @return size 查询的条数
     * */
    @GetMapping
    public ResultJson<IPage<Attachment>> files(@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "10") Integer size) {
        try {
            Page<Attachment> page = new Page<>();
            page.setCurrent(current);
            page.setSize(size);
            IPage<Attachment> attachmentIPage = attachmentService.page(page);
            return ResultJson.ok(attachmentIPage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultJson.fail();
        }
    }

    /**
     * 删除附件
     * */
    @DeleteMapping("/{id}")
    public ResultJson<String> delete(@PathVariable Long id, @RequestParam String path)  {
        return attachmentService.delete(id, path);
    }

    /**
     * 附件上传
     * */
    @PostMapping("/upload")
    public ResultJson<List<String>> upload(@RequestParam("multipartFile") MultipartFile[] file) {

        return attachmentService.upload(file);
    }

}
