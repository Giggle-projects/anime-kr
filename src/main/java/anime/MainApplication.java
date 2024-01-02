package anime;

import anime.domain.AnimeDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MainApplication.class);
        final ConfigurableApplicationContext ctx = app.run(args);
        final AnimeDao bean = ctx.getBean(AnimeDao.class);
        bean.readDataFile();
    }
}
