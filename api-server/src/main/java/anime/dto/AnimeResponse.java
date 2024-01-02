package anime.dto;

public record AnimeResponse(
    Integer index,
    String title,
    String famousLine,
    String imageUrl
) {
    public static AnimeResponse of(Anime anime, String rootPath) {
        return new AnimeResponse(
            anime.index(),
            anime.title(),
            anime.famousLine(),
            rootPath + anime.imagePath()
        );
    }
}