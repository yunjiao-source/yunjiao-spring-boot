package io.yunjiao.project.rql.basic;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * 用户实体
 *
 * @author yangyunjiao
 */
@Data
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Integer age;

    private String email;

    @Column(name="birth_date")
    private Date birthDate;

    @Column(name="created_at")
    private Date createdAt;
}
