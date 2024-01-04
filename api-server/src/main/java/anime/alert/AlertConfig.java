package anime.alert;

import anime.config.SecretEnvs;
import com.slack.api.Slack;
import com.slack.api.webhook.Payload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AlertConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlertConfig.class);

    @Bean
    public AlertChain alertChain(List<AlertManager> alertManagers) {
        var alertChain = new AlertChain();
        for (var alertManager : alertManagers) {
            alertChain = alertChain.add(alertManager);
        }
        return alertChain;
    }

    @ConditionalOnProperty(value = "slack.webhook.url")
    @Bean
    public AlertManager slack() {
        return message -> {
            try {
                var payload = Payload.builder().text(message).build();
                var slackClient = Slack.getInstance();
                slackClient.send(SecretEnvs.getEnv("slack.webhook.url"), payload);
            } catch (Exception ignored) {
            }
        };
    }

    @Bean
    public AlertManager logging() {
        return LOGGER::error;
    }
}

