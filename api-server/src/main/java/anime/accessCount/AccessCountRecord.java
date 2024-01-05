package anime.accessCount;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

import static anime.accessCount.AccessCountAggregateSchedule.RECORD_CACHE_ID;

@AllArgsConstructor
@Getter
@RedisHash(value = "accessCount", timeToLive = -1)
public class AccessCountRecord {

    @Id
    String id;
    int unrecorded;
    int todayCount;
    int totalCount;
    int yesterdayCount;
    LocalDateTime lastRecorded;

    public static AccessCountRecord initialize() {
        return new AccessCountRecord(RECORD_CACHE_ID, LocalDateTime.now());
    }

    public AccessCountRecord(String id, LocalDateTime lastRecorded) {
        this.id = id;
        this.lastRecorded = lastRecorded;
    }

    public void record() {
        todayCount += unrecorded;
        totalCount += unrecorded;
        unrecorded = 0;
        if (lastRecorded.toLocalDate().isBefore(LocalDateTime.now().toLocalDate())) {
            yesterdayCount = todayCount;
            todayCount = 0;
        }
        lastRecorded = LocalDateTime.now();
    }
}
