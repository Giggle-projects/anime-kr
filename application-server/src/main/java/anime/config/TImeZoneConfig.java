package anime.config;

import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
public class TImeZoneConfig {

    public TImeZoneConfig() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
}
