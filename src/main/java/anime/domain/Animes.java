package anime.domain;

import lombok.val;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class Animes {

    private final Map<Integer, Anime> animes;
    private final RandomAnimeCache valuesCache;

    public Animes(AnimeDao animeDao) {
        animes = new ConcurrentHashMap<>();
        var i = animes.size() + 1;
        for (val anime : animeDao.readDataFile()) {
            animes.put(i++, anime);
        }
        valuesCache = new RandomAnimeCache(new ArrayList<>(animes.values()));
    }

    public Anime random() {
        return valuesCache.next();
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
