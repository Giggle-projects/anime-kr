package anime.dto;

public record AnimeResponse(
    Integer index,
    String title,
    String famousLine
) {
    public static AnimeResponse of(Anime anime) {
        return new AnimeResponse(
            anime.index(),
            anime.title(),
            anime.famousLine()
        );
    }
}