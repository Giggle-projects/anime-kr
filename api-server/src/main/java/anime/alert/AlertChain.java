package anime.alert;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
public class AlertChain {

    private final List<AlertManager> alerts;

    public AlertChain() {
        this.alerts = new ArrayList<>();
    }

    public AlertChain(List<AlertManager> alerts) {
        this.alerts = alerts;
    }

    public AlertChain add(AlertManager alertManager) {
        alerts.add(alertManager);
        return new AlertChain(new LinkedList<>(alerts));
    }

    @Async
    public void alert(String message) {
        for (var alert : alerts) {
            try {
                alert.alert(message);
            } catch (Exception ignored) {

            }
        }
    }
}
