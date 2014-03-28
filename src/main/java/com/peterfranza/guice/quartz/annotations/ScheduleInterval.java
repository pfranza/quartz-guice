package com.peterfranza.guice.quartz.annotations;

import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
public @interface ScheduleInterval {

	int value() default 60; 
	
}
