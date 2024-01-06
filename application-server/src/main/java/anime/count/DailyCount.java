package anime.count;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "dailyCount")
@NoArgsConstructor
@AllArgsConstructor
@Getter
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
