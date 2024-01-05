package anime.config;

import anime.alert.AlertManagerChain;
import anime.alert.AlertManager;
import com.slack.api.Slack;
import com.slack.api.webhook.Payload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AlertConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlertConfig.class);

    @Bean
    public AlertManagerChain alertChain(List<AlertManager> alertManagers) {
        return new AlertManagerChain(alertManagers);
    }

    @ConditionalOnProperty(value = "slack.webhook.url")
    @Bean
    public AlertManager slack(
        @Value("slack.webhook.url") String slackUrl
    ) {
        return message -> {
            try {
                var payload = Payload.builder().text(message).build();
                var slackClient = Slack.getInstance();
                slackClient.send(slackUrl, payload);
            } catch (Exception ignored) {
            }
        };
    }

    @Bean
    public AlertManager logging() {
        return LOGGER::error;
    }
}

