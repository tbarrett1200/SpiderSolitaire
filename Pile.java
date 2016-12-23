import acm.graphics.GCompound;
import acm.graphics.GObject;
import acm.graphics.GRect;

/**
 * Models a pile of in spider solitaire
 * 
 * @author Thomas Barrett
 */
@SuppressWarnings("serial")
public class Pile extends GCompound {

	private Deck cards;

	private final GRect emptyRect;
	
	private static final double OFFSET_RATIO = 1 / 5.0;

	/**
	 * Creates a new pile with the given deck of cards
	 * 
	 * @param cards
	 *            the deck of cards to create the pile with
	 */
	public Pile(Deck cards) {
		this.cards = cards;
		for (Card c : cards) {
			add((GCard) c);
		}
		emptyRect = emptyRect();
		add(emptyRect);
		layout();
	}

	private GRect emptyRect() {
		GRect rect = new GRect(GCard.cardWidth(), GCard.cardHeight());
		return rect;
	}
	/**
	 * Returns that deck of cards
	 * 
	 * @return the cards
	 */
	public Deck getCards() {
		return cards;
	}

	/**
	 * Picks up a pile containing the given number of cards
	 * 
	 * @param numCards
	 *            the amount of cards to remove
	 * @return a new pile
	 */
	private Pile pickUp(int numCards) {
		if (cards.get(numCards - 1).isFaceUp()) {
			Deck subDeck = cards.deal(numCards);
			layout();
			return new Pile(subDeck);
		}
		return null;
	}

	
	/**
	 * Picks up a pile containing the given card and all cards preceding it
	 * 
	 * @param card
	 *            the card to pick up
	 * @return
	 */
	public Pile pickUp(Card card) {
		if (!canPickUp(card)) return null;
		int index = cards.indexOf(card);
		if (index == -1)
			return null;
		return pickUp(index + 1);
	}

	public void putDown(GCard card) {
		cards.add(0, card);
		add(card);
		layout();
	}


	/**
	 * Adds a pile on top
	 * 
	 * @param pile
	 */
	public void putDown(Pile pile) {
		cards.addAll(0, pile.getCards());
		for (Card c : pile.getCards()) {
			add((GCard) c);
		}
		layout();
	}

	/**
	 * Returns the card at the given point on the canvas
	 * 
	 * @param x
	 *            the x coordinate of the point
	 * @param y
	 *            the y coordinate of the point
	 * @return the Card at the coordinate or null if no card exists
	 */
	public Card getCard(double x, double y) {
		GObject obj = getElementAt(getLocalPoint(x, y));
		if (obj instanceof Card) {
			return (Card)obj;
		} else {
			return null;
		}
	}

	public boolean canPickUp(Card card) {
		if (card == null) {
			System.out.println("NULL");
			return false;
		}
		
		Suit suit = cards.get(0).getSuit();
		Rank rank = cards.get(0).getRank();
		
		for(int i=1; i<=cards.indexOf(card); i++) {
			Card c = cards.get(i);
			if (!c.isFaceUp()) {
				System.out.println("NOT FACE UP");
				return false;
			}
			if (c.getSuit() != suit) {
				System.out.println("WRONG SUIT");
				return false;
			}
			
			int rank1 = getRankOrdering(rank);
			int rank2 = getRankOrdering(c.getRank());
			int diff = rank2-rank1;
			
			if (diff != 1) {
				System.out.println("NOT IN ORDER");
				System.out.println("1:" + rank1 + " 2:" + rank2);
				return false;
			}
			rank = c.getRank();
		}
		return true;
	}
	
	public boolean canPutDown(Pile p) {
		if (cards.isEmpty()) return true;
		if (!cards.get(0).isFaceUp()) return false;
		
		Deck d = p.getCards();
		Card c = d.get(d.size()-1);
		int rank1 = getRankOrdering(c.getRank());
		int rank2 = getRankOrdering(cards.get(0).getRank());
		int diff = rank2-rank1;
		
		if (diff!=1) {
			return false;
		}
		
		return true;
	}
	
	private int getRankOrdering(Rank a) {
		switch (a) {
		case ACE:
			return 0;
		case DEUCE:
			return 1;
		case THREE:
			return 2;
		case FOUR:
			return 3;
		case FIVE:
			return 4;
		case SIX:
			return 5;
		case SEVEN:
			return 6;
		case EIGHT:
			return 7;
		case NINE:
			return 8;
		case TEN:
			return 9;
		case JACK:
			return 10;
		case QUEEN:
			return 11;
		default:
			return 12;
		
		}
	}

	/**
	 * Flips the top card on the pile
	 */
	public void flipTopCard() {
		if (cards.isEmpty()) return;
		GCard top = (GCard) cards.get(0);
		if (!top.isFaceUp()) {
			top.turnFaceUp();
		}
	}

	private void layout() {
		if (cards.isEmpty()) {
			emptyRect.setVisible(true);
		} else {
			emptyRect.setVisible(false);
		}
		
		double offset = OFFSET_RATIO * GCard.cardHeight();
		for (int i = 0; i < cards.size(); i++) {
			GCard c = (GCard) cards.get(cards.size() - 1 - i);
			c.sendToFront();
			c.setLocation(0.0, i * offset);
		}
	}

}
