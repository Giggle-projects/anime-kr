package anime.utils;

import com.slack.api.Slack;
import com.slack.api.webhook.Payload;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Slf4j
public class SlackUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger("slack");

    public static void send(String message) {
        var payload = Payload.builder().text(message).build();
        try {
            var slackClient = Slack.getInstance();
            slackClient.send(SecretEnvs.getEnv("slack.webhook.url"), payload);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
