import java.util.ArrayList;
import java.util.List;

import acm.graphics.GCompound;

/*
 * File: GPack.java
 * Author: Thomas Barrett
 * Created: May 5, 2017
 */

public class GPack extends GCompound {

    List<GDeck> decks = new ArrayList<GDeck>();
    
    public GPack(Pack pack) {
	addDecks(pack);
    }
    
    private void addDecks(Pack pack) {
	for(int i = 0 ; i < 5; i++) {
	    GDeck deck = new GDeck(pack.deal(10));
	    decks.add(deck);
	    add(deck, GCard.cardWidth() * 0.5 * i, 0);
	}
    }
    
    public GCard dealCard() {
	GCard card = decks.get(decks.size()-1).deal();
	layout();
	return card;
    }
    
    private void layout() {
	if (decks.get(decks.size()-1).isEmpty()) {
	    decks.remove(decks.size()-1);
	}
    }
}
