package game;


import java.awt.BorderLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cards.CardView;
import diseases.DiseaseView;

import static constants.Constants.*;

public class GameView extends JFrame {

	GameModel model;
	BorderLayout layout;
	JLabel background;
	JPanel gameInfoPanel, playerInfoPanel, playerActionPanel, mapPanel;

	public GameView(GameModel model) {
		this.model = model;
		
		init();
	}
	
	private void init() {
		this.layout = new BorderLayout();
		
		this.background = new JLabel();
		
		this.gameInfoPanel = new JPanel();
		this.playerInfoPanel = new JPanel();
		this.playerActionPanel = new JPanel();
		this.mapPanel = new JPanel();
	}
	
	public void viewGame() {
		drawGameInfo();
		drawPlayerInfo();
		drawPlayerActions();
		drawMap();
		updateFrame();
	}
	
	private void drawGameInfo() {
		drawPlayerDeckOutline();
		drawCureOutlineMarkers();
		drawDiseaseInfoCounters();
		drawInfectionDeckOutline();
	}
	
	private void drawPlayerDeckOutline() {
		CardView playerDeck = new CardView(true, true);
		
		playerDeck.drawPanel();
		
		this.gameInfoPanel.add(playerDeck);
	}
	
	private void drawCureOutlineMarkers() {
		DiseaseView cureMarkers = new DiseaseView();
		
		cureMarkers.drawPanel();
		
		this.gameInfoPanel.add(cureMarkers);
	}
	
	private void drawDiseaseInfoCounters() {
		GameInfoView diseaseInfo = new GameInfoView();
		
		diseaseInfo.drawPanel();
		
		this.gameInfoPanel.add(diseaseInfo);
	}
	
	private void drawInfectionDeckOutline() {
		
	}
	
	private void drawPlayerInfo() {
		
	}
	
	private void drawPlayerActions() {
		
	}
	
	private void drawMap() {
		setBackground();
		this.mapPanel.add(this.background);
	}
	
	private void setBackground() {
		ImageIcon backgroundImg = new ImageIcon();
		Image image = setImage(MAP_IMG);
		Image scaledImage = image.getScaledInstance(1730, 860, Image.SCALE_SMOOTH);
		backgroundImg = new ImageIcon(scaledImage);

		this.background.setIcon(backgroundImg);
	}
	
	private Image setImage(String filepath) {
		Image image = null;

		try {
			File file = new File(getClass().getResource(filepath).toURI());
			image = ImageIO.read(file);
		} catch (IOException | URISyntaxException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}

		return image;
	}
	
	private void updateFrame() {
		this.setLayout(this.layout);
		this.setPreferredSize(GAME_BOARD_SIZE);
		this.setResizable(false);
		this.add(this.gameInfoPanel, BorderLayout.NORTH);
		this.add(this.playerInfoPanel, BorderLayout.WEST);
		this.add(this.playerActionPanel, BorderLayout.SOUTH);
		this.add(this.mapPanel, BorderLayout.CENTER);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		this.setTitle("Pandemic: " + numPlayers + " " + PLAYERS_AT 
//				+ getDifficulty() + " " + DIFFICULTY);
		this.pack();
		this.setVisible(true);
	}
}
