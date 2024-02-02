package anime.count;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static anime.count.DailyCount.DATA_ID;

@RequiredArgsConstructor
public class DailyCounts {

    private final DailyCountRepository dailyCountRepository;

    @PostConstruct
    public void init() {
        if(!dailyCountRepository.existsById(DATA_ID)) {
            dailyCountRepository.save(DailyCount.SINGLE_DATA);
        }
    }

    @Transactional
    public void record(int count) {
        var data = get();
        data.add(count);
        dailyCountRepository.save(data);
    }

    @Transactional
    public DailyCount get() {
        return dailyCountRepository.findById(DATA_ID).orElseThrow();
    }
}
