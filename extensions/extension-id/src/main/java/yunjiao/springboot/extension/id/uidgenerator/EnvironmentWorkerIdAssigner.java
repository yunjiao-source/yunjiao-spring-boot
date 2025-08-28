package yunjiao.springboot.extension.id.uidgenerator;

import cc.siyecao.uid.core.resposity.WorkerIdAssigner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import yunjiao.springboot.extension.common.CommonConsts;
import yunjiao.springboot.extension.common.util.Utils;

/**
 * 从系统环境变量获取workId
 *
 * @author yangyunjiao
 */
@Slf4j
@RequiredArgsConstructor
public class EnvironmentWorkerIdAssigner implements WorkerIdAssigner {
    private final Environment env;

    @Override
    public long assignWorkerId() {
        long workerId = Utils.convertEnv(env, CommonConsts.ENV_SNOWFLAKE_WORKER_ID, Long.class, 1L);

        if (workerId == 1L) {
            log.info("Uid-Generator 框架雪花算法配置使用默认参数。如需支持分布式，请设置系统环境变量：{}", CommonConsts.ENV_SNOWFLAKE_WORKER_ID);
        }
        return workerId;
    }
}
