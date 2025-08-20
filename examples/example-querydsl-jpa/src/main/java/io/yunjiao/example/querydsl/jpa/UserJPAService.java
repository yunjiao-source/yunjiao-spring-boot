package io.yunjiao.example.querydsl.jpa;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

/**
 * 服务
 *
 * @author yangyunjiao
 */
@Getter
@Service
@RequiredArgsConstructor
public class UserJPAService {
    private final UserJPARepository userJPARepository;

    @Transactional
    public void insertUserBatch() {
        List<User> userList = IntStream.range(0, 1000).mapToObj(i -> {
            User user = new User();
            user.setName("李四-" + i);
            user.setAge(102);

            return user;
        }).toList();
        userJPARepository.insertUserBatch(userList);
    }

    @Transactional
    public void deleteUserBatch() {
        userJPARepository.deleteUser(101);
    }

    public List<User> findByName(String name) {
        UserQuery query = new UserQuery().name(name)
                .orderByAge(true);
        return userJPARepository.findByQuery(query);
    }

}
