package anime;

import anime.anime.Animes;
import anime.data.AnimeDao;
import anime.anime.Anime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class AnimesTest {

    @Mock
    private AnimeDao animeDao;

    private Animes animes;

    Anime dummy1 = new Anime(1, "A", "this is test line 1");
    Anime dummy2 = new Anime(2, "A", "this is test line 2");
    Anime dummy3 = new Anime(3, "B", "this is test line 3");
    Anime dummy4 = new Anime(4, "C", "this is test line 4");

    List<Anime> dummies = List.of(dummy1, dummy2, dummy3, dummy4);

    @BeforeEach
    public void init() {
        Mockito.when(animeDao.readDataFile())
            .thenReturn(dummies);
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

    @DisplayName("애니 제목으로 검색할 수 있다.")
    @Nested
    class searchByTitle {

        @DisplayName("애니 제목으로 검색할 수 있다.")
        @Test
        void searchByTitle1() {
            var result = animes.findAllByTitle("A");
            assertThat(result).isEqualTo(List.of(dummy1, dummy2));
        }

        @DisplayName("검색 결과가 없는 경우 빈 리스트를 반환한다.")
        @Test
        void searchByTitle2() {
            var result = animes.findAllByTitle("this is not exist");
            assertThat(result).isEqualTo(List.of());
        }

        @DisplayName("제목이 null 인 경우, 빈 리스트를 반환한다.")
        @Test
        void searchByTitle3() {
            var result = animes.findAllByTitle(null);
            assertThat(result).isEqualTo(List.of());
        }
    }

    @DisplayName("인덱스 번호로 검색할 수 있다.")
    @Nested
    class searchById {

        @DisplayName("애니 인덱스 번호로 검색할 수 있다.")
        @Test
        void searchById1() {
            var result = animes.getById(1);
            assertThat(result).isEqualTo(dummy1);
        }

        @DisplayName("존재하지 않은 인덱스 번호로 검색할 경우, AE 예외를 발생시킨다.")
        @Test
        void searchById2() {
            assertThatThrownBy(
                () -> animes.getById(Integer.MAX_VALUE)
            ).isInstanceOf(NoSuchElementException.class);
        }
    }

    @DisplayName("랜덤으로 조회할 수 있다.")
    @Nested
    class SearchRandom {

        @DisplayName("조회시 랜덤으로 조회된 데이터를 조회할 수 있다.")
        @Test
        void searchRandom1() {
            for (int i = 0; i < 5; i++) {
                var result = animes.random(Optional.empty());
                assertThat(dummies.contains(result)).isTrue();
            }
        }

        @DisplayName("조회시 제목을 통해 제목에 해당하는 랜덤값을 조회할 수 있다.")
        @Test
        void searchRandom2() {
            for (int i = 0; i < 5; i++) {
                var title = "A";
                var result = animes.random(Optional.of(title));
                assertThat(result.title()).isEqualTo(title);
            }
        }
    }
}
