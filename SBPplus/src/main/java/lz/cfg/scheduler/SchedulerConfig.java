package lz.cfg.scheduler;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;

@Configuration
public class SchedulerConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        //开启任务线程池100个
        scheduledTaskRegistrar.setScheduler(Executors.newScheduledThreadPool(100));
    }
}
