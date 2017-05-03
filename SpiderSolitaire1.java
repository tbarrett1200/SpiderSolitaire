import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import org.omg.Messaging.SyncScopeHelper;

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
public class SpiderSolitaire1 extends GraphicsProgram {

    private static final int INITIAL_WIDTH = 1000;
    private static final int INITIAL_HEIGHT = 600;

    private static final int PILE_COUNT = 10;
    private static final int GAP_COUNT = PILE_COUNT + 1;

    private static final double PILE_WIDTH = GCard.cardWidth();
    private static final double GAP_WIDTH = (INITIAL_WIDTH - PILE_WIDTH * PILE_COUNT) / GAP_COUNT;

    private Pack pack;
    private Difficulty difficulty;
    private Pile[] piles = new Pile[PILE_COUNT];
    private GDeck decks[] = new GDeck[5];

    private JLabel statusMessage;
    private JButton newGameButton;
    private JComboBox<Difficulty> difficultyComboBox;

    //public static void main(String[] args) {
	//new SpiderSolitaire().start(args);
    //}

    /**
     * Initializes the deck on the canvas and sets up event handling.
     */
    @Override
    public void init() {
	setSize(INITIAL_WIDTH, INITIAL_HEIGHT);
	setBackground(Color.GREEN.darker().darker());

	

	newGameButton.addActionListener(event -> startNewGame());

    }

    public void startNewGame() {
	removeAll();

	difficulty = (Difficulty) difficultyComboBox.getSelectedItem();
	pack = new Pack(difficulty);
	pack.shuffle();

	for (int i = 0; i < PILE_COUNT; i++) {
	    int numCards = i < 4 ? 5 : 4;
	    Pile p = new Pile(pack.deal(numCards));
	    piles[i] = p;
	    p.flipTopCard();
	    add(p, (i + 1) * GAP_WIDTH + i * PILE_WIDTH, GAP_WIDTH);
	}

	for (int i = 0; i < 5; i++) {
	    decks[i] = new GDeck(pack.deal(10));
	    GDeck deck = decks[i];
	    deck.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
		    for (int i = 4; i >= 0; i--) {
			GDeck deck = decks[i];
			if (!deck.isEmpty()) {
			    for (Pile pile : piles)
				pile.add(deck.deal().flipOver());
			    break;
			}
		    }
		}
	    });
	    add(deck, getWidth() - GCard.cardWidth() - GCard.cardWidth() * 0.5 * i - GAP_WIDTH,
		    getHeight() - GCard.cardHeight() - GAP_WIDTH);
	}

	addMouseListeners();
    }
}
