

@SuppressWarnings("serial")
public class GCompletePile extends GDeck {

	@Override
	protected void layout() {
		for (int i = 0; i < deck.size(); i++) {
			GCard king = deck.get(i);
			if (!king.isFaceUp()) king.flipOver();
			king.setLocation(i * 0.5 * GCard.cardWidth(), 0);
		}
	}		
}
