package anime;

import anime.dto.Anime;
import anime.exception.AnimeException;
import anime.utils.SlackUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AnimeApi {

    private final Animes animes;

    public AnimeApi(Animes animes) {
        this.animes = animes;
    }

    @GetMapping("/api/random/{title}")
    public ResponseEntity<Anime> random(@PathVariable Optional<String> title) {
        if (title.isPresent()) {
            final Anime anime = animes.randomByTitle(
                    title.orElseThrow(() -> new AnimeException("title is not found"))
            );
            return ResponseEntity.ok(anime);
        }
        final Anime anime = animes.random();
        return ResponseEntity.ok(anime);
    }

    @GetMapping("/api/anime")
    public ResponseEntity<List<Anime>> search(@RequestParam String keyword) {
        var anime = animes.searchByLine(keyword);
        return ResponseEntity.ok(anime);
    }

    @GetMapping("/api/anime/{id}")
    public ResponseEntity<Anime> find(@PathVariable int id) {
        var anime = animes.findById(id)
                .orElseThrow(() -> new AnimeException("id is not found"));
        return ResponseEntity.ok(anime);
    }

    @ExceptionHandler(AnimeException.class)
    public ResponseEntity<String> animeExceptionHandler(AnimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> unhandledServerError(IllegalArgumentException e) {
        SlackUtils.send(e.getMessage());
        return ResponseEntity.internalServerError().body("interval server error");
    }
}

