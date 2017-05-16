
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import acm.graphics.GCanvas;
import acm.graphics.GObject;

@SuppressWarnings("serial")
public class SpiderSolitairePanel extends GCanvas implements CompletedPileDisplayable {

	private static final int INITIAL_WIDTH = 1000;
	private static final int INITIAL_HEIGHT = 600;

	private static final int PILE_COUNT = 10;
	private static final int GAP_COUNT = PILE_COUNT + 1;

	private static final double PILE_WIDTH = GCard.cardWidth();
	private static final double GAP_WIDTH = (INITIAL_WIDTH - PILE_WIDTH * PILE_COUNT) / GAP_COUNT;

	private Selection selection;
	private Pile[] piles = new Pile[PILE_COUNT];
	private GPack pack;
	private GCompletePile completePile;
	
	/**
	 * Constructor
	 * @param difficulty the difficulty level of the game
	 * @param message displays user messages
	 */
	public SpiderSolitairePanel(Difficulty difficulty, MessageDisplayable message) {
		setPreferredSize(new Dimension(INITIAL_WIDTH, INITIAL_HEIGHT));
		setBackground(Color.GREEN.darker());
		
		double width = getPreferredSize().getWidth();
		double height = getPreferredSize().getHeight();
		
		Pack pack = new Pack(difficulty);
		pack.shuffle();
			
		this.pack = new GPack(pack, piles, message);
		add(this.pack, width - 20, height - this.pack.getHeight() - 20);
		
		completePile = new GCompletePile();
		add(completePile, 20, height - completePile.getHeight() - 20);
		
		
		for (int i = 0; i < PILE_COUNT; i++) {
			piles[i] = new Pile(message, this);
		}
		for (int i = 0; i < 44; i++) {
			piles[i % PILE_COUNT].add(pack.deal());
		}
		for (int i = 0; i < PILE_COUNT; i++) {
			piles[i].flipTopCard();
			add(piles[i], (i + 1) * GAP_WIDTH + i * PILE_WIDTH, GAP_WIDTH);
		}
		
		addMouseListener(mouse);
		addMouseMotionListener(mouseMotion);
	}

	/* Mouse Event Listener */
	private MouseListener mouse = new MouseAdapter() {
		@Override
		public void mousePressed(MouseEvent e) {
			GObject obj = getElementAt(e.getX(), e.getY());
			if (obj instanceof Pile) {
				Card c = ((Pile)obj).getCard(e.getX(), e.getY());
				selection = new Selection(SpiderSolitairePanel.this, ((Pile)obj).pickUp(c), ((Pile)obj), e.getX(), e.getY());
			} else if (obj == pack) {
				pack.dealPack();
			}
		};
		
		@Override
		public void mouseReleased(MouseEvent e) {
			if (selection != null && selection.isActive()) selection.drop(e.getX(), e.getY());
		};
	};
	
	/* Mouse Motion Event Listener */
	private MouseMotionListener mouseMotion = new MouseMotionAdapter() {
		@Override
		public void mouseDragged(MouseEvent e) {
			if (selection != null) selection.drag(e.getX(), e.getY());
		};
	};
	
	@Override
	public void displayPile(GCard king) {
		completePile.add(king);
	}

}
