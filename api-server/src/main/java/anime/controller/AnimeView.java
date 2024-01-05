package anime.controller;

import anime.accessCount.Access;
import anime.dto.AnimeResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Access
@Controller
public class AnimeView {

    private final String imageRootPath;
    private final Animes animes;

    public AnimeView(
        @Value("${image.url.root.path}") String imageRootPath,
        Animes animes
    ) {
        this.imageRootPath = imageRootPath;
        this.animes = animes;
    }

    @GetMapping({"/view/random", "/view/random/{title}"})
    public String random(@PathVariable Optional<String> title, Model model) {
        var animeResponse = AnimeResponse.of(animes.random(title), imageRootPath);
        model.addAttribute("ani", animeResponse);
        return "anime-info";
    }

    @GetMapping("/view/anime/{id}")
    public String find(@PathVariable int id, Model model) {
        var animeResponse = AnimeResponse.of(animes.getById(id), imageRootPath);
        model.addAttribute("ani", animeResponse);
        return "anime-info";
    }
}
