package com.orange.easyexcel.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解，判断是否需要合并以及合并的主键
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CustomMerge {

    /**
     * 是否需要合并单元格
     * @return
     */
    boolean needMerge() default false;

    /**
     * 是否是主键，即该字段相同的行合并
     * @return
     */
    boolean isPK() default  false;
}
