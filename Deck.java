import java.util.ArrayList;
import java.util.Collections;

/**
 * Models a deck of cards
 * @author Thomas Barrett
 */
@SuppressWarnings("serial")
public class Deck extends ArrayList<Card> {

    /**
     * Removes and returns the top card from the deck, returns null if none
     * @return the top card in the deck
     */
    public Card deal() {
	return size() > 0 ? remove(0) : null;
    }
    
    /**
     * Shuffles the remaining cards in the deck
     */
    public void shuffle() {
	Collections.shuffle(this);
    }
    
}
