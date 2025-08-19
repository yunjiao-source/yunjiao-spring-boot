package io.yunjiao.project.rql.basic.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * implementation holds our Query parameters
 *
 * @author yangyunjiao
 */
@Data
@AllArgsConstructor
public class SearchCriteria {
    /**
     * used to hold field name – for example: firstName, age, … etc.
     */
    private String key;

    /**
     * used to hold the operation – for example: Equality, less than, … etc.
     */
    private String operation;

    /**
     * used to hold the field value – for example: john, 25, … etc.
     */
    private Object value;
}


