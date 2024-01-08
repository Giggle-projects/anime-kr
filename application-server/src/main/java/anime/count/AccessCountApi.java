package anime.count;

import anime.config.AccessCountConfig;
import anime.shutdown.ShutDown;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@ConditionalOnBean(AccessCountConfig.class)
@ShutDown
@RequiredArgsConstructor
@RestController
public class AccessCountApi {

    private final DailyCounts dailyCounts;

    @GetMapping("/api/counts")
    public ResponseEntity<DailyCount> count() {
        var result = dailyCounts.get();
        return ResponseEntity.ok(result);
    }
}
