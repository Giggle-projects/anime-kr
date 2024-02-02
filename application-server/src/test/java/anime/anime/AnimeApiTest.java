package anime.anime;

import anime.alert.AlertManagerChain;
import anime.anime.Anime;
import anime.anime.AnimeApi;
import anime.anime.AnimeApiExceptionHandler;
import anime.anime.Animes;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AnimeApiTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Mock
    private Animes animes;

    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders
            .standaloneSetup(new AnimeApi(animes))
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
            .setControllerAdvice(new AnimeApiExceptionHandler(new AlertManagerChain(Collections.emptyList())))
            .build();
    }

    @DisplayName("Request random anime")
    @Test
    public void random() throws Exception {
        var randomAnime = new Anime(1, "title", "famousLine");
        Mockito.when(animes.random(""))
            .thenReturn(randomAnime);

        mockMvc
            .perform(get("/api/anime/random"))
            .andExpect(status().isOk())
            .andExpect(content().json(OBJECT_MAPPER.writeValueAsString(randomAnime)));
    }

    @DisplayName("Request random anime by title")
    @Test
    public void randomByTitle() throws Exception {
        var title = "title";
        var randomAnime = new Anime(1, title, "famousLine");
        Mockito.when(animes.random(title))
            .thenReturn(randomAnime);

        mockMvc
            .perform(get("/api/anime/random?title=" + title))
            .andExpect(status().isOk())
            .andExpect(content().json(OBJECT_MAPPER.writeValueAsString(randomAnime)));
    }

    @DisplayName("Request search anime with title and line")
    @Test
    public void search() throws Exception {
        var searchLine = "line";
        var title = "title";
        var searched = List.of(
            new Anime(1, title, searchLine + "1"),
            new Anime(2, title, searchLine + "2")
        );
        Mockito.when(animes.search(title, searchLine, 20, 0))
            .thenReturn(searched);

        mockMvc
            .perform(get("/api/anime/search?line=" + searchLine + "&title=" + title))
            .andExpect(status().isOk())
            .andExpect(content().json(OBJECT_MAPPER.writeValueAsString(searched)));
    }

    @DisplayName("Request search anime with pagination")
    @Test
    public void searchWithPagination() throws Exception {
        var searched = List.of(
            new Anime(1, "title", "searchLine1"),
            new Anime(2, "title", "searchLine2")
        );
        Mockito.when(animes.search("", "", 10, 1))
            .thenReturn(searched);

        mockMvc
            .perform(get("/api/anime/search?page=1&size=10"))
            .andExpect(status().isOk())
            .andExpect(content().json(OBJECT_MAPPER.writeValueAsString(searched)));
    }

    @DisplayName("Request anime with id")
    @Test
    public void findById() throws Exception {
        var searchId = 1;
        var searched = new Anime(1, "title", "famousLine");
        Mockito.when(animes.getById(searchId))
            .thenReturn(searched);

        mockMvc
            .perform(get("/api/anime/" + searchId))
            .andExpect(status().isOk())
            .andExpect(content().json(OBJECT_MAPPER.writeValueAsString(searched)));
    }

    @DisplayName("Bad request with invalid id - not exists data")
    @Test
    public void findByInvalidId1() throws Exception {
        var invalidSearchId = -1;
        Mockito.when(animes.getById(invalidSearchId))
            .thenThrow(new NoSuchElementException("no such element"));

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
        Mockito.when(animes.random(Mockito.anyString()))
            .thenThrow(IllegalArgumentException.class);
        mockMvc
            .perform(get("/api/anime/random"))
            .andExpect(status().isInternalServerError());
    }
}
