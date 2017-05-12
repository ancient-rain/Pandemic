package game;

import static constants.Constants.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cards.CardModel;
import cards.CardView;
import characters.AbstractCharacterController;
import characters.CharacterFrontEndModel;
import characters.CharacterModel;
import characters.CharacterView;
import city.CityController;
import city.CityFrontEndModel;
import city.CityView;
import diseases.DiseaseController;
import diseases.DiseaseView;

public class GameView extends JFrame implements ActionListener {

	private JButton moveButton = new JButton(MOVE_BUTTON);
	private JButton treatButton = new JButton(TREAT_BUTTON);
	private JButton cureButton = new JButton(CURE_BUTTON);
	private JButton buildButton = new JButton(BUILD_BUTTON);
	private JButton shareButton = new JButton(SHARE_BUTTON);
	private JButton passButton = new JButton(PASS_BUTTON);
	GameController controller;
	GameModel model;
	BorderLayout layout;
	JLabel background;
	JPanel gameInfoPanel, playerInfoPanel, playerActionPanel, mapPanel;
	List<CharacterModel> players;
	CityView cities;

	public GameView(GameController controller) {
		this.controller = controller;
		this.model = controller.getGameModel();
		this.layout = new BorderLayout();
		this.background = new JLabel();
		this.gameInfoPanel = new JPanel();
		this.playerInfoPanel = new JPanel();
		this.playerActionPanel = new JPanel();
		this.mapPanel = new JPanel();
		this.players = this.model.getCharacters();

		CityController cityController = this.controller.getCityController();
		this.cities = new CityView(cityController);
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
		JLabel spacer = new JLabel();
		JLabel selectedCityOutline = new JLabel();
		
		spacer.setPreferredSize(SPACER);
		spacer.setBorder(BorderFactory.createLineBorder(CUSTOM_GRAY_1));
		spacer.setOpaque(true);
		
		selectedCityOutline.setText(NO_SELECTED_CITY);
		selectedCityOutline.setHorizontalAlignment(JLabel.CENTER);
		selectedCityOutline.setPreferredSize(SELECTED_CITY_SIZE);
		
		this.moveButton.addActionListener(this);
		this.treatButton.addActionListener(this);
		this.cureButton.addActionListener(this);
		this.buildButton.addActionListener(this);
		this.shareButton.addActionListener(this);
		this.passButton.addActionListener(this);
		
		this.playerActionPanel.setLayout(actionLayout);
		this.playerActionPanel.add(spacer);
		this.playerActionPanel.add(selectedCityOutline);
		this.playerActionPanel.add(this.moveButton);
		this.playerActionPanel.add(this.treatButton);
		this.playerActionPanel.add(this.cureButton);
		this.playerActionPanel.add(this.buildButton);
		this.playerActionPanel.add(this.shareButton);
		this.playerActionPanel.add(this.passButton);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() instanceof JButton) {
			Object button = event.getSource();
			
			if (button == this.moveButton) {
				if(this.controller.getCurrentPlayer().getCharactersCurrentCity().equals("Atlanta")){
					this.controller.moveCharacter(this.controller.getCurrentPlayer(), this.controller.getCityController().getCityByName("Chicago"));
				}else {
					this.controller.moveCharacter(this.controller.getCurrentPlayer(), this.controller.getCityController().getCityByName("Atlanta"));
				}
			} else if (button == this.treatButton) {
				
			} else if (button == this.cureButton) {
				
			} else if (button == this.buildButton) {
				
			} else if (button == this.shareButton) {
				
			} else if (button == this.passButton) {
				this.controller.endOfTurn();
			}			
			repaint();
		}
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
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(GAME_BOARD_SIZE);
		this.setResizable(false);
		this.add(this.gameInfoPanel, BorderLayout.NORTH);
		this.add(this.playerInfoPanel, BorderLayout.WEST);
		this.add(this.playerActionPanel, BorderLayout.SOUTH);
		this.add(this.mapPanel, BorderLayout.CENTER);
		this.pack();
		this.setVisible(true);
	}
	
	@Override // TODO: fix the flashing issue, might have to extend JPanel
	public void paint(Graphics gr) {
		super.paint(gr);
		
		gr.setColor(CUSTOM_BLUE);
		gr.fillRect(BLUE_FILLER_X, BLUE_FILLER_Y, 
				GAME_BOARD_SIZE.width - BLUE_FILLER_WIDTH, OFFSET_5);
		
		if (!this.model.isLost() && !this.model.isWon()) {
			paintBoard(gr);
		} else if (this.model.isLost()) {
			System.out.println("GAME OVER");
			System.exit(0);
		} else if (this.model.isWon()) {
			System.out.println("YOU WON!");
			System.exit(0);
		}
		
	}
	
	private void paintBoard(Graphics gr) {
		paintCities(gr);
		paintInfections(gr);
		paintGameCounters(gr);
		paintPlayerHands(gr);
		paintCurrentTurn(gr);
	}
	
	private void paintCities(Graphics gr) {
		this.cities.paintCities(gr);
	}

	private void paintPlayerHands(Graphics gr) {
		List<AbstractCharacterController> players = this.controller.getPlayers();

		for (int i = 0; i < players.size(); i++) {
			CharacterModel player = players.get(i).getCharacterModel();
			int startingY = findPlayerYCoord(i);
			int cardCount = 0;
			
			for (CardModel card : player.getHandOfCards()) {
				String name = card.getName();
				
				if (card.getType().equals(CardModel.CardType.PLAYER)) {
					CityFrontEndModel cityModel = this.cities.getCityToDraw(name);
					Color color = cityModel.getColor();
					int yloc = startingY + cardCount * OFFSET_20;

					paintCardinHand(gr, name, color, yloc);
				} else {
					int yloc = startingY + cardCount * OFFSET_20;
					
					paintCardinHand(gr, name, Color.YELLOW, yloc);
				}
				
				cardCount++;
			}
		}
	}
	
	private int findPlayerYCoord(int playerNum) {
		int totalPlayers = this.players.size();

		if (playerNum == FIRST_PLAYER_INDEX) {
			return PLAYER_ONE_Y;
		} else if (playerNum == SECOND_PLAYER_INDEX) {
			if (totalPlayers == TWO_PLAYERS) {
				return PLAYER_TWO_TWO_PLAYERS_Y;
			} else if (totalPlayers == THREE_PLAYERS) {
				return PLAYER_TWO_THREE_PLAYERS_Y;
			} else {
				return PLAYER_TWO_FOUR_PLAYERS_Y;
			}
		} else if (playerNum == THIRD_PLAYER_INDEX) {
			if (totalPlayers == THREE_PLAYERS) {
				return PLAYER_THREE_THREE_PLAYERS_Y;
			} else {
				return PLAYER_THREE_FOUR_PLAYERS_Y;
			}
		} else {
			return PLAYER_FOUR_PLAYERS_Y;
		}
	}

	private void paintCardinHand(Graphics gr, String cityName, Color color, int yloc) {
		Graphics2D gr2D = (Graphics2D) gr;

		if (color.equals(Color.BLUE)) {
			gr.setColor(PLAYER_HAND_BLUE);
		} else if (color.equals(Color.YELLOW)) {
			gr.setColor(PLAYER_HAND_YELLOW);
		} else if (color.equals(Color.BLACK)) {
			gr.setColor(PLAYER_HAND_BLACK);
		} else {
			gr.setColor(PLAYER_HAND_RED);
		}

		gr2D.setFont(FONT);
		gr2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
				RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		gr2D.fillRect(PLAYER_HAND_X, yloc, PLAYER_HAND_WIDTH, PLAYER_HAND_HEIGHT);
		gr2D.setColor(CUSTOM_GRAY_2);
		gr2D.drawString(cityName, PLAYER_HAND_X + OFFSET_5, yloc + OFFSET_15);
	}

	private void paintInfections(Graphics gr) {
		
	}

	private void paintGameCounters(Graphics gr) {
		Graphics2D gr2D = (Graphics2D) gr;
		
		
		gr2D.setFont(FONT);
		gr2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
				RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		
		paintPlayerDeckCount(gr2D);
		paintDiseaseCounters(gr2D);
		paintDiseaseStatus(gr2D);
		paintNumResearchStation(gr2D);
		paintNumOutbreaks(gr2D);
		paintInfectionRate(gr2D);
		paintDecks(gr2D);
	}
	
	private void paintPlayerDeckCount(Graphics2D gr2D) {
		int playerDeckCount = this.controller.getPlayerDeckController().getNumberOfCardsInDeck();
		Color deckTextColor = checkLowCount(playerDeckCount);
		
		gr2D.setColor(deckTextColor);
		gr2D.drawString(playerDeckCount + "", DECK_COUNTER_X, TOP_PANEL_TEXT_Y);
	}
	
	private void paintDiseaseCounters(Graphics2D gr2D) {
		DiseaseController diseaseController = this.controller.getDiseaseController();
		int blueDisease = diseaseController.getBlueDisease().getCubesLeft();
		int yellowDisease = diseaseController.getYellowDisease().getCubesLeft();
		int blackDisease = diseaseController.getBlackDisease().getCubesLeft();
		int redDisease = diseaseController.getRedDisease().getCubesLeft();
		Color blueTextColor = checkLowCount(blueDisease);
		Color yellowTextColor = checkLowCount(blueDisease);
		Color blackTextColor = checkLowCount(blueDisease);
		Color redTextColor = checkLowCount(blueDisease);
		
		gr2D.setColor(blueTextColor);
		gr2D.drawString(blueDisease + "", BLUE_COUNTER_X, TOP_PANEL_TEXT_Y);
		gr2D.setColor(yellowTextColor);
		gr2D.drawString(yellowDisease + "", YELLOW_COUNTER_X, TOP_PANEL_TEXT_Y);
		gr2D.setColor(blackTextColor);
		gr2D.drawString(blackDisease + "", BLACK_COUNTER_X, TOP_PANEL_TEXT_Y);
		gr2D.setColor(redTextColor);
		gr2D.drawString(redDisease + "", RED_COUNTER_X, TOP_PANEL_TEXT_Y);
	}
	
	private void paintDiseaseStatus(Graphics2D gr2D) {
		Image diseaseStatus;
		DiseaseController diseaseController = this.controller.getDiseaseController();
		
		if (diseaseController.getBlueDisease().isCured()) {
			diseaseStatus = this.setImage(BLUE_CURED);
			if (diseaseController.getBlueDisease().isEradicated()) {
				diseaseStatus = this.setImage(BLUE_ERADICATED);
			}

			gr2D.drawImage(diseaseStatus, BLUE_DISEASE_X, DISEASE_Y, null);
		}

		if (diseaseController.getYellowDisease().isCured()) {
			diseaseStatus = this.setImage(YELLOW_CURED);
			if (diseaseController.getYellowDisease().isEradicated()) {
				diseaseStatus = this.setImage(YELLOW_ERADICATED);
			}

			gr2D.drawImage(diseaseStatus, YELLOW_DISEASE_X, DISEASE_Y, null);
		}

		if (diseaseController.getBlackDisease().isCured()) {
			diseaseStatus = this.setImage(BLACK_CURED);
			if (diseaseController.getBlackDisease().isEradicated()) {
				diseaseStatus = this.setImage(BLACK_ERADICATED);
			}

			gr2D.drawImage(diseaseStatus, BLACK_DISEASE_X, DISEASE_Y, null);
		}

		if (diseaseController.getRedDisease().isCured()) {
			diseaseStatus = this.setImage(RED_CURED);
			if (diseaseController.getRedDisease().isEradicated()) {
				diseaseStatus = this.setImage(RED_ERADICATED);
			}

			gr2D.drawImage(diseaseStatus, RED_DISEASE_X, DISEASE_Y, null);
		}
	}
	
	private void paintNumResearchStation(Graphics2D gr2D) {
		int numStations = this.controller.getCityController().getResearchStationCounter();
		
		gr2D.setColor(CUSTOM_GRAY_2);
		gr2D.drawString(numStations + "", RESEARCH_COUNT_X, TOP_PANEL_TEXT_Y);
	}
	
	private void paintNumOutbreaks(Graphics2D gr2D) {
		int numOutbreaks = this.controller.getCityController().getOutbreakCoutner();
		
		gr2D.setColor(CUSTOM_GRAY_2);
		gr2D.drawString(numOutbreaks + "", OUTBREAK_COUNT_X, TOP_PANEL_TEXT_Y);
	}
	
	private void paintInfectionRate(Graphics2D gr2D) {	
		gr2D.setColor(CUSTOM_GRAY_2);
		gr2D.drawString("2", INFECTION_RATE_X, TOP_PANEL_TEXT_Y);
		gr2D.setColor(CUSTOM_GRAY_3);
		gr2D.drawString("2 2 3 3 4 4", INFECTION_RATE_X + OFFSET_10, TOP_PANEL_TEXT_Y);

	}
	
	private void paintDecks(Graphics2D gr2D) {
		List<CardModel> player = this.controller.getPlayerDeckController().getDiscardedCards();
		List<CardModel> infected = this.controller.getInfectionDeckController().getDiscardedCards();
		CardModel lastInfected = new CardModel("", CardModel.CardType.INFECTION);
		CardModel lastDiscarded = new CardModel("", CardModel.CardType.PLAYER);
		String infectedCityName = "";
		String discardedCityName = "";
		Image infectedCardImg = null;
		Image discardCardImg = null;
		Image playerCard = this.setImage(PLAYER_CARD_IMG);
		Image infectionCard = this.setImage(INFECTION_CARD_IMG);
		FontMetrics metrics = gr2D.getFontMetrics(FONT);
		
		if (player.size() > 0) {
			lastDiscarded = player.get(player.size() - 1);
			discardedCityName = lastDiscarded.getName().toLowerCase();
			discardCardImg = this.setImage(CARD_PATH + discardedCityName + BMP_FILE);
		}
		
		if (infected.size() > 0) {
			lastInfected = infected.get(infected.size() - 1);
			infectedCityName = lastInfected.getName().toLowerCase();
			infectedCardImg = this.setImage(CARD_PATH + infectedCityName + BMP_FILE);
		}
		
		if (player.size() <= 0) {
			int xlocTop = PLAYER_DISCARD_X + (CARD_WIDTH - metrics.stringWidth(PLAYER_DECK)) / 2;
			int xlocBottom = PLAYER_DISCARD_X + (CARD_WIDTH - metrics.stringWidth(DISCARD_PILE)) / 2;
			int yloc = CARD_Y + ((CARD_HEIGHT - metrics.getHeight()) / 2) + metrics.getAscent();
			
			gr2D.setColor(CUSTOM_GRAY_2);
			gr2D.fillRect(PLAYER_DISCARD_X, CARD_Y, CARD_WIDTH, CARD_HEIGHT);
			gr2D.setColor(Color.WHITE);
			gr2D.drawString(PLAYER_DECK, xlocTop, yloc + 8);
			gr2D.drawString(DISCARD_PILE, xlocBottom, yloc - 8);
		} else {
			gr2D.drawImage(discardCardImg, PLAYER_DISCARD_X, CARD_Y, null);
		}
		
		if (infected.size() <= 0) {
			int xlocTop = INFECTION_DISCARD_X + (CARD_WIDTH - metrics.stringWidth(INFECTION_DECK)) / 2;
			int xlocBottom = INFECTION_DISCARD_X + (CARD_WIDTH - metrics.stringWidth(DISCARD_PILE)) / 2;
			int yloc = CARD_Y + ((CARD_HEIGHT - metrics.getHeight()) / 2) + metrics.getAscent();
			
			gr2D.setColor(CUSTOM_GRAY_2);
			gr2D.fillRect(INFECTION_DISCARD_X, CARD_Y, CARD_WIDTH, CARD_HEIGHT);
			gr2D.setColor(Color.WHITE);
			gr2D.drawString(INFECTION_DECK, xlocTop, yloc + OFFSET_8);
			gr2D.drawString(DISCARD_PILE, xlocBottom, yloc - OFFSET_8);
		} else {
			gr2D.drawImage(infectedCardImg, INFECTION_DISCARD_X, CARD_Y, null);
		}
		
		gr2D.drawImage(playerCard, PLAYER_CARD_X, CARD_Y, null);
		gr2D.drawImage(infectionCard, INFECTION_CARD_X, CARD_Y, null);
	}
	
	private Color checkLowCount(int numCards) {
		if (numCards <= 10) {
			return Color.RED;
		} else {
			return CUSTOM_GRAY_2;
		}
	}

	private void paintCurrentTurn(Graphics gr) {
		Graphics2D gr2D = (Graphics2D) gr;
		CharacterModel character = this.controller.getCurrentPlayer().getCharacterModel();
		CharacterFrontEndModel characterFrontEnd = new CharacterFrontEndModel(character);
		String name = character.getName();
		String role = character.getRole();
		String img = characterFrontEnd.getImgPath();
		int actionsLeft = this.model.getActionsLeft();
		int count = 1;
		Image image = setImage(img);
		Color color = characterFrontEnd.getColor();
		
		gr2D.setFont(FONT);
		gr2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
				RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		gr2D.setColor(color);
		gr2D.fillRect(TURN_PANEL_X, TURN_PANEL_Y, TURN_PANEL_WIDTH, TURN_PANEL_HEIGHT);
		gr2D.drawImage(image, TURN_PANEL_X + OFFSET_5, TURN_PANEL_Y + OFFSET_5, null);
		gr2D.setColor(CUSTOM_GRAY_2);
		gr2D.drawString(name, TURN_PANEL_TEXT_X, TURN_PANEL_TEXT_Y);
		gr2D.drawString(role, TURN_PANEL_TEXT_X, TURN_PANEL_TEXT_Y + OFFSET_15);
		
		while (count <= actionsLeft) {
			gr2D.fillOval(408, 882 + OFFSET_15 * count, TURN_RADIUS, TURN_RADIUS);
			count++;
		}
	}
}
