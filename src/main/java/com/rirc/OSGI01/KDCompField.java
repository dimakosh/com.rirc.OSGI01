package com.rirc.OSGI01;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface KDCompField {
	String name() default "";
	String clientType() default "";
	int width() default 0;
	int height() default 0;
	int decimal() default 0;
	int maxlength() default 0;
	String from() default "";
	String fKod() default "";
	String fName() default "";
	boolean isRequired() default false;
	KDCompEnblDsbl enblDsbl() default KDCompEnblDsbl.no;
	String initValue() default "";
}
