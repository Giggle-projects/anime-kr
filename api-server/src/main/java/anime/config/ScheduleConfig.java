package anime.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/*
lockAtMostFor : specifies how long the lock should be kept in case the executing node dies, set value to be longer than execution time.
lockAtLeastFor : specifies minimum amount of time for which the lock should be kept
 */

@Configuration
@EnableScheduling
//@EnableSchedulerLock(defaultLockAtMostFor = "30s")
public class ScheduleConfig {
}
