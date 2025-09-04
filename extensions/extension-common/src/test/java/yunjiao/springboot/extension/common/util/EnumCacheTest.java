package yunjiao.springboot.extension.common.util;

import lombok.Getter;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link EnumCache} 单元测试用例
 *
 * @author yangyunjiao
 */
public class EnumCacheTest {

    @Test
    void givenName_whenFindByName_thenReturnOk() {
        StatusEnum status = EnumCache.getInstance().lookupByName(StatusEnum.class, "SUCCESS");
        assertThat(status).isEqualTo(StatusEnum.SUCCESS);
    }

    @Test
    void givenWrongName_whenFindByName_thenReturnNull() {
        StatusEnum status = EnumCache.getInstance().lookupByName(StatusEnum.class, "SUCCESS1");
        assertThat(status).isNull();
    }

    @Test
    void givenWrongValue_whenFindByName_thenReturnNull() {
        StatusEnum status = EnumCache.getInstance().lookupByValue(StatusEnum.class, "s", StatusEnum::getCode);
        assertThat(status).isNull();
    }

    @Test
    void givenCode_whenFindByValue_thenReturnOk() {
        StatusEnum status = EnumCache.getInstance().lookupByValue(StatusEnum.class, "S", StatusEnum::getCode);
        assertThat(status).isEqualTo(StatusEnum.SUCCESS);
    }

    @Test
    void givenDesc_whenFindByValue_thenReturnOk() {
        StatusEnum status = EnumCache.getInstance().lookupByValue(StatusEnum.class, "失败", StatusEnum::getDesc);
        assertThat(status).isEqualTo(StatusEnum.FAIL);
    }




    @Getter
    enum StatusEnum {
        INIT("I", "初始化"),
        PROCESSING("P", "处理中"),
        SUCCESS("S", "成功"),
        FAIL("F", "失败");

        private final String code;
        private final String desc;

        StatusEnum(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }
}
