package cn.fenrana.blog;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@SpringBootTest
class BlogApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(Instant.now().toEpochMilli());

    }

}
