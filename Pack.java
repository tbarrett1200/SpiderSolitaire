/**
 * Models a spider solitaire pack
 * @author Thomas Barrett
 */
@SuppressWarnings("serial")
public class Pack extends Deck {

    private Difficulty level;
    
    /**
     * Create a pack with the given difficulty
     * @param level the difficulty
     */
    public Pack(Difficulty level) {
	this.level = level;
    }
}
