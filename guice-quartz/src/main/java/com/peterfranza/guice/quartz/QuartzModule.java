package com.peterfranza.guice.quartz;

import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.google.inject.AbstractModule;

public class QuartzModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(SchedulerFactory.class).to(StdSchedulerFactory.class);
		bind(QuartzJobManager.class).asEagerSingleton();
	}

}
