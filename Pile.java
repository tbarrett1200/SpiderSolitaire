import acm.graphics.GCompound;

/**
 * Models a pile of in spider solitaire
 * @author Thomas Barrett
 */
@SuppressWarnings("serial")
public class Pile extends GCompound {

	private Deck cards;
	
	private static final double OFFSET_RATIO = 1/5.0;
	
	/**
	 * Creates a new pile with the given deck of cards
	 * @param cards the deck of cards to create the pile with
	 */
	public Pile(Deck cards) {
		this.cards = cards;
		layoutCards();
	}
	
	/**
	 * Returns the card at the given point on the canvas
	 * @param x the x coordinate of the point
	 * @param y the y coordinate of the point
	 * @return the Card at the coordinate or null if no card exists
	 */
	public Card selectCard(double x, double y) {
		return (Card) getElementAt(getLocalPoint(x, y));
	}
	
	private void layoutCards() {
		double offset = OFFSET_RATIO * GCard.cardHeight();
		for (int i = 0; i < cards.size(); i++) {
			add((GCard)cards.get(cards.size()-1-i), 0.0, i * offset);
		}
		cards.get(0).flipOver();
	}
}
