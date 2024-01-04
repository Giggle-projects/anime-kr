package anime.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@ConditionalOnProperty(value = "mail.enable.mode", havingValue = "true")

@Service
public class DevNotificationManager implements NotificationInterface {

    private static final Logger LOGGER = LoggerFactory.getLogger(DevNotificationManager.class);

    @Override
    public void send(String message) {
        System.out.println("dev");
        LOGGER.error(message);
    }
}
