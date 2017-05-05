import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * File: SpiderSolitaire.java
 * Author: Thomas Barrett
 * Created: May 3, 2017
 */

@SuppressWarnings("serial")
public class SpiderSolitaire extends JFrame {
    
    private JLabel statusMessage;
    private JButton newGameButton;
    private JComboBox<Difficulty> difficultyComboBox;
    
    private JPanel north;
    private JPanel south;
    private SpiderSolitairePanel game;
    
    public SpiderSolitaire() {
	setTitle("Spider Solitaire");
	
	setupGame();
	setupNorth();
	setupSouth();

	add(game);
	add(north, BorderLayout.NORTH);
	add(south, BorderLayout.SOUTH);
	
	pack();
	setLocationRelativeTo(null);
	setVisible(true);
	
	addListeners();
    }
    

    private void setupNorth() {
	north = new JPanel();
	statusMessage = new JLabel("Welcome to Spider Solitaire");
	north.add(statusMessage);
    }

    private void setupSouth() {
	south = new JPanel();
	newGameButton = new JButton("New Game");
	difficultyComboBox = new JComboBox<Difficulty>(Difficulty.values());
	south.add(newGameButton);
	south.add(difficultyComboBox);
    }

    private void setupGame() {
	game = new SpiderSolitairePanel(Difficulty.BEGINNER);
    }
    
    private void addListeners() {
    }
    
    public static void main(String[] args) {
	new SpiderSolitaire();
    }
}
