/**
 * Models a spider solitaire pack
 * 
 * @author Thomas Barrett
 */
@SuppressWarnings("serial")
public class Pack extends Deck<GCard> {

	/**
	 * Create a pack with the given difficulty
	 * 
	 * @param level
	 *            the difficulty
	 */
	public Pack(Difficulty level) {
		switch (level) {
		case BEGINNER:
			for (int i = 0; i < 8; i++) {
				for (Rank rank : Rank.values()) {
					add(new GCard(rank, Suit.SPADES));
				}
			}
			break;
		case INTERMEDIATE:
			for (int i = 0; i < 4; i++) {
				for (Rank rank : Rank.values()) {
					add(new GCard(rank, Suit.SPADES));
					add(new GCard(rank, Suit.HEARTS));
				}
			}
			break;
		case ADVANCED:
			for (int i = 0; i < 2; i++) {
				for (Rank rank : Rank.values()) {
					for (Suit suit : Suit.values()) {
						add(new GCard(rank, suit));
					}
				}
			}
			break;
		}
	}
}
