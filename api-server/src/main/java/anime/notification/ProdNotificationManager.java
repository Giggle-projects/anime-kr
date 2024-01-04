package anime.notification;

import com.slack.api.Slack;
import com.slack.api.webhook.Payload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.IOException;

@ConditionalOnProperty(value = "mail.enable.mode", havingValue = "false", matchIfMissing = true)
@Service
public class ProdNotificationManager implements NotificationInterface {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdNotificationManager.class);

    @Override
    public void send(String message) {
        var payload = Payload.builder().text(message).build();
        try {
            var slackClient = Slack.getInstance();
            slackClient.send(SecretEnvs.getEnv("slack.webhook.url"), payload);
            LOGGER.error(message);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
