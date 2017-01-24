import acm.graphics.GCompound;
import acm.graphics.GObject;
import acm.graphics.GRect;

/**
 * Models a pile of in spider solitaire
 * @author Thomas Barrett
 */
@SuppressWarnings("serial")
public class Pile extends GDeck {

	private GRect emptyRect;
	
	private static final double OFFSET_RATIO = 1 / 5.0;
	private static final double OFFSET = OFFSET_RATIO * GCard.cardHeight();
	private double scale = 1.0;
	
	/**
	 * Creates a new pile with the given deck of cards
	 * @param cards the deck of cards to create the pile with
	 */
	public Pile(Deck<GCard> cards) {
		super(cards);
		addEmptyRect();
		layout();
	}


	private void addEmptyRect() {
	    emptyRect = new GRect(GCard.cardWidth(), GCard.cardHeight());
	    add(emptyRect);
	}
	

	/**
	 * Picks up a pile containing the given number of cards
	 * @param numCards the amount of cards to remove
	 * @return a new pile
	 */
	public Pile pickUp(int numCards) {
		Pile subPile = new Pile(deal(numCards));
		
		if (subPile.canPickUp()) {
			deck.get(deck.size()).flipOver();
			layout();
			return subPile;
		} else {
			super.add(subPile);
			return null;
		}
	}

	
	/**
	 * Picks up a pile containing the given card and all cards preceding it
	 * @param card the card to pick up
	 * @return
	 */
	public Pile pickUp(Card card) {
		int index = deck.indexOf(card);
		if (index == -1) return null;
		return pickUp(index + 1);
	}


	/**
	 * Returns whether or not the pile can be legally picked up
	 * @return true if the pile can be picked up or false otherwise
	 */
	public boolean canPickUp() {
		return true;
	}
	
	/**
	 * Adds a card on top
	 * @param card the card to add on top
	 */
	public void add(GCard card) {
	    card.sc
		super.add(card);
		layout();
	}

	/**
	 * Adds a pile on top
	 * @param pile the pile to add on top
	 */
	public void add(Pile pile) {
		super.addAll(pile);
		layout();
	}

	/**
	 * Returns the card at the given point on the canvas
	 * @param x the x coordinate of the point
	 * @param y the y coordinate of the point
	 * @return the Card at the coordinate or null if no card exists
	 */
	public GCard getCard(double x, double y) {
		GObject obj = getElementAt(getLocalPoint(x, y));
		return obj instanceof GCard? (GCard)obj : null;
	}
	
	/**
	 * Flips the top card on the pile
	 */
	public void flipTopCard() {
		if (deck.isEmpty()) return;
		GCard top = deck.get(deck.size()-1);
		if (!top.isFaceUp()) {
			top.turnFaceUp();
		}
	}
	
	
	/**
	 * Modifies the layout to represent the current state of the pile
	 */
	private void layout() {
		emptyRect.setVisible(deck.isEmpty());
		
		for (int i = 0; i < deck.size(); i++) {
			GCard c = deck.get(i);
			c.setLocation(0.0, i * OFFSET * scale);
		}
	}
	
	@Override
	public void scale(double x, double y) {
	    super.scale(x, x);
	    scale *= x;
	    
	}

}
