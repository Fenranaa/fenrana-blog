package cn.fenrana.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统日志表
 * </p>
 *
 * @author Fenrana
 * @since 2019-11-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;

    public Log(String operation, String method) {
        this.operation = operation;
        this.method = method;
    }

    public Log(String operation, String method, String params) {
        this.operation = operation;
        this.method = method;
        this.params = params;
    }

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 操作用户
     */
    private String username;

    /**
     * 操作描述
     */
    private String operation;

    /**
     * 操作方法
     */
    private String method;

    /**
     * 操作参数
     */
    private String params;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 操作时间
     */
    private Long createTime;

    /**
     * 请求url
     */
    private String url;

    /**
     * 请求来自那个设备， 比如pc， android，平板
     */
    private String device;
    /**
     * 浏览器名称
     */
    private String browserName;
    /**
     * 浏览器版本
     */
    private String browserVersion;


}
