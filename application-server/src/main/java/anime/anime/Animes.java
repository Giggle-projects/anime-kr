package anime.anime;

import anime.data.AnimeDao;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Component
public class Animes {

    private final List<Anime> animes;

    public Animes(AnimeDao animeDao) {
        var animes = animeDao.readDataFile();
        if (animes.isEmpty()) {
            throw new NoSuchElementException("File data is empty");
        }
        this.animes = animes;
    }

    public Anime getById(int id) {
        return animes.stream()
            .filter(it -> it.index().equals(id))
            .findAny()
            .orElseThrow(() -> new NoSuchElementException("Not exists id"));
    }

    public List<Anime> searchByLine(String keyword) {
        return animes.stream()
            .filter(it -> it.famousLine().contains(keyword))
            .collect(Collectors.toList());
    }

    public List<Anime> findAllByTitle(String title) {
        return animes.stream()
            .filter(it -> it.title().equals(title))
            .collect(Collectors.toList());
    }

    public Anime random(Optional<String> optTitle) {
        if (optTitle.isEmpty()) {
            return animes.get(ThreadLocalRandom.current().nextInt(animes.size()));
        }
        var animesByTitle = findAllByTitle(optTitle.orElseThrow());
        return animesByTitle.get(ThreadLocalRandom.current().nextInt(animesByTitle.size()));
    }
}
