package anime.count;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Getter
public class DailyCount {

    public static final Long DATA_ID = 1L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private int todayCount;
    private int totalCount;
    private int yesterdayCount;
    private LocalDateTime lastRecorded = LocalDateTime.now();

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
