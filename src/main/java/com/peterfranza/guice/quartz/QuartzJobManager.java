package com.peterfranza.guice.quartz;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import com.peterfranza.guice.quartz.annotations.CronInterval;
import com.peterfranza.guice.quartz.annotations.ScheduleInterval;
import com.peterfranza.guice.quartz.utils.AnnotatedClassLocator;

@Singleton
public class QuartzJobManager {

	Logger LOG = Logger.getLogger(QuartzJobManager.class.getName());
	
	@Inject
	public QuartzJobManager(SchedulerFactory schedulerFactory, GuiceJobFactory jobFactory, AnnotatedClassLocator classLocator) throws Exception {
	
		Scheduler scheduler = schedulerFactory.getScheduler();
		scheduler.setJobFactory(jobFactory);
		
		for(Class<? extends Job> j: classLocator.find(ScheduleInterval.class, Job.class)) {
			JobDetail jobDetail = JobBuilder.newJob(j).withIdentity(j.getSimpleName()+"Job").build();

			ScheduleInterval interval = j.getAnnotation(ScheduleInterval.class);
			Trigger trigger = TriggerBuilder
					.newTrigger().withIdentity(j.getSimpleName()+"TriggerId")
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(interval.value())
							.repeatForever()).build();			

			LOG.info("Schedule " + j.getSimpleName() + " @ " + interval.value() + "sec");
			scheduler.scheduleJob(jobDetail, trigger);

		}
		
		for(Class<Job> j: classLocator.find(CronInterval.class, Job.class)) {
			JobDetail jobDetail = JobBuilder.newJob(j).withIdentity(j.getSimpleName()+"Job").build();

			CronInterval cron = j.getAnnotation(CronInterval.class);
			Trigger trigger = TriggerBuilder
					.newTrigger().withIdentity(j.getSimpleName()+"TriggerId")
					.withSchedule(CronScheduleBuilder.cronSchedule(cron.value())).build();
			LOG.info("Schedule " + j.getSimpleName() + " @ " + cron.value());
			scheduler.scheduleJob(jobDetail, trigger);

		}
		
		scheduler.start();
		LOG.info("Schedular Started");
	}
	
}
