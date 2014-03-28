package com.peterfranza.guice.quartz.utils;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import com.google.inject.Injector;
import com.google.inject.Key;

public class AnnotatedClassLocator {

	@Inject Injector injector;

	@SuppressWarnings("unchecked")
	public <H> Collection<Class<H>> find(Class<? extends Annotation> annotation, Class<H> type) {
				
		Set<Class<H>> s = new HashSet<Class<H>>();
        for (Key<?> key : injector.getBindings().keySet()) { 	
        	if (type.isAssignableFrom(key.getTypeLiteral().getRawType())) {
        		if(key.getTypeLiteral().getRawType().isAnnotationPresent(annotation)) {
        			s.add((Class<H>) key.getTypeLiteral().getRawType());
        		}

        	}
        }
        return s;
	}
	
}
