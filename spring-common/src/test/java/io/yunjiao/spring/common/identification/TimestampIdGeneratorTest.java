package io.yunjiao.spring.common.identification;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link TimestampIdGenerator} 单元测试用例
 *
 * @author yangyunjiao
 */
public class TimestampIdGeneratorTest {
    @Test
    public void test() {
        final int SIZE = 3000;
        Set<Long> idSet = IntStream.range(0, SIZE).parallel().mapToLong(i -> TimestampIdGenerator.next()).boxed()
                .collect(Collectors.toSet());
        assertThat(idSet).hasSize(SIZE);
        System.out.println(idSet.stream().skip(SIZE -10).collect(Collectors.toList()));
    }
}
