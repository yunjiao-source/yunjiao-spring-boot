package yunjiao.springboot.extension.tika.core;

import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.LinkContentHandler;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link DefaultHandlerType} 单元测试用例
 *
 * @author yangyunjiao
 */
public class DefaultHandlerTypeTest {

    @Test
    void whenCreate_thenOK() {
        assertThat(DefaultHandlerType.body.create()).isInstanceOf(BodyContentHandler.class);
        assertThat(DefaultHandlerType.line.create()).isInstanceOf(LinkContentHandler.class);
    }
}
