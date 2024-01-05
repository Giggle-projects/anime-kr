package anime.controller;

import anime.accessCount.Access;
import anime.accessCount.AccessCount;
import anime.dto.Anime;
import anime.dto.AnimeResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Access
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

    @GetMapping("/api/access/count")
    public ResponseEntity<Integer> userAccessCount() {
        var userAccessCount = AccessCount.getAccessCount();
        return ResponseEntity.ok(userAccessCount);
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

