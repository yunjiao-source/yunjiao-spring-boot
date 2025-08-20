package io.yunjiao.project.rql.persistence.query;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import io.yunjiao.project.rql.persistence.dao.UserRepository;
import io.yunjiao.project.rql.persistence.dao.rsql.CustomRsqlVisitor;
import io.yunjiao.project.rql.persistence.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RsqlIntegrationTest {

    @Autowired
    private UserRepository repository;

    private User userJohn;

    private User userTom;

    @BeforeEach
    public void init() {
        userJohn = new User();
        userJohn.setFirstName("john");
        userJohn.setLastName("doe");
        userJohn.setEmail("john@doe.com");
        userJohn.setAge(22);
        repository.save(userJohn);

        userTom = new User();
        userTom.setFirstName("tom");
        userTom.setLastName("doe");
        userTom.setEmail("tom@doe.com");
        userTom.setAge(26);
        repository.save(userTom);

        repository.flush();

    }

    @Test
    public void givenFirstAndLastName_whenGettingListOfUsers_thenCorrect() {
        final Node rootNode = new RSQLParser().parse("firstName==john;lastName==doe");
        final Specification<User> spec = rootNode.accept(new CustomRsqlVisitor<>());
        final List<User> results = repository.findAll(spec);

        assertThat(userJohn).isIn(results);
        assertThat(userTom).isNotIn(results);
    }

    @Test
    public void givenFirstNameInverse_whenGettingListOfUsers_thenCorrect() {
        final Node rootNode = new RSQLParser().parse("firstName!=john");
        final Specification<User> spec = rootNode.accept(new CustomRsqlVisitor<>());
        final List<User> results = repository.findAll(spec);

        assertThat(userTom).isIn(results);
        assertThat(userJohn).isNotIn(results);
    }

    @Test
    public void givenMinAge_whenGettingListOfUsers_thenCorrect() {
        final Node rootNode = new RSQLParser().parse("age>25");
        final Specification<User> spec = rootNode.accept(new CustomRsqlVisitor<User>());
        final List<User> results = repository.findAll(spec);

        assertThat(userTom).isIn(results);
        assertThat(userJohn).isNotIn(results);
    }

    @Test
    public void givenFirstNamePrefix_whenGettingListOfUsers_thenCorrect() {
        final Node rootNode = new RSQLParser().parse("firstName==jo*");
        final Specification<User> spec = rootNode.accept(new CustomRsqlVisitor<User>());
        final List<User> results = repository.findAll(spec);

        assertThat(userJohn).isIn(results);
        assertThat(userTom).isNotIn(results);
    }

    @Test
    public void givenListOfFirstName_whenGettingListOfUsers_thenCorrect() {
        final Node rootNode = new RSQLParser().parse("firstName=in=(john,jack)");
        final Specification<User> spec = rootNode.accept(new CustomRsqlVisitor<User>());
        final List<User> results = repository.findAll(spec);

        assertThat(userJohn).isIn(results);
        assertThat(userTom).isNotIn(results);
    }
}
