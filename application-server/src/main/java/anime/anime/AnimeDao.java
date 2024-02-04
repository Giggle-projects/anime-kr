package anime.anime;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class AnimeDao {

    private File file;

    public int size() {
        try {
            return (int) fileStream().count();
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to load data file", e);
        }
    }

    public Anime rand() {
        try {
            int count = (int) fileStream().count();
            int randomId = ThreadLocalRandom.current().nextInt(count);
            return fileStream()
                .skip(randomId)
                .map(it -> AnimeMapper.toAnime(it.split("\\|")))
                .findFirst()
                .orElseThrow();
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to load data file", e);
        }
    }

    public Optional<Anime> findById(int id) {
        try {
            return fileStream()
                .map(it -> AnimeMapper.toAnime(it.split("\\|")))
                .filter(it -> it.index().equals(id))
                .findAny();
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to load data file", e);
        }
    }

    public List<Anime> findAllContains(String title, String line) {
        try {
            return fileStream()
                .map(it -> AnimeMapper.toAnime(it.split("\\|")))
                .filter(it -> it.title().contains(title) && it.famousLine().contains(line))
                .toList();
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to load data file", e);
        }
    }

    public List<Anime> findAllContainsByTitle(String title) {
        try {
            return fileStream()
                .map(it -> AnimeMapper.toAnime(it.split("\\|")))
                .filter(it -> it.title().contains(title))
                .toList();
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to load data file", e);
        }
    }

    public Stream<String> fileStream() throws IOException {
        return Files.readLines(file, Charsets.UTF_8).stream()
            .skip(2)
            .filter(it-> !it.isBlank());
    }

    static class AnimeMapper {

        private static final int INDEX = 1;
        private static final int INDEX_OF_TITlE = 2;
        private static final int INDEX_OF_FAMOUS_LINE = 3;

        public static Anime toAnime(String[] line) {
            return new Anime(
                Integer.parseInt(line[INDEX]),
                line[INDEX_OF_TITlE],
                line[INDEX_OF_FAMOUS_LINE]
            );
        }
    }
}


