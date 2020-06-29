package com.alfalahsoftech.alframe.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.METHOD })
public @interface ReqMthdDgntr {

	public String id() default "";

	public String url() default "";

	public AFActionType actionID() default AFActionType.OTHER;
}
