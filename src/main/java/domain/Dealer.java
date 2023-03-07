package domain;

public class Dealer extends User {

	public Dealer(Cards cards) {
		super("딜러", cards);
	}

	public Card getOneDealerCard() {
		return cards.getCards().get(0);
	}
}
