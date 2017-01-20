import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Models a deck of cards
 * 
 * @author Thomas Barrett
 */
@SuppressWarnings("serial")
public class Deck<CardType extends Card> extends ArrayList<CardType> {

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
	public Deck(List<CardType> cards) {
		addAll(cards);
	}

	/**
	 * Removes and returns the top card from the deck, returns null if none
	 * 
	 * @return the top card in the deck
	 */
	public CardType deal() {
		return size() > 0 ? remove(size()-1) : null;
	}

	public CardType top() {
	    return get(size()-1);
	}
	
	/**
	 * Removes and returns an sub deck from the top of the deck
	 * 
	 * @param numCards
	 *            the number of cards to deal
	 * @return an array of cards from the top of the deck
	 */
	public Deck<CardType> deal(int numCards) {
		if (size() < numCards) return null;

		Deck<CardType> cards = new Deck<CardType>(subList(size()-numCards, size()));
		removeRange(size()-numCards, size());

		return cards;
	}

	/**
	 * Shuffles the remaining cards in the deck
	 */
	public void shuffle() {
		Collections.shuffle(this);
	}

}
