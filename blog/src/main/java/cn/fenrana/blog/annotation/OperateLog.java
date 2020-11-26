package cn.fenrana.blog.annotation;

import cn.fenrana.blog.entity.enums.LogType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OperateLog {
    /**
     * 操作类型
     */
    LogType operateType() default LogType.OTHER;

    /**
     * 是否保存请求的参数
     */
    boolean isSaveRequestData() default true;

    /**
     * 操作人
     */
    String operatorType() default "Fenrana";
}
