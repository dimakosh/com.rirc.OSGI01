package com.rirc.OSGI01;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface KDCompType {
	String project() default "";
	String type() default "";
	String[] name() default "";
	String[] roles() default "";
}
