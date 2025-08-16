package io.yunjiao.example.querydsl;

import io.yunjiao.example.querydsl.sql.User;
import io.yunjiao.example.querydsl.sql.UserSQLService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 * 示例
 *
 * @author yangyunjiao
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserCommandLineRunner implements CommandLineRunner {
    private final UserSQLService userSQLService;

    @Override
    public void run(String... args) throws Exception {
        findById();
        findSingleByName();
        findList();
        findQPage();
        findPage();
        findUserByOrderStatus();
        insertUserAndUpdate();

        findByName();
        insertUserBatch();
        deleteUserBatch();
    }

    void findByName() {
        log.info("findByName = {}", userSQLService.findByName("tom").size());
    }

    void deleteUserBatch() {
        log.info("deleteUserBatch");
        userSQLService.deleteUserBatch();
    }

    void insertUserBatch() {
        log.info("insertUserBatch");
        userSQLService.insertUserBatch();
    }

    void findById() {
        User user = userSQLService.getUserSQLRepository().findById(2L);
        log.info("findById = {}", user);
    }

    void findSingleByName() {
        Optional<User> userOpt = userSQLService.getUserSQLRepository().findSingleByName("张三丰");
        log.info("findSingleByName = {}", userOpt);
    }

    void findList() {
        List<User> userList = userSQLService.getUserSQLRepository().findList("tom", 18, new Date(0));
        log.info("findList.size = {}", userList.size());
    }

    void findQPage() {
        Page<User> userPage = userSQLService.getUserSQLRepository().findQPage(30);
        log.info("findQPage = {}", userPage);
    }

    void findPage() {
        Page<User> userPage = userSQLService.getUserSQLRepository().findPage(30);
        log.info("findPage = {}", userPage);
    }

    void findUserByOrderStatus() {
        List<User> userList = userSQLService.getUserSQLRepository().findUserByOrderStatus();
        log.info("findUserByOrderStatus = {}", userList);
    }


    void insertUserAndUpdate() {
        User user = new User();
        user.setName("name");
        user.setAge(0);

        Long id = userSQLService.getUserSQLRepository().insertUser(user);
        log.info("insertUser = {}", id);

        Long count = userSQLService.getUserSQLRepository().updateName(id, "new-name");
        log.info("updateName = {}", count);
    }

}
