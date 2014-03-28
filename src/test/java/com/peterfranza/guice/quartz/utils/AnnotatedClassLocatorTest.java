package com.peterfranza.guice.quartz.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.peterfranza.guice.quartz.annotations.CronInterval;
import com.peterfranza.guice.quartz.annotations.ScheduleInterval;

public class AnnotatedClassLocatorTest {

	@Test
	public void test() {
		AnnotatedClassLocator a = Guice.createInjector(new AbstractModule() {
			
			@Override
			protected void configure() {
				bind(TestJob.class);
				bind(TestJob2.class);
			}
		}).getInstance(AnnotatedClassLocator.class);
		
		assertNotNull(a);
		assertEquals(1, a.find(ScheduleInterval.class, Job.class).size());
	}
	
	@ScheduleInterval
	public static class TestJob implements Job {

		public void execute(JobExecutionContext context)
				throws JobExecutionException {}
		
	}
	
	@CronInterval
	public static class TestJob2 implements Job {

		public void execute(JobExecutionContext context)
				throws JobExecutionException {}
		
	}

}
