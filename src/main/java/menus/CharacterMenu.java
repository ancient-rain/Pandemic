package menus;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import static code.GraphicsConstants.*;

public class CharacterMenu extends JFrame implements ActionListener {
	List<String> characterList;
	String[] selectedCharacters;
	String[] playerNames;
	Object[] characters;
	int numPlayers;
	boolean playerOneSet, playerTwoSet, playerThreeSet, playerFourSet, allPlayersSet,
		characterListDrawn;
	JButton changeNameOne, changeRoleOne, changeNameTwo, changeRoleTwo, changeNameThree, 
		changeRoleThree, changeNameFour, changeRoleFour, startButton, contingencyButton,
		dispatcherButton, medicButton, operationsButton, quarantineButton, researcherButton,
		scientistButton;
	JLabel playerNameOne, characterNameOne, playerNameTwo, characterNameTwo, playerNameThree,
		characterNameThree, playerNameFour, characterNameFour, warningLabel;
	JPanel playerPanel;
	JFrame characterSelection;
	
	public CharacterMenu(int numPlayers) {
		this.numPlayers = numPlayers;
		this.allPlayersSet = false;
		this.init(numPlayers);
	}
	public void viewMenu() {
		this.addActionListeners();
		this.addPlayers();
		this.updateFrame();
	}
	
	private void addActionListeners() {
		this.startButton.addActionListener(this);
		this.changeNameOne.addActionListener(this);
		this.changeNameTwo.addActionListener(this);
		this.changeNameThree.addActionListener(this);
		this.changeNameFour.addActionListener(this);
		this.changeRoleOne.addActionListener(this);
		this.changeRoleTwo.addActionListener(this);
		this.changeRoleThree.addActionListener(this);
		this.changeRoleFour.addActionListener(this);
	}
	
	private void addPlayers() {
		for (int i = 1; i <= this.numPlayers; i++) {
			this.addInitialPlayer(i);
		}
		
		this.playerPanel.add(this.warningLabel);
		this.playerPanel.add(this.startButton);
		this.add(this.playerPanel);
	}
	
	private void addInitialPlayer(int playerNum) {
		JPanel playerNamePanel = new JPanel();
		JPanel playerDescriptionPanel = new JPanel();
		JPanel playerOptionPanel = new JPanel();
		JPanel playerRolePanel = new JPanel();
		FlowLayout playerDescriptionLayout = new FlowLayout(FlowLayout.LEFT, OFFSET_5, 
				OFFSET_5);
		FlowLayout playerOptionLayout = new FlowLayout(FlowLayout.LEFT, OFFSET_5, OFFSET_5);
		BoxLayout playerNameLayout = new BoxLayout(playerNamePanel, BoxLayout.Y_AXIS);
		BorderLayout playerRoleLayout = new BorderLayout();
		Color color = Color.GRAY;
		
		playerNamePanel.setLayout(playerNameLayout);
		playerNamePanel.setBackground(color);
		playerNamePanel.setOpaque(true);
		addPlayerNameLabels(playerNamePanel, playerNum);
		
		playerDescriptionPanel.setLayout(playerDescriptionLayout);
		playerDescriptionPanel.setAlignmentX(LEFT_ALIGNMENT);
		playerDescriptionPanel.setBackground(color);
		playerDescriptionPanel.setOpaque(true);
		playerDescriptionPanel.add(playerNamePanel);
		playerDescriptionPanel.setPreferredSize(TURN_HEADER_SIZE);
		
		playerOptionPanel.setLayout(playerOptionLayout);
		playerOptionPanel.setBackground(color);
		playerOptionPanel.setOpaque(true);
		
		addPlayerOptionButtons(playerOptionPanel, playerNum);
		
		playerRolePanel.setLayout(playerRoleLayout);
		playerRolePanel.add(playerDescriptionPanel, BorderLayout.CENTER);
		playerRolePanel.add(playerOptionPanel, BorderLayout.SOUTH);
		
		this.playerPanel.add(playerRolePanel);
	}
	
	private void addPlayerNameLabels(JPanel panel, int playerNum) {
		if (playerNum == PLAYER_ONE) {
			panel.add(this.playerNameOne);
			panel.add(this.characterNameOne);
		} else if (playerNum == PLAYER_TWO) {
			panel.add(this.playerNameTwo);
			panel.add(this.characterNameTwo);
		} else if (playerNum == PLAYER_THREE) {
			panel.add(this.playerNameThree);
			panel.add(this.characterNameThree);
		} else {
			panel.add(this.playerNameFour);
			panel.add(this.characterNameFour);
		}
	}
	
	private void addPlayerOptionButtons(JPanel panel, int playerNum) {
		if (playerNum == PLAYER_ONE) {
			panel.add(this.changeNameOne);
			panel.add(this.changeRoleOne);
		} else if (playerNum == PLAYER_TWO) {
			panel.add(this.changeNameTwo);
			panel.add(this.changeRoleTwo);
		} else if (playerNum == PLAYER_THREE) {
			panel.add(this.changeNameThree);
			panel.add(this.changeRoleThree);
		} else {
			panel.add(this.changeNameFour);
			panel.add(this.changeRoleFour);
		}
	}
	
	private void updateFrame() {
		int frameHeight = CHARACTER_MENU_HEIGHT + 
				(this.numPlayers - TWO_PLAYERS) * OFFSET_100;
		this.setTitle("Choose Roles");
		this.setSize(CHARACTER_MENU_SIZE, frameHeight);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() instanceof JButton) {
			Object button = event.getSource();
			
			if (button == this.startButton) {
				if (checkAllPlayersSet()) {
					this.allPlayersSet = true;
					this.setVisible(false);
				} else {
					this.warningLabel.setText("Not all players are set");
				}
			} else if (button == this.changeNameOne) {
				String name = JOptionPane.showInputDialog(this,
						"First player's new name:", "Change Name",
						JOptionPane.QUESTION_MESSAGE);
				
				if (name != null && !name.isEmpty()) {
					this.playerNameOne.setText(name);
					this.playerNames[0] = name;
				} else {
					this.warningLabel.setText("New name cannot be empty");
				}
			} else if (button == this.changeNameTwo) {
				String name = JOptionPane.showInputDialog(this,
						"Second player's new name:", "Change Name",
						JOptionPane.QUESTION_MESSAGE);
				
				if (name != null && !name.isEmpty()) {
					this.playerNameTwo.setText(name);
					this.playerNames[1] = name;
				} else {
					this.warningLabel.setText("New name cannot be empty");
				}
			} else if (button == this.changeNameThree) {
				String name = JOptionPane.showInputDialog(this,
						"Third player's new name:", "Change Name",
						JOptionPane.QUESTION_MESSAGE);
				
				if (name != null && !name.isEmpty()) {
					this.playerNameThree.setText(name);
					this.playerNames[2] = name;
				} else {
					this.warningLabel.setText("New name cannot be empty");
				}
			} else if (button == this.changeNameFour) {
				String name = JOptionPane.showInputDialog(this,
						"Fourth player's new name:", "Change Name",
						JOptionPane.QUESTION_MESSAGE);
				
				if (name != null && !name.isEmpty()) {
					this.playerNameFour.setText(name);
					this.playerNames[3] = name;
				} else {
					this.warningLabel.setText("New name cannot be empty");
				}
			} else if (button == this.changeRoleOne) {
				this.viewCharacterSelectionFrame();
				String character = (String) JOptionPane.showInputDialog(this,
						"Select a character (referencing the other menu):", 
						"Select character", JOptionPane.INFORMATION_MESSAGE,
						null, characters, characters[0]); 
				
				if (character != null) {
					this.characterNameOne.setText(character);
					this.selectedCharacters[0] = character;
					this.updateCharacterList();
					this.playerOneSet = true;
					this.setVisible(true);
					this.characterSelection.setVisible(false);
				} else {
					this.warningLabel.setText("No character selcted");
					this.setVisible(true);
					this.characterSelection.setVisible(false);
				}
			} else if (button == this.changeRoleTwo) {
				this.viewCharacterSelectionFrame();
				String character = (String) JOptionPane.showInputDialog(this,
						"Select a character (referencing the other menu):",
						"Select character", JOptionPane.INFORMATION_MESSAGE,
						null, characters, characters[0]); 
				
				if (character != null) {
					this.characterNameTwo.setText(character);
					this.selectedCharacters[1] = character;
					this.updateCharacterList();
					this.playerTwoSet = true;
					this.setVisible(true);
					this.characterSelection.setVisible(false);
				} else {
					this.warningLabel.setText("No character selcted");
					this.setVisible(true);
					this.characterSelection.setVisible(false);
				}
			} else if (button == this.changeRoleThree) {
				this.viewCharacterSelectionFrame();
				String character = (String) JOptionPane.showInputDialog(this,
						"Select a character (referencing the other menu):",
						"Select character", JOptionPane.INFORMATION_MESSAGE,
						null, characters, characters[0]); 
				
				if (character != null) {
					this.characterNameThree.setText(character);
					this.selectedCharacters[2] = character;
					this.updateCharacterList();
					this.playerThreeSet = true;
					this.setVisible(true);
					this.characterSelection.setVisible(false);
				} else {
					this.warningLabel.setText("No character selcted");
					this.setVisible(true);
					this.characterSelection.setVisible(false);
				}
			} else if (button == this.changeRoleFour) {
				this.viewCharacterSelectionFrame();
				String character = (String) JOptionPane.showInputDialog(this,
						"Select a character (referencing the other menu):",
						"Select character", JOptionPane.INFORMATION_MESSAGE,
						null, this.characters, this.characters[0]); 
				
				if (character != null) {
					this.characterNameFour.setText(character);
					this.selectedCharacters[3] = character;
					this.updateCharacterList();
					this.playerFourSet = true;
					this.setVisible(true);
					this.characterSelection.setVisible(false);
				} else {
					this.warningLabel.setText("No character selcted");
					this.setVisible(true);
				}
			}
		}
	}
	
	private void updateCharacterList() {
		String [] tempCharacterArray = CHARACTERS_NAME_ARRAY;
		this.characterList = new ArrayList<>();
		
		for (int i = 0; i < this.selectedCharacters.length; i++) {
			String name = this.selectedCharacters[i];
			for (int j = 0; j < tempCharacterArray.length; j++) {
				if (name.equals(tempCharacterArray[j])) {
					tempCharacterArray[j] = "";
				}
			}
		}		
		
		for (String s : tempCharacterArray) {
			if (!s.equals("")) {
				this.characterList.add(s);
			}
		}
		this.characters = (Object[]) this.characterList.toArray();
	}
	
	private boolean checkAllPlayersSet() {
		return this.playerOneSet && this.playerTwoSet && 
				this.playerThreeSet && this.playerFourSet;
	}
	
	private void viewCharacterSelectionFrame() {
		this.setVisible(false);
		
		if (!this.characterListDrawn) {
			this.characterSelection.setLayout(new GridLayout(OFFSET_2, OFFSET_4));
			this.characterSelection.setLocation(OFFSET_0, OFFSET_0);
			this.viewCharacterInformation();
			this.characterSelection.setDefaultCloseOperation(EXIT_ON_CLOSE);
			this.characterSelection.pack();
			this.characterListDrawn = true;
		}
		
		this.characterSelection.setVisible(true);
	}
	
	private void viewCharacterInformation() {
		ImageIcon character = new ImageIcon();
		List<String> characterCards = CHARACTERS_CARD_LIST;
		List<JLabel> cardLabels = new ArrayList<>();
		
		this.addCharacterLabels(cardLabels);
		
		for (int i = 0; i < characterCards.size(); i++) {
			JLabel label = cardLabels.get(i);
			String imagePath = characterCards.get(i);
			Image image = this.setImage(imagePath);
			character = new ImageIcon(image);
			label.setIcon(character);
			this.characterSelection.add(label);
		}
	}
	
	private void addCharacterLabels(List<JLabel> labels) {
		for (int i = 0; i < CHARACTERS_CARD_LIST.size(); i++) {
			labels.add(new JLabel());
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
	
	public String[] getPlayerNames() {
		return this.playerNames;
	}
	
	public String[] getCharacterNames() {
		return this.selectedCharacters;
	}
	
	private void init(int numPlayers) {
		this.characterList = CHARACTERS_NAME_LIST;
		this.selectedCharacters = new String[] {"", "", "", ""};
		this.playerNames = new String[] {"Player1", "Player2", "Player3", "Player4"};
		this.characters = CHARACTERS_NAME_ARRAY;
		this.characterSelection = new JFrame("List of characters");
		this.startButton = new JButton("Start Game");
		this.warningLabel = new JLabel("Please give all players a role");
		this.warningLabel.setForeground(Color.RED);
		this.playerPanel = new JPanel();
		this.characterListDrawn = false;
		this.changeNameOne = new JButton("Change Name");
		this.changeRoleOne = new JButton("Change Role");
		this.changeNameTwo = new JButton("Change Name");
		this.changeRoleTwo = new JButton("Change Role");
		this.changeNameThree = new JButton("Change Name");
		this.changeRoleThree = new JButton("Change Role");
		this.changeNameFour = new JButton("Change Name");
		this.changeRoleFour = new JButton("Change Role");
		
		this.playerNameOne = new JLabel("Player1");
		this.characterNameOne = new JLabel("Character not selected");
		this.playerNameTwo = new JLabel("Player2");
		this.characterNameTwo = new JLabel("Character not selected");
		this.playerNameThree = new JLabel("Player3");
		this.characterNameThree = new JLabel("Character not selected");
		this.playerNameFour = new JLabel("Player4");
		this.characterNameFour = new JLabel("Character not selected");
		
		if (numPlayers == TWO_PLAYERS) {
			this.playerOneSet = false;
			this.playerTwoSet = false;
			this.playerThreeSet = true;
			this.playerFourSet = true;
		} else if (numPlayers == THREE_PLAYERS) {
			this.playerOneSet = false;
			this.playerTwoSet = false;
			this.playerThreeSet = false;
			this.playerFourSet = true;
		} else {
			this.playerOneSet = false;
			this.playerTwoSet = false;
			this.playerThreeSet = false;
			this.playerFourSet = false;
		}
	}
}