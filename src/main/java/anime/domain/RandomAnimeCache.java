package anime.domain;

import java.util.List;
import java.util.Random;

public class RandomAnimeCache {

    private final Random RANDOM = new Random();
    private final List<Anime> animeCache;

    public RandomAnimeCache(List<Anime> animeCache) {
        this.animeCache = animeCache;
    }

    public Anime next() {
        return animeCache.get(RANDOM.nextInt(animeCache.size()));
    }
}
