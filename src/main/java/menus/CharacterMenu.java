package menus;

import static constants.Card.CHARACTERS_CARD_LIST;
import static constants.City.ATLANTA;
import static constants.Game.CHARACTERS_NAME_LIST;
import static constants.Game.CARD_LAYOUT;
import static constants.Game.PLAYER_NAME;
import static constants.Game.ENTER_PLAYER_NAME;
import static constants.Game.SELECT_CHARACTER;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
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
		
		while (playerName.equals("")) {
			playerName = JOptionPane.showInputDialog(null, ENTER_PLAYER_NAME + count + ":",
					PLAYER_NAME, JOptionPane.INFORMATION_MESSAGE);
			
			if (playerName == null) {
				System.exit(0);
			}
		}
		
		return playerName;
	}
	
	private String selectCharacter(String playerName) {
		String character = "";
		int selectedCharacter = -1;
		
		while (selectedCharacter == -1) {
			selectedCharacter = JOptionPane.showOptionDialog(null, this.panel, SELECT_CHARACTER,
					JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, 
					this.characterNames.toArray(), null);
		}
		
		character = this.characterNames.get(selectedCharacter);
		this.characterCardList.remove(selectedCharacter);
		this.characterNames.remove(selectedCharacter);
		
		return character;
	}
	
	
	private void setCardPanel() {
		List<String> characterCards = this.characterCardList;
		List<JLabel> cardLabels = new ArrayList<>();
		
		this.panel.setLayout(CARD_LAYOUT);
		
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
			e.printStackTrace();
			System.exit(0);
		}
		
		return image;
	}
}