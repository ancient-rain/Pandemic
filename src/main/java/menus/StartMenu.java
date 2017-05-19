package menus;

import java.awt.Desktop;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import static constants.Game.*;

public class StartMenu {
	private JLabel background;
	private JPanel panel;
	protected int numPlayers;
	protected int difficulty;
	
	public StartMenu() {
		this.background = new JLabel();
		this.panel = new JPanel();
		this.numPlayers = 0;
		this.difficulty = 0;
	}
	
	public int getNumPlayers() {
		return this.numPlayers;
	}
	
	public int getDifficulty() {
		return this.difficulty;
	}
	
	public void view() {
		try {
			this.viewOptions();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void viewOptions() throws IOException {	
		this.background = setBackground();
		
		this.panel.add(this.background);
		
		selectStartingOptions();
		selectNumberOfPlayers();
		selectDifficulty();
		confirm();
	}
	
	private void selectStartingOptions() {
		String[] options = {PLAY, RULES, EXIT};
		int option = -1;
		
		while (option != 0) {
			option = JOptionPane.showOptionDialog(null, this.panel, "Pandemic",
					JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
			
			if (option == -1 || option == 2) {
				System.exit(0);
			} else if (option == 1) {
				File file = new File(PANDEMIC_RULES);
				try {
					Desktop.getDesktop().open(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void selectNumberOfPlayers() {
		String[] options = {TWO_PLAYERS_TEXT, THREE_PLAYERS_TEXT, FOUR_PLAYERS_TEXT};
		int option = -1;
		
		while (option == -1) {
			option = JOptionPane.showOptionDialog(null, this.panel, SELECT_NUM_PLAYERS,
					JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
			
			if (option == -1) {
				System.exit(0);
			} else if (option == 0) {
				this.numPlayers = TWO_PLAYERS;
			} else if (option == 1) {
				this.numPlayers = THREE_PLAYERS;
			} else {
				this.numPlayers = FOUR_PLAYERS;
			}
		}
	}
	
	private void selectDifficulty() {
		String[] options = {INTRODUCTORY, STANDARD, HEROIC};
		int option = -1;
		
		while (option == -1) {
			option = JOptionPane.showOptionDialog(null, this.panel, SELECT_GAME_DIFFICULTY,
					JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
			
			if (option == -1) {
				System.exit(0);
			} else if (option == 0) {
				this.difficulty = INTRODUCTORY_DIFFICULTY;
			} else if (option == 1) {
				this.difficulty = STANDARD_DIFFICULTY;
			} else {
				this.difficulty = HEROIC_DIFFICULTY;
			}
		}
	}
	
	private void confirm() {
		String title = "";
		String[] options = {PROCEED, EXIT};
		int option = -1;
		
		while (option != 0) {
			title = getConfirmTitle();
			option = JOptionPane.showOptionDialog(null, this.panel, title,
					JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
			
			if (option == -1 || option == 1) {
				System.exit(0);
			}
		}
	}
	
	private JLabel setBackground() throws IOException {	
		try {
			File image = new File(PANDEMIC_IMG);
			Image backgroundImg = ImageIO.read(image);
			this.background.setIcon(new ImageIcon(backgroundImg));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return background;
	}

	private String getConfirmTitle() {
		String title = PROCEED_WITH;
		String currentPlayers =  this.numPlayers + " " + PLAYERS_AT;
		String currentDifficulty = "";

		if (this.difficulty == INTRODUCTORY_DIFFICULTY) {
			currentDifficulty = INTRODUCTORY.toUpperCase() + " " + DIFFICULTY;
		} else if (this.difficulty == STANDARD_DIFFICULTY) {
			currentDifficulty = STANDARD.toUpperCase() + " " + DIFFICULTY;
		} else {
			currentDifficulty = HEROIC.toUpperCase() + " " + DIFFICULTY;
		}
		
		title += currentPlayers;
		title += currentDifficulty;
		
		return title;
	}
}
