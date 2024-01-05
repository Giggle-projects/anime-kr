package anime.controller;

import anime.dto.AnimeResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class AnimeView {

    private final Animes animes;

    public AnimeView(Animes animes) {
        this.animes = animes;
    }

    @GetMapping({"/view/random", "/view/random/{title}"})
    public String random(@PathVariable Optional<String> title, Model model) {
        var animeResponse = AnimeResponse.of(animes.random(title));
        model.addAttribute("ani", animeResponse);
        return "index";
    }

    @GetMapping("/view/anime/{id}")
    public String find(@PathVariable int id, Model model) {
        var animeResponse = AnimeResponse.of(animes.getById(id));
        model.addAttribute("ani", animeResponse);
        return "index";
    }
}
