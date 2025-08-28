package yunjiao.springboot.autoconfigure.id;

import cc.siyecao.uid.core.UidGenerator;
import cc.siyecao.uid.core.impl.CachedUidGenerator;
import cc.siyecao.uid.core.impl.DefaultUidGenerator;
import cc.siyecao.uid.core.resposity.WorkerIdAssigner;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import yunjiao.springboot.extension.id.uidgenerator.EnvironmentWorkerIdAssigner;
import yunjiao.springboot.extension.id.uidgenerator.UidGeneratorCached;
import yunjiao.springboot.extension.id.uidgenerator.UidGeneratorDefault;

/**
 * 基于Uid-Generator框架的自动配置
 *
 * @author yangyunjiao
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({UidGenerator.class})
@EnableConfigurationProperties({UidGeneratorProperties.class})
public class UidGeneratorConfiguration {
    /**
     * {@link PostConstruct} 注解方法
     */
    @PostConstruct
    public void postConstruct() {
        log.info("Uid Generator Configuration");
    }

    @Bean
    WorkerIdAssigner workerIdAssigner(Environment env) {
        EnvironmentWorkerIdAssigner bean = new EnvironmentWorkerIdAssigner(env);
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [Environment Worker Id Assigner:{}]", bean);
        }
        return bean;
    }

    @Bean
    UidGeneratorDefault uidGeneratorDefault(UidGeneratorProperties properties,
                                            WorkerIdAssigner workerIdAssigner) throws Exception {
        DefaultUidGenerator defaultUidGenerator = new DefaultUidGenerator();
        defaultUidGenerator.setEpochStr(properties.getEpochStr());
        defaultUidGenerator.setSeqBits(properties.getSeqBits());
        defaultUidGenerator.setTimeBits(properties.getTimeBits());
        defaultUidGenerator.setWorkerBits(properties.getWorkerBits());
        defaultUidGenerator.setWorkerIdAssigner(workerIdAssigner);
        defaultUidGenerator.afterPropertiesSet();

        UidGeneratorDefault bean = new UidGeneratorDefault(defaultUidGenerator);
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [Uid Generator Default:{}]", bean);
        }
        return bean;
    }

    @Bean
    UidGeneratorCached uidGeneratorCached(UidGeneratorProperties properties,
                                          WorkerIdAssigner workerIdAssigner,
                                          ObjectProvider<CachedUidGeneratorConfigurer> configurers) throws Exception {
        CachedUidGenerator cachedUidGenerator = new CachedUidGenerator();
        cachedUidGenerator.setEpochStr(properties.getEpochStr());
        cachedUidGenerator.setSeqBits(properties.getSeqBits());
        cachedUidGenerator.setTimeBits(properties.getTimeBits());
        cachedUidGenerator.setWorkerBits(properties.getWorkerBits());
        cachedUidGenerator.setWorkerIdAssigner(workerIdAssigner);
        configurers.orderedStream().forEach(configurer -> configurer.configure(cachedUidGenerator));

        cachedUidGenerator.afterPropertiesSet();

        UidGeneratorCached bean = new UidGeneratorCached(cachedUidGenerator);
        if (log.isDebugEnabled()) {
            log.debug("Configure Bean [Uid Generator Cached:{}]", bean);
        }
        return bean;
    }
}
