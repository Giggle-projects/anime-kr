package anime.domain;

import anime.exception.DataFileException;
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
            throw new DataFileException("Failed to load data file", e);
        }
    }
}

class AnimeMapper {

    private static final int INDEX = 1;
    private static final int INDEX_OF_TITlE = 2;
    private static final int INDEX_OF_FAMOUS_LINE = 3;
    private static final int INDEX_OF_IMAGE_FILE_PATH = 4;

    public static Anime toAnime(String[] line) {
        return new Anime(
            Integer.parseInt(line[INDEX]),
            line[INDEX_OF_TITlE],
            line[INDEX_OF_FAMOUS_LINE],
            line[INDEX_OF_IMAGE_FILE_PATH]
        );
    }
}
