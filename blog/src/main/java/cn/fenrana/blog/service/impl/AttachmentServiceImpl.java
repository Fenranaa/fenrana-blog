package cn.fenrana.blog.service.impl;

import cn.fenrana.blog.entity.Attachment;
import cn.fenrana.blog.mapper.AttachmentMapper;
import cn.fenrana.blog.service.IAttachmentService;
import cn.fenrana.blog.utils.ResultJson;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AttachmentServiceImpl extends ServiceImpl<AttachmentMapper, Attachment> implements IAttachmentService {

    @Autowired
    private AttachmentMapper attachmentMapper;
    /**
     * 附件上传
     * */
    @Override
    public String upload(MultipartFile multipartFile) {

        Attachment attachment = new Attachment();



      try {
          String filName = multipartFile.getOriginalFilename();
          String contentType = multipartFile.getContentType();
          long size = multipartFile.getSize();

          String[] fileNameSplit = filName.split("\\.");
          attachment.setName(fileNameSplit[0]);
          attachment.setSuffix(fileNameSplit[1]);
          LocalDateTime now = LocalDateTime.now();
          attachment.setCreateTime(now);
          attachment.setMediaType(contentType);
          attachment.setSize(size);
          //给附件名添加随机字符串,避免重名文件
          String a = "-" + RandomUtil.randomString(7) + ".";
          filName ="upload/" + filName.replace(".", a);
          attachment.setPath(filName);
          attachment.setThumbPath(filName);

          String path = ResourceUtils.getURL("classpath:").getPath();
          File file = new File(path + "static/" + filName);

          multipartFile.transferTo(file);
          int insert = attachmentMapper.insert(attachment);
          return filName;
      } catch (Exception e){
          e.printStackTrace();
          throw new RuntimeException("图片上传失败");
      }



    }
    /**
     * 批量上传文件
     * */
    @Override
    public ResultJson<List<String>> upload(MultipartFile[] files) {


        try {
            List<String> urls = new ArrayList<>();
            for (MultipartFile multipartFile : files) {
                String url = upload(multipartFile);
                urls.add(url);
            }
            return ResultJson.ok(urls);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultJson.fail();
        }
    }

    /**
     * 删除附件
     * */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultJson<String> delete(Long id, String path) {

        try {
            attachmentMapper.deleteById(id);
            String path1 = ResourceUtils.getURL("classpath:").getPath();
            File file = new File(path1 + "static/" + path);
            boolean isDelete = file.delete();
            if (!isDelete) {
                //如果文件删除失败回滚事务
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            return ResultJson.ok("文件删除成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultJson.fail();
        }
    }
}
