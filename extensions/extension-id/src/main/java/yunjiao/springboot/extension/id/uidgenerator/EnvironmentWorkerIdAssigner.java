package yunjiao.springboot.extension.id.uidgenerator;

import cc.siyecao.uid.core.resposity.WorkerIdAssigner;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import yunjiao.springboot.extension.common.CommonConsts;
import yunjiao.springboot.extension.common.util.Utils;

/**
 * 从系统环境变量获取workId
 *
 * @author yangyunjiao
 */
@RequiredArgsConstructor
public class EnvironmentWorkerIdAssigner implements WorkerIdAssigner {
    private final Environment env;

    @Override
    public long assignWorkerId() {
        return Utils.convertEnv(env, CommonConsts.ENV_SNOWFLAKE_WORKER_ID, Long.class, 1L);
    }
}
