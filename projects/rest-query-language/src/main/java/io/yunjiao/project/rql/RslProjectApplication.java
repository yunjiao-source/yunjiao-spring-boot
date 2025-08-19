package io.yunjiao.project.rql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启动
 *
 * @author yangyunjiao
 */
@SpringBootApplication
@EnableTransactionManagement
public class RslProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(RslProjectApplication.class, args);
    }
}
