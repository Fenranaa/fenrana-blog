package cn.fenrana.blog.entity.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class PageParam {
    private Integer size;
    private Integer current;

    public PageParam() {
        this.current = 1;
        this.size = 10;
    }
}
