package anime.accessCount;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class AccessCountScheduler {

    public static final int SCHEDULE_TERM_SEC = 10;

    private final DailyCounts dailyCounts;

    @Scheduled(fixedRate = 1000 * SCHEDULE_TERM_SEC, initialDelay = 0)
    public void record() {
        var accessCount = AccessCount.flush();
        dailyCounts.record(accessCount);
        log.info("add count : " + accessCount);
    }
}
