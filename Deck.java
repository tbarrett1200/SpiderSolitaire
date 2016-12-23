import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Models a deck of cards
 * 
 * @author Thomas Barrett
 */
@SuppressWarnings("serial")
public class Deck extends ArrayList<Card> {

	/**
	 * Creates an empty deck
	 */
	public Deck() {
	}

	/**
	 * Creates a deck filled with the given cards
	 * 
	 * @param cards
	 */
	public Deck(List<Card> cards) {
		addAll(cards);
	}

	/**
	 * Removes and returns the top card from the deck, returns null if none
	 * 
	 * @return the top card in the deck
	 */
	public Card deal() {
		return size() > 0 ? remove(0) : null;
	}

	/**
	 * Removes and returns an sub deck from the top of the deck
	 * 
	 * @param numCards
	 *            the number of cards to deal
	 * @return an array of cards from the top of the deck
	 */
	public Deck deal(int numCards) {
		if (size() < numCards)
			return null;

		Deck cards = new Deck(subList(0, numCards));
		removeRange(0, numCards);

		return cards;
	}

	/**
	 * Shuffles the remaining cards in the deck
	 */
	public void shuffle() {
		Collections.shuffle(this);
	}

}
