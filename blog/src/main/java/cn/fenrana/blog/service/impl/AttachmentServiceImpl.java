package cn.fenrana.blog.service.impl;

import cn.fenrana.blog.entity.Attachment;
import cn.fenrana.blog.entity.AttachmentPageQuery;
import cn.fenrana.blog.mapper.AttachmentMapper;
import cn.fenrana.blog.service.IAttachmentService;
import cn.fenrana.blog.utils.ResultJson;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class AttachmentServiceImpl extends ServiceImpl<AttachmentMapper, Attachment> implements IAttachmentService {

    @Autowired
    private AttachmentMapper attachmentMapper;

    @Value("${server.name}")
    private String name;

    @Value("${server.port}")
    private String port;

    /**
     * 附件上传
     */
    @Override
    public Attachment upload(MultipartFile multipartFile) {

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
            filName = "upload/" + filName.replace(".", a);
            attachment.setPath(filName);
            attachment.setThumbPath(filName);

            String path = ResourceUtils.getURL("classpath:").getPath();
            File file = new File(path + "static/" + filName);

            multipartFile.transferTo(file);
            int insert = attachmentMapper.insert(attachment);
            attachment.setPath("http://" + name + ":" + port + "/" + attachment.getPath());
            return attachment;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("图片上传失败");
        }


    }

    /**
     * 批量上传文件
     */
    @Override
    public ResultJson<List<Attachment>> upload(MultipartFile[] files) {


        try {
            List<Attachment> urls = new ArrayList<>();
            for (MultipartFile multipartFile : files) {
                Attachment attachment = upload(multipartFile);
                urls.add(attachment);
            }
            return ResultJson.ok(urls);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultJson.fail();
        }
    }

    /**
     * 删除附件
     */
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

    @Override
    public ResultJson<Map<String, Object>> images(Integer current, Integer size) {
        Page<Attachment> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        QueryWrapper<Attachment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("suffix", "png");
        queryWrapper.or();
        queryWrapper.eq("suffix", "jpg");
        queryWrapper.or();
        queryWrapper.eq("suffix", "gif");
//        IPage<Attachment> attachmentIPage = attachmentService.page(page, queryWrapper);
        IPage<Attachment> attachmentIPage = attachmentMapper.selectPage(page, queryWrapper);
        List<Attachment> records = attachmentIPage.getRecords();

        records.forEach(item -> {
            item.setPath("http://" + name + ":" + port + "/" + item.getPath());
        });
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("total", attachmentIPage.getTotal());
        dataMap.put("data", records);
        return ResultJson.ok(dataMap);
    }

    /**
     * 返后文件的所有种类，以后缀名区分
     *
     * @return List<String> 后缀名集合
     */
    @Override
    public ResultJson<List<String>> getAllSuffix() {
        return getGroupByField("suffix");
    }

    @Override
    public ResultJson<IPage<Attachment>> filesByQuery(AttachmentPageQuery attachmentPageQuery) {
        QueryWrapper<Attachment> queryWrapper = new QueryWrapper<>();

        if (StrUtil.isNotBlank(attachmentPageQuery.getSearchKey())) {
            queryWrapper.like("name", attachmentPageQuery.getSearchKey());
        }
        Map<String, String> map = new HashMap<>();
        if (StrUtil.isNotBlank(attachmentPageQuery.getMediaType())) {
            map.put("media_type", attachmentPageQuery.getMediaType());
        }

        if (StrUtil.isNotBlank(attachmentPageQuery.getSuffix())) {
            map.put("suffix", attachmentPageQuery.getSuffix());
        }
        if (StrUtil.isNotBlank(attachmentPageQuery.getSaveLocation())) {
            map.put("save_location", attachmentPageQuery.getSaveLocation());
        }
        queryWrapper.allEq(map);
        Page<Attachment> page = new Page<>();
        page.setSize(attachmentPageQuery.getSize());
        page.setCurrent(attachmentPageQuery.getCurrent());
        IPage<Attachment> attachmentIPage = attachmentMapper.selectPage(page, queryWrapper);

        return ResultJson.ok(attachmentIPage);
    }

    /**
     * 返回文件的储存位置
     */
    @Override
    public ResultJson<List<String>> getFileSaveLocation() {
        return getGroupByField("save_location");
    }

    /**
     * 返回分组集合 group by
     */
    private ResultJson<List<String>> getGroupByField(String field) {
        QueryWrapper<Attachment> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(field);
        queryWrapper.groupBy(field);
        List<Attachment> attachments = attachmentMapper.selectList(queryWrapper);
        List<String> collect = attachments.stream().map(item -> {
            if (field.equals("suffix")) {
                return item.getSuffix();
            } else {
                return item.getSaveLocation();
            }
        }).collect(Collectors.toList());
        return ResultJson.ok(collect);
    }
}
