package blackjack.domain.user;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import blackjack.domain.CardFixtures;
import blackjack.domain.card.Card;
import blackjack.domain.card.Deck;
import blackjack.domain.card.Denomination;
import blackjack.domain.card.Suit;
import blackjack.domain.money.Money;
import blackjack.domain.strategy.hit.PlayerHitStrategy;
import blackjack.domain.user.state.Ready;

public class PlayerTest {

    private Player createDefaultPlayer() {
        return new Player("pobi", new Ready(), "1000");
    }

    @DisplayName("플레이어 생성 검증")
    @Test
    public void createPlayer() {
        //given
        String name = "pobi";

        //when
        Player player = new Player(name, new Ready(), "1000");

        //then
        assertThat(player).isNotNull();
    }

    @DisplayName("플레이어는 카드를 뽑을 수 있다.")
    @Test
    public void testDrawCard() {
        //given
        Player player = createDefaultPlayer();
        player.hit(new Card(Suit.CLOVER, Denomination.ACE));

        //when
        List<Card> cards = player.getHandCards();

        //then
        assertThat(cards.size()).isEqualTo(1);
    }

    @DisplayName("카드를 뽑을 수 있는지 여부를 확인할 수 있다.")
    @Test
    public void testCardDrawable() {
        //given
        Deck deck = new Deck();
        Player player = createDefaultPlayer();

        //when
        while (player.hit(deck.drawCard()))
            ;

        //then
        assertThat(player.isFinished()).isTrue();
    }

    @DisplayName("플레어어가 보여주는 초기 카드는 2장이다.")
    @Test
    public void testShowInitCards() {
        //given
        Player player = createDefaultPlayer();

        player.hit(new Card(Suit.CLOVER, Denomination.SEVEN));
        player.hit(new Card(Suit.SPADE, Denomination.JACK));

        //when
        List<Card> cards = player.showInitCards();

        //then
        assertThat(cards.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("플레이어는 참을 입력받으면 카드를 한장 더 받을 수 있다.")
    public void testPlayerHitWithInputY() {
        // given
        Deck deck = new Deck();
        Player player = createDefaultPlayer();
        // when
        boolean isHit = player.hitOrStay(deck, new PlayerHitStrategy(() -> true));
        // then
        assertThat(isHit).isTrue();
    }

    @Test
    @DisplayName("플레이어는 거짓을 입력받으면 카드를 한장 더 받을 수 없다.")
    public void testPlayerHitWithInputN() {
        // given
        Deck deck = new Deck();
        Player player = createDefaultPlayer();

        player.hit(CardFixtures.FIVE);
        player.hit(CardFixtures.SEVEN);
        // when
        boolean isHit = player.hitOrStay(deck, new PlayerHitStrategy(() -> false));
        // then
        assertThat(isHit).isFalse();
    }

    @Test
    @DisplayName("딜러 상대로 수익률을 계산한다.")
    public void testPlayerProfit() {
        // given
        Player player = createDefaultPlayer();
        Dealer dealer = new Dealer();

        player.hit(CardFixtures.TEN);
        player.hit(CardFixtures.SEVEN);
        player.stay();

        dealer.hit(CardFixtures.TEN);
        dealer.hit(CardFixtures.FIVE);
        // when
        Money profit = player.calculateProfit(dealer);
        // then
        assertThat(profit.getAmount()).isEqualTo(1000);
    }
}