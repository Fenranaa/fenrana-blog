package cn.fenrana.blog.service;

import cn.fenrana.blog.entity.Attachment;
import cn.fenrana.blog.utils.ResultJson;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IAttachmentService extends IService<Attachment> {

    /**
     * 附件上传
     * */
    String upload(MultipartFile multipartFile);

    /**
     * 批量上传
     * */
    ResultJson<List<String>> upload(MultipartFile[] files);
    /**
     * 删除附件
     * */
    ResultJson<String> delete(Long id, String path);

    ResultJson<Map<String, Object>> images(Integer current, Integer size);
}
