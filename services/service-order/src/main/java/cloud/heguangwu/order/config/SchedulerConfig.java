package cloud.heguangwu.order.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;


@Configuration
public class SchedulerConfig implements SchedulingConfigurer {
    @Value("${schedule.executor.number:10}")
    private int scheduleExecutorNumber;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(scheduleExecutorNumber);
        scheduler.setThreadNamePrefix("scheduled-task-"); // 线程名前缀
        scheduler.setAwaitTerminationSeconds(60);        // 优雅关闭等待时间
        scheduler.setWaitForTasksToCompleteOnShutdown(true); // 关闭时等待任务完成
        scheduler.initialize(); // 必须初始化
        taskRegistrar.setTaskScheduler(scheduler);
    }
}
