package io.yunjiao.extension.apjson.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * apijson restful api
 *
 * @author yangyunjiao
 */
@Target({TYPE, METHOD})
@Retention(RUNTIME)
public @interface ApijsonRest {
}
