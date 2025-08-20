-- 创建Users表
CREATE TABLE IF NOT EXISTS Users (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NULL,
    age INT NULL,
    birth_date DATE NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- 存储过程：生成1000条随机数据
DELIMITER $$
CREATE PROCEDURE GenerateUsersData()
BEGIN
    DECLARE counter INT DEFAULT 0;
    DECLARE first_name VARCHAR(20);
    DECLARE last_name VARCHAR(20);
    DECLARE full_name VARCHAR(50);
    DECLARE user_email VARCHAR(100);
    DECLARE user_age INT;
    DECLARE birth_date DATE;

    -- 名字和姓氏池
    DROP TEMPORARY TABLE IF EXISTS name_pool;
    CREATE TEMPORARY TABLE name_pool (
        first_names VARCHAR(20),
        last_names VARCHAR(20)
    );

INSERT INTO name_pool VALUES
                          ('James','Smith'),('Mary','Johnson'),('John','Williams'),('Patricia','Brown'),
                          ('Robert','Jones'),('Jennifer','Garcia'),('Michael','Miller'),('Linda','Davis'),
                          ('William','Rodriguez'),('Elizabeth','Martinez'),('David','Hernandez'),('Barbara','Lopez'),
                          ('Richard','Gonzalez'),('Susan','Wilson'),('Joseph','Anderson'),('Jessica','Thomas');

-- 主数据生成循环
WHILE counter < 1000 DO
        -- 随机选择名字
SELECT first_names, last_names INTO first_name, last_name
FROM name_pool ORDER BY RAND() LIMIT 1;

SET full_name = CONCAT(first_name, ' ', last_name);
        SET user_email = CONCAT(
            LOWER(first_name),
            '.',
            LOWER(last_name),
            FLOOR(RAND() * 1000),
            '@',
            ELT(FLOOR(RAND() * 5) + 1, 'gmail.com','yahoo.com','hotmail.com','outlook.com','company.com')
        );

        -- 生成年龄和生日（年龄18-100岁）
        SET user_age = FLOOR(18 + RAND() * 83);
        SET birth_date = DATE_SUB(CURDATE(),
            INTERVAL user_age * 365 DAY
        );

        -- 插入数据（忽略重复email错误）
        INSERT IGNORE INTO Users (name, email, age, birth_date)
        VALUES (full_name, user_email, user_age, birth_date);

        SET counter = counter + 1;
END WHILE;
END$$
DELIMITER ;

-- 执行数据生成
CALL GenerateUsersData();

-- 验证数据量
SELECT COUNT(*) AS total_users FROM Users;

CREATE TABLE IF NOT EXISTS `orders` (
                                       `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       `user_id` BIGINT NOT NULL,
                                       `status` ENUM('pending', 'completed', 'cancelled', 'refunded') NOT NULL DEFAULT 'pending',
    `transaction_time` DATETIME NULL,
    `amount` DECIMAL(10, 2) NULL,
    INDEX `idx_user` (`user_id`),
    INDEX `idx_status` (`status`),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE
    ) ENGINE=InnoDB;

-- 3. 生成100条订单记录
INSERT INTO `orders` (`user_id`, `status`, `transaction_time`, `amount`)
SELECT
    -- 随机取用户ID (1~100)
    id,

    -- 随机状态
    ELT(FLOOR(1 + RAND() * 4), 'pending', 'completed', 'cancelled', 'refunded') AS status,

    -- 随机交易时间（近1年内）
    DATE_SUB(NOW(), INTERVAL FLOOR(RAND() * 365) DAY) + INTERVAL FLOOR(RAND() * 24) HOUR AS transaction_time,

    -- 随机金额 (10.00 ~ 5000.00)
    ROUND(10 + RAND() * 4990, 2) AS amount
FROM users limit 100;