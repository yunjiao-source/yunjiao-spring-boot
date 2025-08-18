package io.yunjiao.extension.querydsl.sql;

import lombok.Data;

import java.util.Date;

/**
 * 订单实体
 *
 * @author yangyunjiao
 */
@Data
public class Order {
    private Long id;
    private String status;
    private Long userId;
    private String email;
    private String birthData;
    private Date transactionTime;
}
