package io.yunjiao.spring.querydsl.jpa;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单
 *
 * @author yangyunjiao
 */
@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 推荐懒加载
    @JoinColumn(name = "user_id") // 指定外键列名
    private User user;

    private String status;

    private BigDecimal amount;

    @Column(name="transaction_time")
    private Date transactionTime;
}
