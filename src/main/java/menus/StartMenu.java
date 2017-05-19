package menus;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import static constants.Game.*;

public class StartMenu extends JFrame implements ActionListener {
	private JLabel background, playersLabel, difficultyLabel, confirmLabel;
	private JPanel startPanel, playerPanel, difficultyPanel, confirmPanel;
	private JButton playButton, rulesButton, exitButton, twoPlayerButton, 
		threePlayerButton, fourPlayerButton, playerBackButton, 
		introButton, standardButton, heroicButton, difficultyBackButton, 
		startButton, confirmBackButton;
	private JPanel panel;
	protected boolean gameSet;
	protected int numPlayers;
	protected int difficulty;
	
	public StartMenu() {
		this.init();
	}
	
	public void showOptions() {
		JPanel panel = new JPanel();
		
		try {
			this.background = setBackground();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.panel.add(this.background);
		
		selectStartingOptions();
		selectNumberOfPlayers();
		selectDifficulty();
		confirm();
	}
	
	private void selectStartingOptions() {
		String[] options = {"Play", "Rules", "Exit"};
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
		
	}
	
	private void selectDifficulty() {
		
	}
	
	private void confirm() {
		
	}
	
	public void viewMenu() {			
		this.addActionListeners();
		this.updateStartPanel();
		this.updatePlayerPanel();
		this.updateDifficultyPanel();
		this.updateConfirmPanel();
		this.updateBackground();
		this.updateFrame();
	}
	
	public JLabel setBackground() throws IOException {	
		JLabel background = new JLabel();
		
		try {
			File image = new File(PANDEMIC_IMG);
			Image backgroundImg = ImageIO.read(image);
			background.setIcon(new ImageIcon(backgroundImg));
			this.add(background);
			background.setLayout(new FlowLayout());
		} catch (IOException e) {
			System.out.println("Error found when starting the game");
			System.exit(0);
			e.printStackTrace();
		}
		
		return background;
	}
	
	public void addActionListeners() {
		this.playButton.addActionListener(this);
		this.rulesButton.addActionListener(this);
		this.exitButton.addActionListener(this);
		this.twoPlayerButton.addActionListener(this);
		this.threePlayerButton.addActionListener(this);
		this.fourPlayerButton.addActionListener(this);
		this.playerBackButton.addActionListener(this);
		this.introButton.addActionListener(this);
		this.standardButton.addActionListener(this);
		this.heroicButton.addActionListener(this);
		this.difficultyBackButton.addActionListener(this);
		this.startButton.addActionListener(this);
		this.confirmBackButton.addActionListener(this);
	}
	
	public void updateStartPanel() {
		this.startPanel.add(this.playButton);
		this.startPanel.add(this.rulesButton);
		this.startPanel.add(this.exitButton);
	}
	
	public void updatePlayerPanel() {
		this.playerPanel.add(this.playersLabel);
		this.playerPanel.add(this.twoPlayerButton);
		this.playerPanel.add(this.threePlayerButton);
		this.playerPanel.add(this.fourPlayerButton);
		this.playerPanel.add(this.playerBackButton);
	}
	
	public void updateDifficultyPanel() {
		this.difficultyPanel.add(this.difficultyLabel);
		this.difficultyPanel.add(this.introButton);
		this.difficultyPanel.add(this.standardButton);
		this.difficultyPanel.add(this.heroicButton);
		this.difficultyPanel.add(this.difficultyBackButton);
	}
	
	public void updateConfirmPanel() {
		this.confirmPanel.add(this.confirmLabel);
		this.confirmPanel.add(this.startButton);
		this.confirmPanel.add(this.confirmBackButton);
	}
	
	public void updateBackground() {
		try {
			this.background = this.setBackground();
			this.background.add(this.startPanel);
			this.background.setLocation(OFFSET_0, OFFSET_0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void updateFrame() {
		this.setTitle("Pandemic");
		this.setSize(START_MENU_SIZE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setContentPane(this.startPanel);
		this.add(this.background);
	}
	
	public void updateConfirmLabel() {
		String currentPlayers =  this.numPlayers + PLAYERS_STRING;
		String currentDifficulty = "";
		
		if (this.difficulty == INTRODUCTORY_DIFFICULTY) {
			currentDifficulty = INTRODUCTORY.toUpperCase() + DIFFICULTY;
		} else if (this.difficulty == STANDARD_DIFFICULTY) {
			currentDifficulty = STANDARD.toUpperCase() + DIFFICULTY;
		} else {
			currentDifficulty = HEROIC.toUpperCase() + DIFFICULTY;
		}
		
		this.confirmLabel.setText(START_GAME_WITH + currentPlayers + AT_STRING + 
				currentDifficulty + "?");
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() instanceof JButton) {
			Object button = event.getSource();
			
			if (button == this.playButton) {
				this.remove(this.startPanel);
				this.setContentPane(this.playerPanel);
			} else if (button == this.rulesButton) {
				try {
					File file = new File(PANDEMIC_RULES);
					Desktop.getDesktop().open(file);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} else if (button == this.exitButton) {
				System.exit(0);
			} else if (button == this.twoPlayerButton) {
				this.numPlayers = TWO_PLAYERS;
				this.remove(this.playerPanel);
				this.setContentPane(this.difficultyPanel);
			} else if (button == this.threePlayerButton) {
				this.numPlayers = THREE_PLAYERS;
				this.remove(this.playerPanel);
				this.setContentPane(this.difficultyPanel);
			} else if (button == this.fourPlayerButton) {
				this.numPlayers = FOUR_PLAYERS;
				this.remove(this.playerPanel);
				this.setContentPane(this.difficultyPanel);
			} else if (button == this.playerBackButton) {
				this.remove(this.playerPanel);
				this.setContentPane(this.startPanel);
			} else if (button == this.introButton) {
				this.difficulty = INTRODUCTORY_DIFFICULTY;
				this.remove(this.difficultyPanel);
				this.updateConfirmLabel();
				this.setContentPane(this.confirmPanel);
			} else if (button == this.standardButton) {
				this.difficulty = STANDARD_DIFFICULTY;
				this.remove(this.difficultyPanel);
				this.updateConfirmLabel();
				this.setContentPane(this.confirmPanel);
			} else if (button == this.heroicButton) {
				this.difficulty = HEROIC_DIFFICULTY;
				this.remove(this.difficultyPanel);
				this.updateConfirmLabel();
				this.setContentPane(this.confirmPanel);
			} else if (button == this.difficultyBackButton) {
				this.remove(this.difficultyPanel);
				this.setContentPane(this.playerPanel);
			} else if (button == this.startButton) {
				this.gameSet = true;
				this.setVisible(false);
			} else if (button == this.confirmBackButton) {
				this.remove(this.confirmPanel);
				this.setContentPane(this.difficultyPanel);
			}    
			
			this.validate();
			this.repaint();
			this.add(this.background);
		}
	}
	
	public int getNumPlayers() {
		return this.numPlayers;
	}
	
	public int getDifficulty() {
		return this.difficulty;
	}
	
	public boolean getGameSet() {
		return this.gameSet;
	}
	
	private void init() {
		this.background = new JLabel();
		this.playersLabel = new JLabel(SELECT_NUM_PLAYERS);
		this.difficultyLabel = new JLabel(SELECT_GAME_DIFFICULTY);
		this.confirmLabel = new JLabel();
		
		this.startPanel = new JPanel();
		this.playerPanel = new JPanel();
		this.difficultyPanel = new JPanel();
		this.confirmPanel = new JPanel();
		this.panel = new JPanel();
		
		this.playButton = new JButton(PLAY);
		this.rulesButton = new JButton(RULES_BUTTON);
		this.exitButton = new JButton(EXIT);
		this.twoPlayerButton = new JButton(TWO_PLAYERS_BUTTON);
		this.threePlayerButton = new JButton(THREE_PLAYERS_BUTTON);
		this.fourPlayerButton = new JButton(FOUR_PLAYERS_BUTTON);
		this.playerBackButton = new JButton(BACK);
		this.introButton = new JButton(INTRODUCTORY_BUTTON);
		this.standardButton = new JButton(STANDARD_BUTTON);
		this.heroicButton = new JButton(HEROIC_BUTTON);
		this.difficultyBackButton = new JButton(BACK);
		this.startButton = new JButton(START_BUTTON);
		this.confirmBackButton = new JButton(BACK);
	}
}
