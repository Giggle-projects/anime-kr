package anime.alert;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class AlertManagerChain {

    private final List<AlertManager> alerts;

    @Async
    public void alert(String message) {
        alerts.forEach(it -> {
            try {
                it.alert(message);
            } catch (Exception ignored) {
            }
        });
    }
}
