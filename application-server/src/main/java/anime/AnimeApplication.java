package anime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import shutdown.EnableShutDown;

@EnableShutDown
@SpringBootApplication
public class AnimeApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AnimeApplication.class);
        app.run(args);
    }
}
