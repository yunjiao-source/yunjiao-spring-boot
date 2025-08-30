package yunjiao.springboot.autoconfigure.apijson;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import yunjiao.springboot.extension.apjson.orm.DataSourceContextHolder;

/**
 * 多数据源
 *
 * @author yangyunjiao
 */
public class MultipleDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSourceType();
    }
}
