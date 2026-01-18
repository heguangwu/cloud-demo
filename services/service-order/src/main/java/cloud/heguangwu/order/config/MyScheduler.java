package cloud.heguangwu.order.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyScheduler {
    @Value("${hello.cron: >>>>>>>Say HELLO<<<<<<}")
    private String helloCron;

    @Scheduled(initialDelay = 10000, fixedRate = 5000)
    public void scheduleFixedRate() {
        log.info(" >>>> schedule FixedRate 首次调用 10s，调用频率:5s");
    }

    @Scheduled(cron = "0 */1 * * * *")
    public void scheduleHelloCron() {
        log.info(" >>>> schedule Cron调用{}，每1分钟执行一次", helloCron);
    }
}
