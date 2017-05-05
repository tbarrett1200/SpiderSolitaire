import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.omg.Messaging.SyncScopeHelper;

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

    private Difficulty difficulty;
    private Pile[] piles = new Pile[PILE_COUNT];
    private GPack pack;
    
    public SpiderSolitairePanel(Difficulty d) {
	setPreferredSize(new Dimension(INITIAL_WIDTH,INITIAL_HEIGHT));
	setBackground(Color.GREEN.darker());
	
	this.difficulty = d;
	Pack pack = new Pack(difficulty);
	pack.shuffle();
	
	createPiles();
	dealPiles(pack);
	addPiles();
	addDeck(pack);
	
	addListeners();
    }
    

    private void createPiles() {
	for (int i = 0; i < PILE_COUNT; i++) {
	    piles[i] = new Pile();
	}
    }
    
    private void dealPiles(Pack pack) {
	for (int i = 0; i < 44; i++) {
	    piles[i % PILE_COUNT].add(pack.deal());
	}
    }
    
    private void addPiles() {
	for (int i = 0; i < PILE_COUNT; i++) {
	    piles[i].flipTopCard();
	    add(piles[i], (i + 1) * GAP_WIDTH + i * PILE_WIDTH, GAP_WIDTH);
	}
    }
    
    private void addDeck(Pack pack) {
	this.pack = new GPack(pack);
	add(this.pack, 50, 450);
    }

    private void addListeners() {
	addPackListeners();
	for (Pile p: piles) {
	    addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
		    for(int i = 0; i < PILE_COUNT; i++) {
			Card c = p.getCard(e.getX(), e.getY());
			p.pickUp(c);
		    }
		}
	    });
	}
    }
    
    private void addPackListeners() {
	pack.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
	        for(int i = 0; i < PILE_COUNT; i++) {
	            piles[i].add(pack.dealCard());
	        }
	        super.mouseClicked(e);
	    }
	});
    }
}
