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
	
	private MessageDisplayable error;
	private CompletedPileDisplayable complete;

	/**
	 * Creates a new pile with the given deck of cards
	 * @param cards the deck of cards to create the pile with
	 * @param error displays error messages
	 * @param complete handles complete piles
	 */
	public Pile(Deck<GCard> cards, MessageDisplayable error, CompletedPileDisplayable complete) {
		super(cards);
		this.error = error;
		this.complete = complete;
		addEmptyRect();
		layout();
	}

	/**
	 * Creates a new empty pile
	 * @param error displays error messages
	 * @param complete handles complete piles
	 */
	public Pile(MessageDisplayable error, CompletedPileDisplayable complete) {
	    super(new Deck<GCard>());
	    this.error = error;
		this.complete = complete;
	    addEmptyRect();
	    layout();
	}
	

	/**
	 * Picks up a pile containing the given number of cards
	 * @param numCards the amount of cards to remove
	 * @return a new pile
	 */
	public Pile pickUp(int numCards) {
		Pile subPile = new Pile(deal(numCards), error, complete);
		if (subPile.canPickUp()) {
			return subPile;
		} else {
			addAll(subPile);
			return null;
		}
	}

	
	/**
	 * Picks up a pile containing the given card and all cards preceding it
	 * @param card the card to pick up
	 * @return
	 */
	public Pile pickUp(Card card) {
		if (card == null) return null;
		int index = deck.indexOf(card);
		if (index == -1) return null;
		return pickUp(deck.size()-index);
	}


	/**
	 * Returns whether or not the pile can be legally picked up
	 * @return true if the pile can be picked up or false otherwise
	 */
	public boolean canPickUp() {
		//Face Up
		for (GCard card: deck) {
			if (!card.isFaceUp()) {
				error.displayMessage("You can only pick up face up cards");
				return false;
			}
		}
		//Ordered
		if (!isOrdered()) {
			error.displayMessage("Cards must be in order to pick up");
			return false;
		}
		return true;
	}
	
	

	/**
	 * Adds a pile on top
	 * @param pile the pile to add on top
	 */
	public boolean putDown(Pile pile) {
		if (canPutDown(pile)) {
			addAll(pile);
			displayCompletedPile(completedPile());
			return true;
		} return false;
	}

	private void displayCompletedPile(GCard king) {
		if (king != null) {
			flipTopCard();
			complete.displayPile(king);
		}
	}

	/**
	 * Checks for a completed pile
	 * @return the completed pile or null if one does not exist
	 */
	private GCard completedPile() {
		
		Card king = null;
		
		//finds last face up king in the deck
		for (GCard card: deck) {
			if (card.isFaceUp() && card.getRank().equals(Rank.KING)) {
				king = card;
			}
		}
		
		//no face up king found
		if (king == null) {
			return null;
		}
		
		//the sub-pile starting with the last face-up king
		Pile p = pickUp(king);
		
		//if the sup-pile ranges from king to ace and is in order
		if (p.isOrdered() && p.deck.get(p.deck.size()-1).getRank().equals(Rank.ACE)) {
			System.out.println("Pile is complete");
			return p.deck.get(0);
		} else {
			addAll(p);
			return null;
		}	
	}

	
	public boolean canPutDown(Pile pile) {
		//Only allows King to be placed on an empty pile
		if (isEmpty()) return pile.deck.get(0).getRank().equals(Rank.KING);
		
		//Cards must be in descending order
		int last = deck.get(deck.size()-1).getRank().ordinal();
		int first = pile.deck.get(0).getRank().ordinal();
		
		if (last - first != 1) {
			return false;
		}
		return true;
	}
	
	/**
	 * Returns the card at the given point on the canvas
	 * @param x the x coordinate of the point
	 * @param y the y coordinate of the point
	 * @return the Card at the coordinate or null if no card exists
	 */
	public GCard getCard(double x, double y) {
		GObject obj = getElementAt(getLocalPoint(x, y));
		if (obj instanceof GCard) {
			return (GCard)obj;
		} else return null;
	}
	
	public void flipTopCard() {
		if (deck.isEmpty()) return;
		GCard top = deck.get(deck.size()-1);
		top.turnFaceUp();
	}

	public boolean isOrdered() {
		int previous = deck.get(0).getRank().ordinal();
		for (int i = 1; i < deck.size(); i++) {
			int ordinal = deck.get(i).getRank().ordinal();
			if (ordinal != previous - 1) return false;
			previous = ordinal;
		}
		return true;
	}
	
	//Adds empty rect image
	private void addEmptyRect() {
	    emptyRect = new GRect(GCard.cardWidth(), GCard.cardHeight());
	    add(emptyRect);
	}
	
	//Updates the layout to represent the current state of the pile
	protected void layout() {
		
		if (emptyRect != null) emptyRect.setVisible(deck.isEmpty());
		
		for (int i = 0; i < deck.size(); i++) {
			GCard c = deck.get(i);
			c.setLocation(0.0, i * OFFSET * scale);
		}
	}
	

}
