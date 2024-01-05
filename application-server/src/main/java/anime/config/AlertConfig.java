package anime.config;

import anime.alert.AlertManager;
import anime.alert.AlertManagerChain;
import com.slack.api.Slack;
import com.slack.api.webhook.Payload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.List;

@Slf4j
@Configuration
public class AlertConfig {

    @Bean
    public AlertManagerChain alertChain(List<AlertManager> alertManagers) {
        return new AlertManagerChain(alertManagers);
    }

    @ConditionalOnProperty(value = "slack.webhook.url")
    @Bean
    public AlertManager slack(@Value("slack.webhook.url") String slackUrl) {
        return message -> {
            try {
                var payload = Payload.builder().text(message).build();
                var slackClient = Slack.getInstance();
                slackClient.send(slackUrl, payload);
            } catch (IOException ignored) {
            }
        };
    }

    @Bean
    public AlertManager logging() {
        return log::error;
    }
}

