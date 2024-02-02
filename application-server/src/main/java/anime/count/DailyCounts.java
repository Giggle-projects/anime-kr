package anime.count;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static anime.count.DailyCount.DATA_ID;

@RequiredArgsConstructor
public class DailyCounts {

    private final DailyCountRepository dailyCountRepository;

    @Transactional
    public void record(int count) {
        var data = get();
        data.add(count);
        dailyCountRepository.save(data);
    }

    @Transactional(readOnly = true)
    public DailyCount get() {
        return dailyCountRepository.findById(DATA_ID).orElseThrow();
    }
}
