package anime;

import anime.dto.Anime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class AnimeApi {

    private final Animes animes;

    public AnimeApi(Animes animes) {
        this.animes = animes;
    }

    @GetMapping("/api/random")
    public ResponseEntity<Anime> random(@PathVariable Optional<String> title) {
        if (title.isPresent()) {
            final Anime anime = animes.randomByTitle(title.orElseThrow());
            return ResponseEntity.ok(anime);
        }
        final Anime anime = animes.random();
        return ResponseEntity.ok(anime);
    }

    @GetMapping("/api/anime")
    public ResponseEntity<List<Anime>> search(String keyword) {
        var anime = animes.searchByLine(keyword);
        return ResponseEntity.ok(anime);
    }

    @GetMapping("/api/anime/{id}")
    public ResponseEntity<Anime> find(@PathVariable int id) {
        var anime = animes.findById(id).orElseThrow();
        return ResponseEntity.ok(anime);
    }

    @GetMapping("/api/random/{title}")
    public ResponseEntity<Anime> random(@PathVariable String title) {
        val anime = animes.random(title);
        System.out.println(anime);
        return ResponseEntity.ok(anime);
    }
}
