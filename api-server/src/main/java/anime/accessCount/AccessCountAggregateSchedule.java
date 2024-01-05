package anime.accessCount;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class AccessCountAggregateSchedule {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessCountAggregateSchedule.class);

    public static final String RECORD_CACHE_ID = "anime-access-count";

    private final AccessCountRepository accessCountRepository;

    public AccessCountAggregateSchedule(AccessCountRepository accessCountRepository) {
        this.accessCountRepository = accessCountRepository;
    }

    @Scheduled(fixedRate = 60 * 60, initialDelay = 0)
    public void sum() {
        LOGGER.info("aggregate access count");
        var accessCountRecord = accessCountRepository.findById(RECORD_CACHE_ID)
            .orElse(AccessCountRecord.initialize());
        accessCountRecord.record();
        accessCountRepository.save(accessCountRecord);
    }
}
