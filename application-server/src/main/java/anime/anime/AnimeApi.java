package anime.anime;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AnimeApi {

    private final Animes animes;

    @GetMapping("/api/anime/random")
    public ResponseEntity<Anime> random(
        @RequestParam(required = false, defaultValue = "") String title
    ) {
        var result = animes.random(title);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/api/anime/search")
    public ResponseEntity<List<Anime>> search(
        @RequestParam(required = false, defaultValue = "") String title,
        @RequestParam(required = false, defaultValue = "") String line,
        Pageable pageable
    ) {
        var results = animes.search(title, line, pageable.getPageSize(), pageable.getPageNumber());
        return ResponseEntity.ok(results);
    }

    @GetMapping("/api/anime/{id}")
    public ResponseEntity<Anime> find(@PathVariable int id) {
        var result = animes.getById(id);
        return ResponseEntity.ok(result);
    }
}
