/**
 * Models the suit of a playing card.
 * 
 * @author Thomas Barrett
 */
public enum Suit {
	CLUBS, DIAMONDS, HEARTS, SPADES;

	@Override
	public String toString() {
		return name().toLowerCase();
	}
}
