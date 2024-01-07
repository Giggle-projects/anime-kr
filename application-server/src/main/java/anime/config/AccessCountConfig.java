package anime.config;

import anime.count.AccessCountFilter;
import anime.count.AccessCountScheduler;
import anime.count.DailyCountRepository;
import anime.count.DailyCounts;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

@ConditionalOnProperty("spring.data.mongodb.uri")
@Configuration
public class AccessCountConfig {

    private final DailyCountRepository dailyCountRepository;

    public AccessCountConfig(DailyCountRepository dailyCountRepository) {
        this.dailyCountRepository = dailyCountRepository;
    }

    @Bean
    public OncePerRequestFilter accessCountFilter() {
        return new AccessCountFilter();
    }

    @Bean
    public AccessCountScheduler recordAccessCounterScheduler() {
        return new AccessCountScheduler(dailyCounts());
    }

    @Bean
    public DailyCounts dailyCounts() {
        return new DailyCounts(dailyCountRepository);
    }
}
