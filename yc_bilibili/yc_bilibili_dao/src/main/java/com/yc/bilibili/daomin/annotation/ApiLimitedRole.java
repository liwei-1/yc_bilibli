package com.yc.bilibili.daomin.annotation;


import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 自定义注解
 * @author 12490
 * Documented 这个元注解表示当使用 javadoc 等工具为代码生成文档时，自定义注解也应该被包含进去
 * Target({ElementType.METHOD})  表示 自定义注解可以应用于表示这个注解只能应用于方法上
 * Retention(RetentionPolicy.RUNTIME) 表示这个注解在运行时仍然可见，因此可以通过反射机制读取
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@Component
public @interface ApiLimitedRole {
    String [] limitedRoleCodeList() default {};
}
