import java.awt.Color;
import java.awt.Dimension;

import acm.graphics.GCanvas;

/*
 * File: SpiderSolitairePanel.java
 * Author: Thomas Barrett
 * Created: May 3, 2017
 */

@SuppressWarnings("serial")
public class SpiderSolitairePanel extends GCanvas {

    public SpiderSolitairePanel() {
	setPreferredSize(new Dimension(800,600));
	setBackground(Color.GREEN.darker());
    }
}
