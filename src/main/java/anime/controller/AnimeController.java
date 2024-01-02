package anime.controller;

import anime.domain.Anime;
import anime.domain.Animes;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnimeController {

    private final Animes animes;

    public AnimeController(Animes animes) {
        this.animes = animes;
    }

    @GetMapping("/api/random")
    public ResponseEntity<Anime> random() {
        val anime = animes.random();
        System.out.println(anime);
        return ResponseEntity.ok(anime);
    }
}
