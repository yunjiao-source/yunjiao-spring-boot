package io.yunjiao.example.querydsl;

import io.yunjiao.example.querydsl.jpa.User;
import io.yunjiao.example.querydsl.jpa.UserJPAService;
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
    private final UserJPAService userJPAService;

    @Override
    public void run(String... args) throws Exception {
        findById();
        findOrderById();
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
        log.info("findByName = {}", userJPAService.findByName("tom").size());
    }

    void deleteUserBatch() {
        log.info("deleteUserBatch");
        userJPAService.deleteUserBatch();
    }

    void insertUserBatch() {
        log.info("insertUserBatch");
        userJPAService.insertUserBatch();
    }

    void findById() {
        User user = userJPAService.getUserJPARepository().findById(2L);
        log.info("findById = {}", user);
    }

    void findOrderById() {
        User user = userJPAService.getUserJPARepository().findOrderById(2L);
        log.info("findById = {}", user);
    }

    void findSingleByName() {
        Optional<User> userOpt = userJPAService.getUserJPARepository().findSingleByName("张三丰");
        log.info("findSingleByName = {}", userOpt);
    }

    void findList() {
        List<User> userList = userJPAService.getUserJPARepository().findList("tom", 18, new Date(0));
        log.info("findList.size = {}", userList.size());
    }

    void findQPage() {
        Page<User> userPage = userJPAService.getUserJPARepository().findQPage(30);
        log.info("findQPage = {}", userPage);
    }

    void findPage() {
        Page<User> userPage = userJPAService.getUserJPARepository().findPage(30);
        log.info("findPage = {}", userPage);
    }

    void findUserByOrderStatus() {
        List<User> userList = userJPAService.getUserJPARepository().findUserByOrderStatus();
        log.info("findUserByOrderStatus = {}", userList);
    }


    void insertUserAndUpdate() {
        User user = new User();
        user.setName("name");
        user.setAge(0);

        long count = userJPAService.getUserJPARepository().insertUser(user);
        log.info("insertUser = {}", count);

        count = userJPAService.getUserJPARepository().updateName(1L, "new-name");
        log.info("updateName = {}", count);
    }

}
