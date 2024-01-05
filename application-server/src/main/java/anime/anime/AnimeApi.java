package anime.anime;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class AnimeApi {

    private final Animes animes;

    @GetMapping({"/api/random", "/api/random/{title}"})
    public ResponseEntity<Anime> random(@PathVariable Optional<String> title) {
        var result = animes.random(title);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/api/anime")
    public ResponseEntity<List<Anime>> search(@RequestParam String keyword) {
        var results = animes.searchByLine(keyword);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/api/anime/{id}")
    public ResponseEntity<Anime> find(@PathVariable int id) {
        var result = animes.getById(id);
        return ResponseEntity.ok(result);
    }
}
