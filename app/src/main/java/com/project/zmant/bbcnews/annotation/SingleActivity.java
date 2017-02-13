package com.project.zmant.bbcnews.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * @author zmant 2016/12/11 17:43
 * @classname SingleActivity
 * @description Module需要的注解
 */
@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface SingleActivity {
}
