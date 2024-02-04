package anime.anime;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class Animes {

    private final AnimeDao animes;

    public Anime random() {
        return animes.rand();
    }

    public Anime random(String title) {
        if (title.isBlank()) {
            return random();
        }
        var animesByTitle = allByTitleContains(title);
        if (animesByTitle.isEmpty()) {
            throw new NoSuchElementException("Non existent anime");
        }
        return animesByTitle.get(ThreadLocalRandom.current().nextInt(animesByTitle.size()));
    }

    public List<Anime> search(String title, String line, int pageSize, int pageNumber) {
        var collect = animes.findAllContains(title, line)
            .stream()
            .limit(pageNumber * pageSize + pageSize)
            .collect(Collectors.toList());
        return collect.subList(
            Math.min(collect.size(), pageNumber * pageSize),
            Math.min(pageNumber * pageSize + pageSize, collect.size())
        );
    }

    public Anime getById(int id) {
        return animes.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Not exists id"));
    }

    public List<Anime> allByTitleContains(String title) {
        return animes.findAllContainsByTitle(title);
    }
}
