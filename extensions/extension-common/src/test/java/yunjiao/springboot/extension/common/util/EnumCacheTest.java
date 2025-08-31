package yunjiao.springboot.extension.common.util;

import org.junit.jupiter.api.Test;
import yunjiao.springboot.extension.common.util.EnumCache;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link EnumCache} 单元测试用例
 *
 * @author yangyunjiao
 */
public class EnumCacheTest {

    @Test
    void givenName_whenFindByName_thenReturnOk() {
        StatusEnum status = EnumCache.findByName(StatusEnum.class, "SUCCESS");
        assertThat(status).isEqualTo(StatusEnum.SUCCESS);
    }

    @Test
    void givenWrongName_whenFindByName_thenReturnNull() {
        StatusEnum status = EnumCache.findByName(StatusEnum.class, "SUCCESS1");
        assertThat(status).isNull();
    }

    @Test
    void givenValue_whenFindByValue_thenReturnOk() {
        StatusEnum status = EnumCache.findByValue(StatusEnum.class, "S");
        assertThat(status).isEqualTo(StatusEnum.SUCCESS);
    }

    @Test
    void givenWrongValue_whenFindByName_thenReturnNull() {
        StatusEnum status = EnumCache.findByName(StatusEnum.class, "s");
        assertThat(status).isNull();
    }


    enum StatusEnum {
        INIT("I", "初始化"),
        PROCESSING("P", "处理中"),
        SUCCESS("S", "成功"),
        FAIL("F", "失败");

        private String code;
        private String desc;

        StatusEnum(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public String getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

        static {
            // 通过名称构建缓存,通过EnumCache.findByName(StatusEnum.class,"SUCCESS",null);调用能获取枚举
            EnumCache.registerByName(StatusEnum.class, StatusEnum.values());
            // 通过code构建缓存,通过EnumCache.findByValue(StatusEnum.class,"S",null);调用能获取枚举
            EnumCache.registerByValue(StatusEnum.class, StatusEnum.values(), StatusEnum::getCode);
        }
    }
}
