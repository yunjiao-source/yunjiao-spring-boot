package io.yunjiao.example.apijson.config;

import apijson.StringUtil;
import io.yunjiao.spring.boot.autoconfigure.apijson.ApijsonSqlConfigConfigurer;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * sql配置
 *
 * @author yangyunjiao
 */
@Configuration
public class CustomSqlConfigConfigurer implements ApijsonSqlConfigConfigurer {
    @Override
    public void configure(Map<String, String> rawMap, Map<String, List<String>> versionedTableColumnMap, Map<String, Map<String, String>> versionedKeyColumnMap) {
        rawMap.put("`to`.`id`", "");  // 空字符串 "" 表示用 key 的值 `to`.`id`
        rawMap.put("toDate", "");  // "@column": "date;date_format('2020-01-01','%Y-%m-%d'):toDate", "@having": "(date > toDate)", "@raw": "@column,@having"
        rawMap.put("to.momentId", "`to`.`momentId`");  // 最终以 `to`.`userId` 拼接 SQL，相比以上写法可以让前端写起来更简单
        rawMap.put("(`Comment`.`userId`=`to`.`userId`)", "");  // 已经是一个条件表达式了，用 () 包裹是为了避免 JSON 中的 key 拼接在前面导致 SQL 出错
        rawMap.put("sum(if(userId%2=0,1,0))", "");  // 超过单个函数的 SQL 表达式
        rawMap.put("sumUserIdIsEven", "sum(if(`userId`%2=0,1,0)) AS sumUserIdIsEven");  // 简化前端传参
        rawMap.put("substring_index(substring_index(content,',',1),',',-1)", "");  // APIAuto 不支持 '，可以用 Postman 测
        rawMap.put("substring_index(substring_index(content,'.',1),'.',-1) AS subContent", "");  // APIAuto 不支持 '，可以用 Postman 测
        rawMap.put("commentWhereItem1","(`Comment`.`userId` = 38710 AND `Comment`.`momentId` = 470)");
        rawMap.put("to_days(now())-to_days(`date`)<=7", "");  // 给 @having 使用
        rawMap.put("sexShowStr", "CASE sex WHEN 0 THEN '男' WHEN 1 THEN '女' ELSE '其它' END");  // 给 @having 使用

        rawMap.put("length(url)", "");  // APIAuto 获取分组
        rawMap.put("length(substring_index(url,'/',-1))", "");  // APIAuto 获取分组
        rawMap.put("length(url)-length(substring_index(url,'/',-1))-1", "");  // APIAuto 获取分组
        rawMap.put("length(url) - length(substring_index(url,'/',-1)) - 1", "");  // APIAuto 获取分组
        rawMap.put("substr(url,1,length(url)-length(substring_index(url,'/',-1))-1)", "");  // APIAuto 获取分组
        rawMap.put("substr(url,1,length(url) - length(substring_index(url,'/',-1)) - 1)", "");  // APIAuto 获取分组
        rawMap.put("substr(url,1,length(url)-length(substring_index(url,'/',-1))-1) url", "");  // APIAuto 获取分组
        rawMap.put("substr(url,1,length(url) - length(substring_index(url,'/',-1)) - 1) url", "");  // APIAuto 获取分组
        rawMap.put("length(substr(url,1,length(url)-length(substring_index(url,'/',-1))-1))", "");  // APIAuto 获取分组
        rawMap.put("substr(url,1,length(url)-length(substring_index(url,'/',-1))-1):groupUrl", "substr(url,1,length(url)-length(substring_index(url,'/',-1))-1) `groupUrl`");  // APIAuto 获取分组
        rawMap.put("ifnull(`group`,'-')", "");  // APIAuto 获取分组
        rawMap.put("any_value(ifnull(`group`,'-'))", "");  // APIAuto 获取分组
        rawMap.put("length(`group`)", "");  // APIAuto 获取分组
        rawMap.put("length(`group`) > 0", "");  // APIAuto 获取分组
        rawMap.put("CASE WHEN length(`group`) > 0 THEN `group` ELSE '-' END", "");  // APIAuto 获取分组
        rawMap.put("(CASE WHEN length(`group`) > 0 THEN `group` ELSE '-' END)", "");  // APIAuto 获取分组
        rawMap.put("(CASE WHEN length(`group`) > 0 THEN `group` ELSE '-' END) `name`", "");  // APIAuto 获取分组
        rawMap.put("(CASE WHEN length(`group`) > 0 THEN `group` ELSE '-' END):groupName`", "(CASE WHEN length(`group`) > 0 THEN `group` ELSE '-' END) `groupName`");  // APIAuto 获取分组
        rawMap.put("LIKE", "");  // UnitAuto 获取分组
        rawMap.put("substr(package,2)", "");  // UnitAuto 获取分组
        rawMap.put("CASE WHEN package LIKE '*%' THEN substr(package,2) ELSE package END", "");  // UnitAuto 获取分组
        rawMap.put("(CASE WHEN package LIKE '*%' THEN substr(package,2) ELSE package END) `url`", "");  // UnitAuto 获取分组
        rawMap.put("(CASE WHEN package LIKE '*%' THEN substr(package,2) ELSE package END) `groupUrl`", "");  // UnitAuto 获取分组
        rawMap.put("(CASE WHEN package LIKE '*%' THEN substr(package,2) ELSE package END):groupUrl", "(CASE WHEN package LIKE '*%' THEN substr(package,2) ELSE package END) `groupUrl`");  // UnitAuto 获取分组


        versionedTableColumnMap.put("User", Arrays.asList(StringUtil.split("id,sex,name,tag,head,contactIdList,pictureList,date")));
        // 需要对应方法传参也是这样拼接才行，例如 ColumnUtil.compatInputColumn(column, getSQLDatabase() + "-" + getSQLSchema() + "-" + getTable(), getMethod());
        versionedTableColumnMap.put("MYSQL-sys-Privacy", Arrays.asList(StringUtil.split("id,certified,phone,balance,_password,_payPassword")));

        Map<String, String> userKeyColumnMap = new HashMap<>();
        userKeyColumnMap.put("gender", "sex");
        userKeyColumnMap.put("createTime", "date");
        versionedKeyColumnMap.put("User", userKeyColumnMap);

        Map<String, String> privacyKeyColumnMap = new HashMap<>();
        privacyKeyColumnMap.put("rest", "balance");
        // 需要对应方法传参也是这样拼接才行，例如 ColumnUtil.compatInputKey(super.getKey(key), getSQLDatabase() + "-" + getSQLSchema() + "-" + getTable(), getMethod());
        versionedKeyColumnMap.put("MYSQL-sys-Privacy", privacyKeyColumnMap);
    }
}
