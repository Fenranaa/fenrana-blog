package cn.fenrana.blog.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Attachment {

    /**id*/
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 创建时间
     * */
    private LocalDateTime createTime;
    /**
     * 附件类型
     * */
    private String mediaType;
    /**
     * 附件名字
     * */
    private String name;
    /**
     * 附件存放路径
     * */
    private String path;
    /**
     * 文件大小
     * */
    private Long size;
    /**
     * 缩略图路径
     * */
    private String thumbPath;
    /**
     * 文件后缀
     * */
    private String suffix;
}
