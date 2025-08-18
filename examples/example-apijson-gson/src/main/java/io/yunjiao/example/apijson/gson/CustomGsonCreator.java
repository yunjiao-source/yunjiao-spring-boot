package io.yunjiao.example.apijson.gson;

import apijson.gson.APIJSONFunctionParser;
import io.yunjiao.springboot.autoconfigure.apijson.ApijsonSqlProperties;
import io.yunjiao.springboot.autoconfigure.apijson.gson.GsonCreator;

import javax.sql.DataSource;
import java.io.Serializable;

/**
 * 自定义
 *
 * @author yangyunjiao
 */
public class CustomGsonCreator extends GsonCreator {
    public CustomGsonCreator(DataSource dataSource, ApijsonSqlProperties sqlProperties) {
        super(dataSource, sqlProperties);
    }

    @Override
    public APIJSONFunctionParser<Serializable> createFunctionParser() {
        return new CustomGsonFunctionParser();
    }
}
