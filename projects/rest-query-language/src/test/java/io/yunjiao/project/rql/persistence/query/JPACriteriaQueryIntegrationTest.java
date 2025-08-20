package io.yunjiao.project.rql.persistence.query;

import io.yunjiao.project.rql.persistence.dao.UserRepository;
import io.yunjiao.project.rql.persistence.model.User;
import io.yunjiao.project.rql.web.util.SearchCriteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class JPACriteriaQueryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    private User userJohn;

    private User userTom;

    @BeforeEach
    public void init() {
        userJohn = new User();
        userJohn.setFirstName("john");
        userJohn.setLastName("doe");
        userJohn.setEmail("john@doe.com");
        userJohn.setAge(22);
        userRepository.save(userJohn);

        userTom = new User();
        userTom.setFirstName("tom");
        userTom.setLastName("doe");
        userTom.setEmail("tom@doe.com");
        userTom.setAge(26);
        userRepository.save(userTom);

        userRepository.flush();
    }

    @Test
    public void givenFirstAndLastName_whenGettingListOfUsers_thenCorrect() {
        final List<SearchCriteria> params = new ArrayList<SearchCriteria>();
        params.add(new SearchCriteria("firstName", ":", "john"));
        params.add(new SearchCriteria("lastName", ":", "doe"));
        final List<User> results = userRepository.searchUser(params);

        assertThat(userJohn).isIn(results);
        assertThat(userTom).isNotIn(results);
    }

    @Test
    public void givenLast_whenGettingListOfUsers_thenCorrect() {
        final List<SearchCriteria> params = new ArrayList<SearchCriteria>();
        params.add(new SearchCriteria("lastName", ":", "doe"));
        final List<User> results = userRepository.searchUser(params);

        assertThat(userJohn).isIn(results);
        assertThat(userTom).isIn(results);
    }

    @Test
    public void givenLastAndAge_whenGettingListOfUsers_thenCorrect() {
        final List<SearchCriteria> params = new ArrayList<SearchCriteria>();
        params.add(new SearchCriteria("lastName", ":", "doe"));
        params.add(new SearchCriteria("age", ">", "25"));
        final List<User> results = userRepository.searchUser(params);

        assertThat(userJohn).isNotIn(results);
        assertThat(userTom).isIn(results);
    }

    @Test
    public void givenWrongFirstAndLast_whenGettingListOfUsers_thenCorrect() {
        final List<SearchCriteria> params = new ArrayList<SearchCriteria>();
        params.add(new SearchCriteria("firstName", ":", "adam"));
        params.add(new SearchCriteria("lastName", ":", "fox"));
        final List<User> results = userRepository.searchUser(params);

        assertThat(userJohn).isNotIn(results);
        assertThat(userTom).isNotIn(results);
    }

    @Test
    public void givenPartialFirst_whenGettingListOfUsers_thenCorrect() {
        final List<SearchCriteria> params = new ArrayList<SearchCriteria>();
        params.add(new SearchCriteria("firstName", ":", "jo"));
        final List<User> results = userRepository.searchUser(params);

        assertThat(userJohn).isIn(results);
        assertThat(userTom).isNotIn(results);
    }
}
