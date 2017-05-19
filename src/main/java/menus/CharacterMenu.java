package menus;

import static constants.Game.OFFSET_15;
import static constants.Game.HALF;
import static constants.Game.CHARACTERS_NAME_LIST;
import static constants.Game.GAME_BOARD_SIZE;
import static constants.Card.CHARACTERS_CARD_LIST;
import static constants.City.ATLANTA;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import characters.CharacterModel;
import city.CityController;
import city.CityModel;

public class CharacterMenu {
	private CityModel atlanta;
	
	List<String> characterCardList, characterNames;
	List<CharacterModel> characters;
	int numPlayers;
	boolean playerOneSet, playerTwoSet, playerThreeSet, playerFourSet, allPlayersSet;
	JPanel panel;
	
	public CharacterMenu(int numPlayers, CityController cityController) {
		this.numPlayers = numPlayers;
		this.allPlayersSet = false;
		this.panel = new JPanel();
		this.characterCardList = CHARACTERS_CARD_LIST;
		this.characterNames = CHARACTERS_NAME_LIST;
		this.characters = new ArrayList<>();
		this.atlanta = cityController.getCityByName(ATLANTA);
	}
	
	public List<CharacterModel> getCharacters() {
		return this.characters;
	}
	
	public void view() {
		selectCharacters();
		randomizeOrder();
	}
	
	private void selectCharacters() {
		int count = 0;
		
		setCardPanel();
		
		while (count < this.numPlayers) {
			String playerName = enterPlayerName(count + 1);
			String characterName = selectCharacter(playerName);
			CharacterModel character = new CharacterModel(characterName, this.atlanta);
			
			character.setName(playerName);
			this.characters.add(character);
			this.panel.removeAll();
			setCardPanel();
			
			count++;
		}
	}
	
	private void randomizeOrder() {
		Collections.shuffle(this.characters);
	}
	
	private String enterPlayerName(int count) {
		String playerName = "";
		
		while (playerName == null|| playerName.equals("")) {
			playerName = JOptionPane.showInputDialog(null, "Enter the name for Player" + count + ":",
					"Player Name", JOptionPane.INFORMATION_MESSAGE);
		}
		
		return playerName;
	}
	
	private String selectCharacter(String playerName) {
		String character = "";
		int selectedCharacter = -1;
		
		while (selectedCharacter == -1) {
			selectedCharacter = JOptionPane.showOptionDialog(null, this.panel, "Select Character",
					JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, 
					this.characterNames.toArray(), null);
		}
		
		character = this.characterNames.get(selectedCharacter);
		this.characterCardList.remove(selectedCharacter);
		this.characterNames.remove(selectedCharacter);
		
		return character;
	}
	
	
	private void setCardPanel() {
		GridLayout cardLayout = new GridLayout(2, 4);
		List<String> characterCards = this.characterCardList;
		List<JLabel> cardLabels = new ArrayList<>();
		
		this.panel.setLayout(cardLayout);
		
		addCharacterLabels(cardLabels);
		addPanelCards(cardLabels, characterCards);
	}
	
	private void addCharacterLabels(List<JLabel> labels) {
		for (int i = 0; i < this.characterCardList.size(); i++) {
			labels.add(new JLabel());
		}
	}
	
	private void addPanelCards(List<JLabel> cardLabels, List<String> characterCards) {
		ImageIcon character = new ImageIcon();
		List<String> charactersToRemove = new ArrayList<>();

		for (int i = 0; i < cardLabels.size(); i++) {
			JLabel label = cardLabels.get(i);
			String imagePath = characterCards.get(i);
			Image image = setImage(imagePath);
			character = new ImageIcon(image);
			
			label.setIcon(character);
			charactersToRemove.add(characterCards.get(i));
			this.panel.add(label);
		}
	}

	private Image setImage(String filepath) {
		Image image = null;
		
		try {
			File file = new File(filepath);
			image = ImageIO.read(file);
		} catch (IOException e) {		
			System.out.println("Could not locate image");
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		return image;
	}
}