package com.peterfranza.guice.quartz.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
public @interface CronInterval {
	String value() default "0 0 11 1/1 * ? *"; 	
}
