
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
public class SpiderSolitaire extends JFrame implements MessageDisplayable {

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
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);

		newGameButton.addActionListener(event -> newGame());
	}

	/* Sets up the panel to the north of the JFrame */
	private void setupNorth() {
		north = new JPanel();
		statusMessage = new JLabel("Welcome to Spider Solitaire");
		north.add(statusMessage);
	}

	/* Sets up the panel to the south of the JFrame */
	private void setupSouth() {
		south = new JPanel();
		newGameButton = new JButton("New Game");
		difficultyComboBox = new JComboBox<Difficulty>(Difficulty.values());
		difficultyComboBox.setSelectedItem(Difficulty.INTERMEDIATE);
		south.add(newGameButton);
		south.add(difficultyComboBox);
	}

	/* Sets up the panel that contains the game */
	private void setupGame() {
		game = new SpiderSolitairePanel(Difficulty.INTERMEDIATE, this);
	}

	/* Replaces the game with a new game*/
	private void newGame() {
		remove(game);
		game = new SpiderSolitairePanel((Difficulty)difficultyComboBox.getSelectedItem(), this);
		add(game);
		validate();
	}

	@Override
	public void displayMessage(String s) {
		statusMessage.setText(s);
	}

	public static void main(String[] args) {
		new SpiderSolitaire();
	}

}
