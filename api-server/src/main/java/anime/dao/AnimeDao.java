package anime.dao;

import anime.dto.Anime;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AnimeDao {

    private final File file;

    public AnimeDao(@Value("${data.file.path}") String dataFilePath) {
        file = new File(dataFilePath);
    }

    public List<Anime> readDataFile() {
        try {
            return Files.readLines(file, Charsets.UTF_8).stream()
                .skip(2)
                .map(it -> AnimeMapper.toAnime(it.split("\\|")))
                .collect(Collectors.toList());
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to load data file", e);
        }
    }
}

