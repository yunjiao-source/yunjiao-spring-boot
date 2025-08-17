package io.yunjiao.example.simple;

import cn.hutool.core.lang.Snowflake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 示例
 *
 * @author yangyunjiao
 */
@Slf4j
@Component
public class SimpleCommandLineRunner implements CommandLineRunner {
    @Autowired
    private Snowflake snowflake;

    @Override
    public void run(String... args) throws Exception {
        log.info("Snowflake = {}", snowflake.nextId());
    }

}
