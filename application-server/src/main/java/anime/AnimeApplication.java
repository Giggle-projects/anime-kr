package anime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AnimeApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AnimeApplication.class);
        app.setAdditionalProfiles("dev");
        app.run(args);
    }
}
