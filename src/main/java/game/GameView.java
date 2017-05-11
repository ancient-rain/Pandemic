package game;

import static constants.Constants.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cards.CardView;
import characters.CharacterModel;
import characters.CharacterView;
import city.CityController;
import city.CityView;
import diseases.DiseaseView;

public class GameView extends JFrame {

	GameController controller;
	GameModel model;
	BorderLayout layout;
	JLabel background;
	JPanel gameInfoPanel, playerInfoPanel, playerActionPanel, mapPanel;
	List<CharacterModel> players;

	public GameView(GameController controller) {
		this.controller = controller;
		this.model = controller.getGameModel();
		init();
	}
	
	private void init() {
		this.layout = new BorderLayout();
		
		this.background = new JLabel();
		
		this.gameInfoPanel = new JPanel();
		this.playerInfoPanel = new JPanel();
		this.playerActionPanel = new JPanel();
		this.mapPanel = new JPanel();
		
		this.players = this.model.getCharacters();
	}
	
	public void viewGame() {
		drawGameInfo();
		drawPlayerInfo();
		drawPlayerActions();
		drawMap();
		updateFrame();
	}
	
	private void drawGameInfo() {
		CardView playerDeck = new CardView(true, true);
		DiseaseView cureMarkers = new DiseaseView();
		GameInfoView diseaseInfo = new GameInfoView();
		CardView infectionDeck = new CardView(false, false);
		
		infectionDeck.drawPanel();
		cureMarkers.drawPanel();
		diseaseInfo.drawPanel();	
		playerDeck.drawPanel();
		
		this.gameInfoPanel.add(playerDeck);
		this.gameInfoPanel.add(cureMarkers);
		this.gameInfoPanel.add(diseaseInfo);
		this.gameInfoPanel.add(infectionDeck);
	}
	
	private void drawPlayerInfo() {
		BoxLayout playerInfoLayout = new BoxLayout(playerInfoPanel, BoxLayout.Y_AXIS);
		
		this.playerInfoPanel.setLayout(playerInfoLayout);
				
		for (CharacterModel character : this.players) {
			CharacterView view = new CharacterView(character);
			
			view.drawPanel();
			
			this.playerInfoPanel.add(view);
		}
		
	}
	
	private void drawPlayerActions() {
		FlowLayout actionLayout = new FlowLayout(FlowLayout.LEFT, OFFSET_15, OFFSET_20);
		JButton moveButton = new JButton(MOVE_BUTTON);
		JButton treatButton = new JButton(TREAT_BUTTON);
		JButton cureButton = new JButton(CURE_BUTTON);
		JButton buildButton = new JButton(BUILD_BUTTON);
		JButton shareButton = new JButton(SHARE_BUTTON);
		JButton passButton = new JButton(PASS_BUTTON);
		JLabel spacer = new JLabel();
		JLabel selectedCityOutline = new JLabel();
		
		spacer.setPreferredSize(SPACER);
		spacer.setBorder(BorderFactory.createLineBorder(CUSTOM_GRAY_1));
		spacer.setOpaque(true);
		
		selectedCityOutline.setText(NO_SELECTED_CITY);
		selectedCityOutline.setHorizontalAlignment(JLabel.CENTER);
		selectedCityOutline.setPreferredSize(SELECTED_CITY_SIZE);
		
		addActionListeners(moveButton, treatButton, cureButton, buildButton, shareButton, passButton);
		
		this.playerActionPanel.setLayout(actionLayout);
		this.playerActionPanel.add(spacer);
		this.playerActionPanel.add(selectedCityOutline);
		this.playerActionPanel.add(moveButton);
		this.playerActionPanel.add(treatButton);
		this.playerActionPanel.add(cureButton);
		this.playerActionPanel.add(buildButton);
		this.playerActionPanel.add(shareButton);
		this.playerActionPanel.add(passButton);
	}
	
	private void addActionListeners(JButton moveButton, JButton treatButton, JButton cureButton, JButton buildButton,
			JButton shareButton, JButton passButton) {
		moveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// write method to click a city
			}
		});
		
		treatButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		buildButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		shareButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		passButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		treatButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
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
			File file = new File(filepath);
			image = ImageIO.read(file);
		} catch (IOException e) {
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
	
	public void paint(Graphics gr) {
		super.paint(gr);
		paintBoard(gr);
	}
	
	private void paintBoard(Graphics gr) {
		paintCities(gr);
		paintPlayerHands(gr);
		paintResearchStations(gr);
		paintInfections(gr);
		paintGameCounters(gr);
		paintPlayerHands(gr);
		paintTurnPanel(gr);
	}

	private void paintResearchStations(Graphics gr) {
		// TODO Auto-generated method stub
		
	}

	private void paintInfections(Graphics gr) {
		// TODO Auto-generated method stub
		
	}

	private void paintGameCounters(Graphics gr) {
		// TODO Auto-generated method stub
		
	}

	private void paintPlayerHands(Graphics gr) {
		// TODO Auto-generated method stub
		
	}

	private void paintTurnPanel(Graphics gr) {
		// TODO Auto-generated method stub
		
	}

	private void paintCities(Graphics gr) {
		CityController cityController = this.controller.getCityController();
		CityView cities = new CityView(cityController);
		cities.paintCities(gr);
	}
}
