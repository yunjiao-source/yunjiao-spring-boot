package yunjiao.springboot.extension.apijson.fastjson2;

import apijson.fastjson2.*;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.io.Serializable;

/**
 * Fastjson2创建器
 *
 * @author yangyunjiao
 */
@RequiredArgsConstructor
public class Fastjson2Creator extends APIJSONCreator<Serializable> {
    /**
     * 数据源
     */
    private final DataSource dataSource;

    /**
     * 数据库版本
     */
    private final String dbVersion;

    @Override
    public APIJSONParser<Serializable> createParser() {
        return new Fastjson2Parser();
    }

    @Override
    public APIJSONFunctionParser<Serializable> createFunctionParser() {
        return new Fastjson2FunctionParser();
    }

    @Override
    public APIJSONVerifier<Serializable> createVerifier() {
        return new Fastjson2Verifier();
    }

    @Override
    public APIJSONSQLConfig<Serializable> createSQLConfig() {
        return new Fastjson2SqlConfig(dbVersion);
    }

    @Override
    public APIJSONSQLExecutor<Serializable> createSQLExecutor() {
        return new Fastjson2SqlExecutor(dataSource);
    }
}
