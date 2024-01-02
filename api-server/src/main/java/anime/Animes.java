package anime;

import anime.dao.AnimeDao;
import anime.dto.Anime;
import anime.exception.AnimeException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Component
public class Animes {

    private final List<Anime> animes;

    public Animes(AnimeDao animeDao) {
        animes = animeDao.readDataFile();
    }

    public Optional<Anime> findById(int id) {
        return animes.stream()
            .filter(it -> it.index().equals(id))
            .findAny();
    }

    public Anime getById(int id) {
        return findById(id).orElseThrow(() -> new AnimeException("id is not found"));
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
            return random();
        }
        return randomByTitle(optTitle.orElseThrow(() -> new AnimeException("title is not found")));
    }

    public Anime random() {
        return animes.get(ThreadLocalRandom.current().nextInt(animes.size()));
    }

    public Anime randomByTitle(String title) {
        var byTitle = findAllByTitle(title);
        return byTitle.get(ThreadLocalRandom.current().nextInt(byTitle.size()));
    }
}
