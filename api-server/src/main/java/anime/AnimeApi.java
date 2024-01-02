package anime;

import anime.dto.Anime;
import anime.dto.AnimeResponse;
import anime.utils.SlackUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class AnimeApi {

    private final String imageRootPath;
    private final Animes animes;

    public AnimeApi(
        @Value("${image.url.root.path}") String imageRootPath,
        Animes animes
    ) {
        this.imageRootPath = imageRootPath;
        this.animes = animes;
    }

    @GetMapping({"/api/random", "/api/random/{title}"})
    public ResponseEntity<AnimeResponse> random(@PathVariable Optional<String> title) {
        return asResponse(animes.random(title));
    }

    @GetMapping("/api/anime")
    public ResponseEntity<List<AnimeResponse>> search(@RequestParam String keyword) {
        return asResponse(animes.searchByLine(keyword));
    }


    @GetMapping("/api/anime/{id}")
    public ResponseEntity<AnimeResponse> find(@PathVariable int id) {
        return asResponse(animes.getById(id));
    }

//    @ExceptionHandler(AnimeException.class)
//    public ResponseEntity<String> animeExceptionHandler(AnimeException e) {
//        return ResponseEntity.badRequest().body(e.getMessage());
//    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> unhandledServerError(IllegalArgumentException e) {
        SlackUtils.send(e.getMessage());
        e.printStackTrace();
        return ResponseEntity.internalServerError().body("interval server error");
    }

    private ResponseEntity<AnimeResponse> asResponse(Anime anime) {
        var response = AnimeResponse.of(anime, imageRootPath);
        return ResponseEntity.ok(response);
    }

    private ResponseEntity<List<AnimeResponse>> asResponse(List<Anime> animes) {
        var response = animes.stream()
            .map(it -> AnimeResponse.of(it, imageRootPath))
            .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}

