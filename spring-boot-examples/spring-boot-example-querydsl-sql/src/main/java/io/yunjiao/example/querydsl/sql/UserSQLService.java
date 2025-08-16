package io.yunjiao.example.querydsl.sql;

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
public class UserSQLService {
    private final UserSQLRepository userSQLRepository;

    @Transactional
    public void insertUserBatch() {
        List<User> userList = IntStream.range(0, 1000).mapToObj(i -> {
            User user = new User();
            user.setName("李四-" + i);
            user.setAge(102);

            return user;
        }).toList();
        userSQLRepository.insertUserBatch(userList);
    }

    @Transactional
    public void deleteUserBatch() {
        userSQLRepository.deleteUser(101);
    }

    public List<User> findByName(String name) {
        UserQuery query = new UserQuery().name(name)
                .orderByAge(true);
        return userSQLRepository.findByQuery(query);
    }

}
