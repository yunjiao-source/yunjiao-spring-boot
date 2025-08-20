package io.yunjiao.springboot.autoconfigure.apijson;

import apijson.StringUtil;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * {@link ApijsonUtils} 单元测试用例
 *
 * @author yangyunjiao
 */
public class ApijsonUtilsTest {
    public BiConsumer<Object, Integer> stringAndNotEmpty = (item, index) -> {
        boolean isTrue =item instanceof String && StringUtil.isNotEmpty((String) item);
        if (!isTrue) {
            throw new IllegalArgumentException(index + ": " + item + " 不符合字符串格式或空串!");
        }
    };

    @Test
    public void givenNotList_whenCheckItemInList_thenException() {
        assertThatThrownBy(() -> ApijsonUtils.checkItemInList(new Object(), stringAndNotEmpty))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(" 不符合 Array 数组类型! 结构必须是 [] ！");

    }

    @Test
    public void givenEmpty_whenCheckItemInList_thenException() {
        List<String> list = List.of("123","345", "");

        assertThatThrownBy(() -> ApijsonUtils.checkItemInList(list, stringAndNotEmpty))
                .isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    public void givenNotEmptyList_whenCheckItemInList_thenSucess() {
        List<String> list = List.of("123","345");

        assertThatCode(() -> ApijsonUtils.checkItemInList(list, stringAndNotEmpty)).doesNotThrowAnyException();
    }

}
