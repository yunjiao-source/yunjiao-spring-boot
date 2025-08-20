package io.yunjiao.example.querydsl.jpa;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户
 *
 * @author yangyunjiao
 */
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer age;

    private String email;

    @Column(name="birth_date")
    private Date birthDate;

    @Column(name="created_at")
    private Date createdAt;

    @ToString.Exclude
    @OneToMany(
            mappedBy = "user", //
            cascade = CascadeType.ALL, // 级联所有操作（保存/更新/删除）
            orphanRemoval = true // 删除孤儿数据（子实体与父实体断开时自动删除）
    )
    private List<Order> orders = new ArrayList<>();
}
