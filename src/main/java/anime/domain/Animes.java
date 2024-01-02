package anime.domain;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Animes {

    public final Map<Integer, Anime> animes = new ConcurrentHashMap<>();

    public Animes(AnimeDao animeDao) {
        int i = animes.size() + 1;
        for (var anime : animeDao.readDataFile()) {
            animes.put(i++, anime);
        }
    }
}
