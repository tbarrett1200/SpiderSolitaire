
import acm.graphics.GCompound;

/**
 * A Deck represented as a GCompound
 */
@SuppressWarnings("serial")
public abstract class GDeck extends GCompound {

    protected Deck<GCard> deck;
    
    public GDeck() {
    	this.deck = new Deck<GCard>();
    }
    
    public GDeck(Deck<GCard> cards) {
    	this.deck = cards;
    	for (GCard card : deck) super.add(card);
    	layout();
    }
    
    /**
     * Returns and removes the top card from the deck
     * @return the removed card
     */
    public GCard deal() {
    	GCard card = deck.deal();
    	remove(card);
    	layout();
		return card;
    }
    
    /**
     * Removes and returns a Deck of cards from the top of the deck
     * @param numCards the number of cards to remove
     * @return the removed cards
     */
    public Deck<GCard> deal(int numCards) {
    	Deck<GCard> cards = deck.deal(numCards);
    	for (GCard card: cards) remove(card);
    	layout();
    	return cards;
    }
    
    /**
     * Adds a GCard
     * @param card the card to add
     */
    public void add(GCard card) {
    	deck.add(card);
    	super.add(card);
    	layout();
    }
    
    /**
     * Adds a deck of GCards
     * @param cards the deck to add
     */
    public void addAll(Deck<GCard> cards) {
    	deck.addAll(cards);
    	for (GCard card : deck) super.add(card);
    	layout();
    }
    
    /**
     * Adds a deck of GCards
     * @param cards the deck to add
     */
    public void addAll(GDeck cards) {
    	deck.addAll(cards.deck);
    	for (GCard card : deck) super.add(card);
    	layout();
    }
    
    public boolean isEmpty() {
    	return deck.isEmpty();
    }
    
    protected abstract void layout();

}
