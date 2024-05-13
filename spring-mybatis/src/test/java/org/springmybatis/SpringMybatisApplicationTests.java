package org.springmybatis;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.*;
import org.springmybatis.mapper.FooMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest(classes = SpringMybatisApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
class SpringMybatisApplicationTests {

    static final Logger logger = LoggerFactory.getLogger(SpringMybatisApplicationTests.class);

    @Autowired
    FooMapper fooMapper;

    @Test
    void contextLoads() {

        LocalDateTime now = fooMapper.now();

        logger.info("NOW={}", now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
    }

}
