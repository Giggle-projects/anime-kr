package anime.accessCount;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@RedisHash(value = "dailyCount", timeToLive = -1)
public class DailyCount {

    @Id
    String id;
    int todayCount;
    int totalCount;
    int yesterdayCount;
    LocalDateTime lastRecorded = LocalDateTime.now();

    public DailyCount(String id) {
        this.id = id;
    }

    public void add(int accessCount) {
        todayCount += accessCount;
        totalCount += accessCount;

        if (lastRecorded.toLocalDate().isBefore(LocalDateTime.now().toLocalDate())) {
            yesterdayCount = todayCount;
            todayCount = 0;
        }
        lastRecorded = LocalDateTime.now();
    }
}
