package anime.controller;

import anime.domain.Anime;
import anime.domain.Animes;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AnimeController {

    private final Animes animes;

    public AnimeController(Animes animes) {
        this.animes = animes;
    }

    @GetMapping("/api/random")
    public ResponseEntity<Anime> random() {
        val anime = animes.random();
        return ResponseEntity.ok(anime);
    }

    @GetMapping("/api/search")
    public ResponseEntity<List<Anime>> search(String keyword) {
        val anime = animes.searchByLine(keyword);
        return ResponseEntity.ok(anime);
    }

    @GetMapping("/api/anime/{id}")
    public ResponseEntity<Anime> find(@PathVariable int id) {
        val anime = animes.findById(id);
        return ResponseEntity.ok(anime);
    }

    @GetMapping("/api/random/{title}")
    public ResponseEntity<Anime> random(@PathVariable String title) {
        val anime = animes.random(title);
        System.out.println(anime);
        return ResponseEntity.ok(anime);
    }
}
