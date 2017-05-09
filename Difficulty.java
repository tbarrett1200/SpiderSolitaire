/**
 * Models the difficulty of a game
 * @author Thomas Barrett
 */
public enum Difficulty {
	BEGINNER, INTERMEDIATE, ADVANCED;
	
	public String toString() {
	    String name = "" + this.name().charAt(0);
	    name += this.name().substring(1).toLowerCase();
	    return name;
	}
}
