package yunjiao.springboot.extension.apijson.gson;

import apijson.gson.*;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.io.Serializable;

/**
 * framework创建器
 *
 * @author yangyunjiao
 */
@RequiredArgsConstructor
public class GsonCreator extends APIJSONCreator<Serializable> {
    private final DataSource dataSource;

    /**
     * 数据库版本
     */
    private final String dbVersion;

    @Override
    public APIJSONParser<Serializable> createParser() {
        return new GsonParser();
    }

    @Override
    public APIJSONFunctionParser<Serializable> createFunctionParser() {
        return new GsonFunctionParser();
    }

    @Override
    public APIJSONVerifier<Serializable> createVerifier() {
        return new GsonVerifier();
    }

    @Override
    public APIJSONSQLConfig<Serializable> createSQLConfig() {
        return new GsonSqlConfig(dbVersion);
    }

    @Override
    public APIJSONSQLExecutor<Serializable> createSQLExecutor() {
        return new GsonSqlExecutor(dataSource);
    }
}
