package yunjiao.springboot.example.id;

import cn.hutool.core.lang.Snowflake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import yunjiao.springboot.extension.id.uidgenerator.UidGeneratorCached;
import yunjiao.springboot.extension.id.uidgenerator.UidGeneratorDefault;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 示例
 *
 * @author yangyunjiao
 */
@Slf4j
@Component
public class IdCommandLineRunner implements CommandLineRunner {
    @Autowired
    private Snowflake snowflake;
    @Autowired
    private UidGeneratorCached uidGeneratorCached;
    @Autowired
    private UidGeneratorDefault uidGeneratorDefault;

    @Override
    public void run(String... args) throws Exception {
        TimeUnit.SECONDS.sleep(3);

        log.info("Snowflake = {}",  generator(() -> snowflake.nextId()).size());
        log.info("uidGeneratorCached = {}", generator(() -> uidGeneratorCached.getUID()).size() );
        log.info("uidGeneratorDefault = {}", generator(() -> uidGeneratorDefault.getUID()).size() );
    }

    private Set<Long> generator(Supplier<Long> supplier) {
        return IntStream.range(0, 4000).mapToObj(i -> supplier.get())
                .peek(l -> System.out.print(l + ","))
                .collect(Collectors.toSet());
    }
}
