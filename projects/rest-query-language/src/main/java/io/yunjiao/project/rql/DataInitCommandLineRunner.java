package io.yunjiao.project.rql;

import io.yunjiao.project.rql.persistence.dao.MyUserRepository;
import io.yunjiao.project.rql.persistence.dao.UserRepository;
import io.yunjiao.project.rql.persistence.model.MyUser;
import io.yunjiao.project.rql.persistence.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author yangyunjiao
 */
@Component
@RequiredArgsConstructor
public class DataInitCommandLineRunner implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MyUserRepository myUserRepository;

    @Override
    public void run(String... args) throws Exception {
        userInit();
        myUserInit();
    }

    private void userInit() {
        if (!userRepository.existsByFirstName("john")) {
            User userJohn = new User();
            userJohn.setFirstName("john");
            userJohn.setLastName("doe");
            userJohn.setEmail("john@doe.com");
            userJohn.setAge(22);
            userRepository.save(userJohn);
        }

        if (!userRepository.existsByFirstName("tom")) {
            User userTom = new User();
            userTom.setFirstName("tom");
            userTom.setLastName("doe");
            userTom.setEmail("tom@doe.com");
            userTom.setAge(26);
            userRepository.save(userTom);
        }

        if (!userRepository.existsByFirstName("percy")) {
            User userPercy = new User();
            userPercy.setFirstName("percy");
            userPercy.setLastName("blackney");
            userPercy.setEmail("percy@blackney.com");
            userPercy.setAge(30);
            userRepository.save(userPercy);
        }
    }

    private void myUserInit() {
        if (!myUserRepository.existsByFirstName("john")) {
            MyUser userJohn = new MyUser();
            userJohn.setFirstName("john");
            userJohn.setLastName("doe");
            userJohn.setEmail("john@doe.com");
            userJohn.setAge(22);
            myUserRepository.save(userJohn);
        }

        if (!myUserRepository.existsByFirstName("tom")) {
            MyUser userTom = new MyUser();
            userTom.setFirstName("tom");
            userTom.setLastName("doe");
            userTom.setEmail("tom@doe.com");
            userTom.setAge(26);
            myUserRepository.save(userTom);
        }

        if (!myUserRepository.existsByFirstName("percy")) {
            MyUser userPercy = new MyUser();
            userPercy.setFirstName("percy");
            userPercy.setLastName("blackney");
            userPercy.setEmail("percy@blackney.com");
            userPercy.setAge(30);
            myUserRepository.save(userPercy);
        }
    }
}
