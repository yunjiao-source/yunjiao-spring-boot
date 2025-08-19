package io.yunjiao.project.rql.basic;

import com.querydsl.core.types.dsl.BooleanExpression;
import io.yunjiao.project.rql.basic.jpa.SearchCriteria;
import io.yunjiao.project.rql.basic.querydsl.UserPredicatesBuilder;
import io.yunjiao.project.rql.basic.querydsl.UserQuerydslRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 接口
 *
 * @author yangyunjiao
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/basic")
public class UserController {
    private final UserService userService;
    private final UserQuerydslRepository userQuerydslRepository;

    @GetMapping("/jpa")
    public Iterable<UserEntity> findAllByJpa(@RequestParam(value = "search", required = false) String search) {
        List<SearchCriteria> params = new ArrayList<SearchCriteria>();
        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)([^,]+?),");
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                params.add(new SearchCriteria(matcher.group(1),
                        matcher.group(2), matcher.group(3)));
            }
        }
        return userService.searchUser(params);
    }

    @GetMapping("/querydsl")
    public Iterable<UserEntity> findAllByQueryDSL(@RequestParam(value = "search", required = false) String search) {
        UserPredicatesBuilder builder = new UserPredicatesBuilder();

        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)([^,]+?),");
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }
        }
        BooleanExpression exp = builder.build();
        return userQuerydslRepository.findAll(exp);
    }
}
