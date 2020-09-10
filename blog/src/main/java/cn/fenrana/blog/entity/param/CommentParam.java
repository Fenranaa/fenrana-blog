package cn.fenrana.blog.entity.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode()
@Data
@AllArgsConstructor
public class CommentParam {
    private Long id;
    private Long articleId;
    private Integer status;
    private String email;
    private Integer sort;
    public CommentParam() {}
}
