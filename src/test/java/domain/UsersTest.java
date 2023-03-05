package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class UsersTest {

    @DisplayName("참여 인원은 1명 이상 4명 이하이다")
    @ParameterizedTest
    @MethodSource("parameterProvider")
    void playerCount1_4(List<String> names) {
        assertThatNoException()
            .isThrownBy(() -> Users.from(names));
    }

    static Stream<List<String>> parameterProvider() {
        return Stream.of(
            List.of("a"),
            List.of("a", "kiara", "ash", "woowa")
        );
    }

    @DisplayName("참여 인원은 1명미만이 될 수 없다")
    @Test
    void playerCount_shouldNotBeUnder1() {
        assertThatThrownBy(() -> Users.from(Collections.emptyList()))
            .hasMessageContaining("플레이어 수는 ")
            .hasMessageContaining("명 이상, ");
    }

    @DisplayName("참여 인원은 4명초과가 될 수 없다")
    @Test
    void playerCount_shouldNotBeOver4() {
        assertThatThrownBy(() -> Users.from(List.of("a", "b", "c", "d", "e")))
            .hasMessageContaining("플레이어 수는 ")
            .hasMessageContaining("명 이하여야 합니다.");
    }

    @DisplayName("플레이어의 이름은 중복될 수 없다")
    @Test
    void validateDuplication() {
        assertThatThrownBy(() -> Users.from(List.of("hongo", "hongo", "ash", "kiara")))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("플레이어 이름은 중복될 수 없습니다.");
    }

    @DisplayName("해당 플레이어에게 카드를 추가한다")
    @Test
    void hitCardByName() {
        Users users = Users.from(List.of("hongo", "ash", "kiara"));

        Player player = users.getPlayers().get(0);
        player.hit(new Card(Denomination.JACK, Suits.HEART));
        assertThat(player.getScore()).isEqualTo(10);
    }
}
