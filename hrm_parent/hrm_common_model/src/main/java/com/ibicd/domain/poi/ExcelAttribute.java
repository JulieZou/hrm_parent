package com.ibicd.domain.poi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName ExcelAttribute
 * @Description Excel 属性注解
 * @Author Julie
 * @Date 2019/10/13 16:02
 * @Version 1.0
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelAttribute {

    /**
     * 对应列名称
     */
    String name() default "";

    /**
     * 列序号
     */
    int sort();

    /**
     * 字段类型对应的格式
     */
    String format() default "";
}
