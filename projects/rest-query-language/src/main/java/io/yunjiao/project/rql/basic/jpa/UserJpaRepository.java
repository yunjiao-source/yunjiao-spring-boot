package io.yunjiao.project.rql.basic.jpa;

import io.yunjiao.project.rql.basic.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 访问仓库
 *
 * @author yangyunjiao
 */
@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {
}
