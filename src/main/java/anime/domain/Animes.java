package anime.domain;

import lombok.val;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class Animes {

    private final Map<Integer, Anime> animes;
    private final RandomAnimes randomAnimes;

    public Animes(AnimeDao animeDao) {
        animes = new ConcurrentHashMap<>();
        final Map<String, List<Anime>> valuesByTitle = new ConcurrentHashMap<>();

        var i = animes.size() + 1;
        for (val anime : animeDao.readDataFile()) {
            animes.put(i++, anime);

            var animeByTitle = valuesByTitle.getOrDefault(anime.title(), new ArrayList<>());
            animeByTitle.add(anime);
            valuesByTitle.put(anime.title(), animeByTitle);
        }
        randomAnimes = new RandomAnimes(new ArrayList<>(animes.values()), valuesByTitle);
    }

    public Anime random() {
        return randomAnimes.next();
    }

    public Anime random(String title) {
        return randomAnimes.next(title);
    }

    public Anime findById(int i) {
        return animes.get(i);
    }

    public int size() {
        return animes.size();
    }

    public List<Anime> searchByLine(String keyword) {
        return animes.values().stream()
            .filter(it -> it.famousLine().contains(keyword))
            .collect(Collectors.toList());
    }
}
