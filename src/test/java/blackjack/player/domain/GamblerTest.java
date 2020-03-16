package blackjack.player.domain;

import blackjack.card.domain.CardBundle;
import blackjack.card.domain.component.CardNumber;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static blackjack.card.domain.CardBundleHelper.aCardBundle;
import static org.assertj.core.api.Assertions.assertThat;

class GamblerTest {

	private static Stream<Arguments> bundleProvider() {
		return Stream.of(
			Arguments.of(
				true, aCardBundle(CardNumber.TEN)
			),
			Arguments.of(
				false, aCardBundle(CardNumber.TEN, CardNumber.ACE)
			),
			Arguments.of(
				false, aCardBundle(CardNumber.TEN, CardNumber.TEN, CardNumber.TWO)
			)
		);
	}

	@DisplayName("겜블러는 버스트이거나 블랙잭이면 카드를 뽑을수 없다.")
	@ParameterizedTest
	@MethodSource("bundleProvider")
	void isDrawable(boolean result, CardBundle cardBundle) {
		Player gambler = new Gambler(cardBundle, "bebop");
		assertThat(gambler.isDrawable()).isEqualTo(result);
	}
}