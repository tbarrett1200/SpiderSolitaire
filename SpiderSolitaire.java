import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import acm.graphics.GObject;
import acm.graphics.GScalable;
import acm.program.GraphicsProgram;

/**
 * The rules for Spider Solitaire card games.
 * 
 * The Pack Each game involves 104 cards and eight suits (some duplicated).
 * Depending on the difficulty, the pack may include: * Beginner - one suit (8
 * decks but using only the spades) * Intermediate - two suits (4 decks but only
 * using the spades and hearts) * Advanced - four suits (2 decks using all four
 * suits)
 * 
 * Object of the Game The goal is to assemble 13 cards of a suit, in ascending
 * sequence from ace through king, on top of a pile. Whenever a full suit of 13
 * cards is so assembled, it is lifted off and discarded from the game. The game
 * is won if all eight suits are played out.
 * 
 * The Deal Ten piles of five cards each are dealt by rows. The first four piles
 * have an additional sixth card dealt -- making a total of 54 initial cards
 * dealt. All cards in each pile are face down, except for the top card, which
 * is face up. The remaining fifty cards are held in reserve. Under suitable
 * conditions (see below), the player can dealer another row of ten cards from
 * this reserve.
 * 
 * The Play The top card of a pile may be moved, together with all face-up cards
 * below it that follow in ascending suit and sequence.
 * 
 * A sequence of available cards may be broken at any point by leaving some
 * cards behind. Example: If a pile from top down shows 4, 5, 6, 7, either the
 * first one, two, or three cards may be moved as a unit, but the 7 may not be
 * moved until the covering three cards are removed. When all face-up cards on a
 * pile are removed, the next card below is turned face up and becomes
 * available.
 * 
 * A movable unit of cards may be placed either in a space or on a card of the
 * next-higher rank to the bottom card of the unit, regardless of color or suit.
 * Example: If the bottom card of a unit is the J, it may be moved onto any one
 * of the four queens.
 * 
 * A king can be moved only onto a space. Alternatively, the spaces may be
 * filled with any movable unit.
 * 
 * When all possible or desired moves come to a standstill, the player deals
 * another row of ten cards face up. However, before such a deal may be made,
 * all spaces must be filled. The final deal consists of only four cards, which
 * are placed on the first four piles.
 * 
 * @author markjones
 *
 */
@SuppressWarnings("serial")
public class SpiderSolitaire extends GraphicsProgram {

	private static final int INITIAL_WIDTH = 1000;
	private static final int INITIAL_HEIGHT = 600;

	private static final int PILE_COUNT = 10;
	private static final int GAP_COUNT = PILE_COUNT + 1;

	private static final double PILE_WIDTH = GCard.cardWidth();
	private static final double GAP_WIDTH = (INITIAL_WIDTH - PILE_WIDTH * PILE_COUNT) / GAP_COUNT;

	private Pile selected;
	private Pile origin;
	private Pack pack;
	private Difficulty difficulty;
	private Pile[] piles = new Pile[PILE_COUNT];
	
	public static void main(String[] args) {
		new SpiderSolitaire().start(args);
	}

	/**
	 * Initializes the deck on the canvas and sets up event handling.
	 */
	@Override
	public void init() {
		setSize(INITIAL_WIDTH, INITIAL_HEIGHT);
		setBackground(Color.GREEN.darker().darker());

		difficulty = Difficulty.BEGINNER;
		pack = new Pack(difficulty);
		pack.shuffle();

		for (int i = 0; i < PILE_COUNT; i++) {
			int numCards = i < 4 ? 5 : 4;
			Pile p = new Pile(pack.deal(numCards));
			piles[i]=p;
			p.flipTopCard();
			add(p, (i + 1) * GAP_WIDTH + i * PILE_WIDTH, GAP_WIDTH);
		}
		addDrawButton();
		addMouseListeners();
		catchResizeEvents();
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (selected != null) {
			selected.setLocation(e.getX(), e.getY());
		}
		super.mouseMoved(e);
	}

	private void addDrawButton() {
		JButton button = new JButton("Draw");
		button.addActionListener((ActionEvent e)-> {
			for (Pile p: piles) {
				GCard card = (GCard)pack.deal();
				if (card!=null) {
				p.putDown((GCard)pack.deal().flipOver());
				}
			}
		});
		add(button, SOUTH);

	}
	private void returnSelected() {
		origin.putDown(selected);
		origin = null;
		selected = null;
	}

	private void addSelected(Pile p) {
		if (p.canPutDown(selected)) {
			p.putDown(selected);
		} else {
			returnSelected();
		}
		origin = null;
		selected = null;
	}

	private boolean hasPileSelected() {
		return selected != null;
	}
	
	private void selectPile(Pile origin, Pile selected) {
		this.selected = selected;
		this.origin = origin;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {

		//removes the selected pile (the one following the mouse) to avoid interference with getElement
		//otherwise getElementAt would always return the selected pile
		if (hasPileSelected()) {
			remove(selected);
		}

		//finds the pile that was clicked on
		Pile pile = (Pile) getElementAt(e.getX(), e.getY());

		if (hasPileSelected()) {
			if (pile == null) {
				returnSelected();
			} else {
				addSelected(pile);
			}
		} else {
			if (pile == null) {
				return;
			} else {
				Pile subPile = pile.pickUp(pile.getCard(e.getX(), e.getY()));
				if (subPile == null) {
					pile.flipTopCard();
				} else {
					selectPile(pile, subPile);
					add(selected, e.getX(), e.getY());
				}
			}
		}
	}

	/**
	 * Sets up the handler for resize events. This handler catches resize
	 * events, rescales the (GScalable) objects, and adjusts the locations of
	 * all GObjects. It does not adjust the font size for GLabels and such.
	 */
	private double wid, ht; // width and height of the canvas (needed for
	// resizing)

	private void catchResizeEvents() {
		wid = getWidth();
		ht = getHeight();

		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				double scaleX = getWidth() / wid, scaleY = getHeight() / ht;
				for (int i = 0; i < getElementCount(); i++) {
					Object obj = getElement(i);
					if (obj instanceof GObject) {
						if (obj instanceof GScalable) {
							((GScalable) obj).scale(scaleX, scaleY);
						}
						((GObject) obj).setLocation(((GObject) obj).getX() * scaleX, ((GObject) obj).getY() * scaleY);
					}
				}
				wid = getWidth();
				ht = getHeight();
			}
		});
	}
}
