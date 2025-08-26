package yunjiao.springboot.autoconfigure.apijson;

import apijson.RequestMethod;
import apijson.framework.APIJSONVerifier;
import apijson.orm.AbstractVerifier;
import apijson.orm.Entry;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link ApijsonVerifierConfigurer} 单元测试用例
 *
 * @author yangyunjiao
 */
public class ApijsonVerifierConfigurerTest {

    @Test
    void givenImplement_whenBuild_thenCheckStaticOk() {
        ApijsonVerifierConfigurerDemo configurer = new ApijsonVerifierConfigurerDemo();
        ApijsonVerifierProperties properties = new ApijsonVerifierProperties();
        properties.setEnableVerifyColumn(false);
        properties.setEnableApijsonRouter(true);
        properties.setUpdateMustHaveIdCondition(false);
        properties.setEnableVerifyRole(false);
        properties.setEnableVerifyContent(false);

        ApijsonUtils.forceInit(AbstractVerifier.class, APIJSONVerifier.class);
        ApijsonUtils.buildAPIJSONVerifierStatic(properties, List.of(configurer));

        assertThat(APIJSONVerifier.ENABLE_APIJSON_ROUTER).isEqualTo(properties.isEnableApijsonRouter());
        assertThat(APIJSONVerifier.ENABLE_VERIFY_COLUMN).isEqualTo(properties.isEnableVerifyColumn());
        assertThat(AbstractVerifier.IS_UPDATE_MUST_HAVE_ID_CONDITION).isEqualTo(properties.isUpdateMustHaveIdCondition());
        assertThat(AbstractVerifier.ENABLE_VERIFY_ROLE).isEqualTo(properties.isEnableVerifyRole());
        assertThat(AbstractVerifier.ENABLE_VERIFY_CONTENT).isEqualTo(properties.isEnableVerifyContent());

        assertThat(AbstractVerifier.ROLE_MAP).containsKey("roleMap");
        assertThat(AbstractVerifier.OPERATION_KEY_LIST).contains("operationKeyList");
        assertThat(AbstractVerifier.SYSTEM_ACCESS_MAP).containsKey("systemAccessMap");
        assertThat(AbstractVerifier.ACCESS_MAP).containsKey("accessNap");

        assertThat(AbstractVerifier.COMPILE_MAP).hasSize(1);
        assertThat(AbstractVerifier.COMPILE_MAP).containsKey("compileMap");
        assertThat(AbstractVerifier.REQUEST_MAP).hasSize(1);
        assertThat(AbstractVerifier.REQUEST_MAP).containsKey("requestMap");
    }

    static class ApijsonVerifierConfigurerDemo implements ApijsonVerifierConfigurer {

        @Override
        public void configure(Map<String, Entry<String, Object>> roleMap,
                              List<String> operationKeyList,
                              Map<String, Map<RequestMethod, String[]>> systemAccessMap,
                              Map<String, Map<RequestMethod, String[]>> accessNap,
                              Map<String, Pattern> compileMap,
                              Map<String, SortedMap<Integer, Map<String, Object>>> requestMap) {
            roleMap.put("roleMap", new Entry<>("roleMap1", "roleMap2"));
            operationKeyList.add("operationKeyList");
            systemAccessMap.put("systemAccessMap", null);
            accessNap.put("accessNap", null);
            compileMap.put("compileMap", null);
            requestMap.put("requestMap", null);
        }
    }
}
