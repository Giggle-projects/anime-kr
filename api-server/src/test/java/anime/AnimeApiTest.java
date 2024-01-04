package anime;

import anime.alert.AlertManagerChain;
import anime.controller.AnimeApi;
import anime.controller.AnimeExceptionHandler;
import anime.controller.Animes;
import anime.dto.Anime;
import anime.dto.AnimeResponse;
import anime.exception.AnimeException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AnimeApiTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String IMAGE_ROOT_PATH = "/";

    @Mock
    private Animes animes;

    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders
            .standaloneSetup(new AnimeApi(IMAGE_ROOT_PATH, animes))
            .setControllerAdvice(new AnimeExceptionHandler(new AlertManagerChain()))
            .build();
    }

    @DisplayName("Request random anime")
    @Test
    public void random() throws Exception {
        var randomAnime = new Anime(1, "title", "famousLine", "imagePath");
        Mockito.when(animes.random(Optional.empty()))
            .thenReturn(randomAnime);

        var expectResponseBody = entityToResponse(randomAnime);
        mockMvc
            .perform(get("/api/random"))
            .andExpect(status().isOk())
            .andExpect(content().json(OBJECT_MAPPER.writeValueAsString(expectResponseBody)));
    }

    @DisplayName("Request random anime by title")
    @Test
    public void randomByTitle() throws Exception {
        var title = "title";
        var randomAnime = new Anime(1, title, "famousLine", "imagePath");
        Mockito.when(animes.random(Optional.of(title)))
            .thenReturn(randomAnime);

        var expectResponseBody = entityToResponse(randomAnime);
        mockMvc
            .perform(get("/api/random/" + title))
            .andExpect(status().isOk())
            .andExpect(content().json(OBJECT_MAPPER.writeValueAsString(expectResponseBody)));
    }

    @DisplayName("Request search anime with line")
    @Test
    public void searchWithLine() throws Exception {
        var searchLine = "line";
        var searched = List.of(
            new Anime(1, "title", searchLine + "1", "imagePath"),
            new Anime(2, "title", searchLine + "2", "imagePath")
        );
        Mockito.when(animes.searchByLine(searchLine))
            .thenReturn(searched);

        var expectResponseBody = entityToResponse(searched);
        mockMvc
            .perform(get("/api/anime?keyword=" + searchLine))
            .andExpect(status().isOk())
            .andExpect(content().json(OBJECT_MAPPER.writeValueAsString(expectResponseBody)));
    }

    @DisplayName("Request anime with id")
    @Test
    public void findById() throws Exception {
        var searchId = 1;
        var searched = new Anime(1, "title", "famousLine", "imagePath");
        Mockito.when(animes.getById(searchId))
            .thenReturn(searched);

        var expectResponseBody = entityToResponse(searched);
        mockMvc
            .perform(get("/api/anime/" + searchId))
            .andExpect(status().isOk())
            .andExpect(content().json(OBJECT_MAPPER.writeValueAsString(expectResponseBody)));
    }

    @DisplayName("Bad request with invalid id - not exists data")
    @Test
    public void findByInvalidId1() throws Exception {
        var invalidSearchId = -1;
        Mockito.when(animes.getById(invalidSearchId))
            .thenThrow(new AnimeException("no such element"));

        mockMvc
            .perform(get("/api/anime/" + invalidSearchId))
            .andExpect(status().isBadRequest());
    }

    @DisplayName("Bad request with invalid id - invalid type")
    @Test
    public void findByInvalidId2() throws Exception {
        var invalidSearchId = "invalidType";
        mockMvc
            .perform(get("/api/anime/" + invalidSearchId))
            .andExpect(status().isBadRequest());
    }

    @DisplayName("Response server error by unhandled exception")
    @Test
    public void responseServerError() throws Exception {
        Mockito.when(animes.random(Optional.empty()))
            .thenThrow(IllegalArgumentException.class);
        mockMvc
            .perform(get("/api/random"))
            .andExpect(status().isInternalServerError());
    }

    private AnimeResponse entityToResponse(Anime anime) {
        return AnimeResponse.of(anime, IMAGE_ROOT_PATH);
    }

    private List<AnimeResponse> entityToResponse(List<Anime> animes) {
        return animes.stream()
            .map(it -> AnimeResponse.of(it, IMAGE_ROOT_PATH))
            .collect(Collectors.toList());
    }
}
