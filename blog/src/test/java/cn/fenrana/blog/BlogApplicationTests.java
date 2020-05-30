package cn.fenrana.blog;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest
class BlogApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(HttpStatus.OK.value());

    }

}
