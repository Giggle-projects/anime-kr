package anime.anime;

import anime.data.AnimeDao;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
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

    public Anime random() {
        return animes.get(ThreadLocalRandom.current().nextInt(animes.size()));
    }

    public Anime random(String title) {
        if(title.isBlank()) {
            return random();
        }
        var animesByTitle = findAllByTitleContains(title);
        if (animesByTitle.size() < 1) {
            throw new NoSuchElementException("Non existent anime");
        }
        return animesByTitle.get(ThreadLocalRandom.current().nextInt(animesByTitle.size()));
    }

    public List<Anime> search(String title, String line, int pageSize, int pageNumber) {
        var collect = animes.stream()
            .filter(it -> it.title().contains(title))
            .filter(it -> it.famousLine().contains(line))
            .limit(pageNumber * pageSize + pageSize)
            .collect(Collectors.toList());
        return collect.subList(
            pageNumber * pageSize,
            Math.min(pageNumber * pageSize + pageSize, collect.size())
        );
    }

    public Anime getById(int id) {
        return animes.stream()
            .filter(it -> it.index().equals(id))
            .findAny()
            .orElseThrow(() -> new NoSuchElementException("Not exists id"));
    }

    public List<Anime> findAllByTitle(String title) {
        return animes.stream()
            .filter(it -> it.title().equals(title))
            .collect(Collectors.toList());
    }

    public List<Anime> findAllByTitleContains(String title) {
        return animes.stream()
            .filter(it -> it.title().contains(title))
            .collect(Collectors.toList());
    }
}
