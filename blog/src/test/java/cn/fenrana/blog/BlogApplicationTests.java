package cn.fenrana.blog;


import cn.fenrana.blog.mapper.ArticleCategoryMapper;
import cn.fenrana.blog.mapper.ArticleMapper;
import cn.fenrana.blog.mapper.ArticleTagMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@SpringBootTest
class BlogApplicationTests {

    @Resource
    private ArticleCategoryMapper articleCategoryMapper;

    @Resource
    private ArticleTagMapper articleTagMapper;

    @Resource
    private ArticleMapper articleMapper;

    @Test
    void contextLoads() {
//        List<Map<String, Object>> maps = articleCategoryMapper.selectCategoryCount();
//        System.out.println(maps);
//        List<Map<String, Object>> maps = articleTagMapper.selectTagCount();
//        System.out.println(maps);
        List<Map<String, Object>> maps = articleMapper.selectArchiveCount();
        System.out.println(maps);
    }

}
