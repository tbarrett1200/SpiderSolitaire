

@SuppressWarnings("serial")
public class GPack extends GDeck {

	private Pile[] piles;
	private MessageDisplayable message;
	
	/**
	 * Constructor
	 * @param cards the cards in the GPack
	 * @param piles the piles to be dealt to
	 * @param message displays message to user
	 */
	public GPack(Deck<GCard> cards, Pile[] piles, MessageDisplayable message) {
		super(cards);
		this.piles = piles;
		this.message = message;
	}

	
	/**  Deals new cards to the piles if possible */
	public void dealPack() {
		if (canDeal()) {
			for (Pile p: piles) {
				p.add(deal());
				p.flipTopCard();
			}
		} else {
			message.displayMessage("New cards cannot be dealt while there is an empty pile");
		}
	}
	
	@Override
	protected void layout() {
		for (int i = 0; i < deck.size(); i++) {
			deck.get(i).setLocation(i / 10 * -0.5 * GCard.cardWidth() - GCard.cardWidth(), 0);
		}
	}

	/* checks if new cards can be dealt */
	private boolean canDeal() {
		for (Pile pile: piles) {
			if (pile.isEmpty()) return false;
		}
		return true;
	}
	
}
