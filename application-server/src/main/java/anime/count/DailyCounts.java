package anime.count;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class DailyCounts {

    public static final String DATA_ID = "anime-counts";

    private final DailyCountRepository dailyCountRepository;

    @Transactional
    public void record(int count) {
        var data = get();
        data.add(count);
        dailyCountRepository.save(data);
    }

    @Transactional(readOnly = true)
    public DailyCount get() {
        return dailyCountRepository.findById(DATA_ID)
            .orElse(new DailyCount(DATA_ID));
    }
}
