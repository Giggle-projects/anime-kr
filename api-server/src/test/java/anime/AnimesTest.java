package anime;

import anime.dao.AnimeDao;
import anime.dto.Anime;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AnimesTest {

    @Mock
    private AnimeDao animeDao;

    Anime dummy1 = new Anime(1, "A", "this is test line 1", "imagePath");
    Anime dummy2 = new Anime(2, "A", "this is test line 2", "imagePath");
    Anime dummy3 = new Anime(3, "B", "this is test line 3", "imagePath");
    Anime dummy4 = new Anime(4, "C", "this is test line 4", "imagePath");

    Animes animes;

    @BeforeEach
    private void init() {
        Mockito.when(animeDao.readDataFile())
            .thenReturn(List.of(dummy1, dummy2, dummy3, dummy4));
        animes = new Animes(animeDao);
    }

    @DisplayName("명대사를 검색할 수 있다.")
    @Nested
    class SearchByLine {

        @DisplayName("단일 결과가 나오는 경우")
        @Test
        void searchByLine1() {
            var searchResult = animes.searchByLine("1");
            assertThat(searchResult.getFirst()).isEqualTo(dummy1);
        }

        @DisplayName("여러 결과가 나오는 경우")
        @Test
        void searchByLine2() {
            var searchResult = animes.searchByLine("this");
            assertThat(searchResult).isEqualTo(List.of(dummy1, dummy2, dummy3, dummy4));
        }

        @DisplayName("검색 결과가 없는 경우")
        @Test
        void searchByLine3() {
            var searchResult = animes.searchByLine("this is not exists");
            assertThat(searchResult).isEqualTo(List.of());
        }

        @DisplayName("검색 키워드가 null 인 경우, NPE 예외를 발생한다.")
        @Test
        void searchByLine4() {
            assertThatThrownBy(
                () -> animes.searchByLine(null)
            ).isInstanceOf(NullPointerException.class);
        }
    }

    @DisplayName("애니 제목으로 검색할 수 있다")
    @Test
    void searchByTitle() {
        var animes = new Animes(animeDao);
        var result = animes.findAllByTitle("A");
        assertThat(result).isEqualTo(List.of(dummy1, dummy2));
    }
}