package io.yunjiao.example.querydsl.sql;

import lombok.Data;

import java.util.Date;

/**
 * 用户
 *
 * @author yangyunjiao
 */
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private String birthData;
    private Date createAt;
}
