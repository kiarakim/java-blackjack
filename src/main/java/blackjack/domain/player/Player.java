package blackjack.domain.player;

import blackjack.domain.card.PlayingCard;
import blackjack.domain.card.PlayingCards;
import blackjack.domain.state.State;

public interface Player {

    boolean isDealer();

    boolean isHit();

    boolean isBlackjack();

    boolean isBust();

    boolean isDraw(PlayingCards playingCards);

    boolean isLose(PlayingCards playingCards);

    boolean isRunning();

    void draw(PlayingCard playingCard);

    void stay();

    State getState();

    PlayingCards playingCards();

    String getName();
}