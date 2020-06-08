package cn.fenrana.blog.entity.param;

import cn.fenrana.blog.entity.Log;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class LogParam extends Log {


    /**
     * 当前页
     */
    private Integer current;

    /**
     * 每页显示的条数
     */
    private Integer size;
}
