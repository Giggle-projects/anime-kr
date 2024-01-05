package anime.dao;

import anime.dto.Anime;

public class AnimeMapper {

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
