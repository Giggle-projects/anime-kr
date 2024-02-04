package anime.config;

import anime.anime.AnimeDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class DataSourceConfig {

    @Bean
    public AnimeDao animeDao(@Value("${data.file.path}") String filePath) {
        var file = new File(filePath);
        return new AnimeDao(file);
    }
}
