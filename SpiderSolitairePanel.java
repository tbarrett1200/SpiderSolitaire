import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import acm.graphics.GCanvas;

/*
 * File: SpiderSolitairePanel.java
 * Author: Thomas Barrett
 * Created: May 3, 2017
 */

@SuppressWarnings("serial")
public class SpiderSolitairePanel extends GCanvas {

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
    
    public SpiderSolitairePanel() {
	setPreferredSize(new Dimension(INITIAL_WIDTH,INITIAL_HEIGHT));
	setBackground(Color.GREEN.darker());
    }
    
    public void startNewGame() {
	removeAll();

	difficulty = Difficulty.INTERMEDIATE;
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

    }
}
