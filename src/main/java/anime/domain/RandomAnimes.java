package anime.domain;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomAnimes {

    private final Random RANDOM = new Random();
    private final List<Anime> animeCache;
    private final Map<String, List<Anime>> valuesByTitle;

    public RandomAnimes(List<Anime> animeCache, Map<String, List<Anime>> valuesByTitle) {
        this.animeCache = animeCache;
        this.valuesByTitle = valuesByTitle;
    }

    public Anime next() {
        return animeCache.get(RANDOM.nextInt(animeCache.size()));
    }

    public Anime next(String title) {
        return valuesByTitle.get(title).get(RANDOM.nextInt(valuesByTitle.size()));
    }
}
