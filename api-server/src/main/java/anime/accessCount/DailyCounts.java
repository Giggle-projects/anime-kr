package anime.accessCount;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class DailyCounts {

    public static final String DATA_ID = "anime-access-count";

    private final DailyCountRepository dailyCountRepository;

    @Transactional
    public void record(int count) {
        var redisData = get();
        redisData.add(count);
    }

    @Transactional(readOnly = true)
    public DailyCount get() {
        return dailyCountRepository.findById(DATA_ID)
            .orElse(new DailyCount(DATA_ID));
    }
}

interface DailyCountRepository extends CrudRepository<DailyCount, String> {
}

