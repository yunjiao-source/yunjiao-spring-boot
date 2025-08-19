package io.yunjiao.project.rql;

import io.yunjiao.project.rql.basic.UserEntity;
import io.yunjiao.project.rql.basic.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 添加数据执行器
 *
 * @author yangyunjiao
 */
@Component
@RequiredArgsConstructor
public class UserCommandLineRunner implements CommandLineRunner {
    private final UserJpaRepository userRepository;


    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findById(1L).isEmpty()) {
            UserEntity user = new UserEntity();
            user.setId(1L);
            user.setName("张三");
            user.setAge(28);
            user.setEmail("zhangs@qq.com");
            user.setCreatedAt(new Date());

            userRepository.save(user);
        }
    }
}
