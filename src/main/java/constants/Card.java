package constants;

import java.awt.Color;
import java.awt.Dimension;

public class Card {
	public final static Dimension CARD_SIZE = new Dimension(121, 69);
	
	public final static String PLAYER_CARD_IMG = "images/playerCard.png";
	public final static String INFECTION_CARD_IMG = "images/infectionCard.png";
	
	public final static Color PLAYER_HAND_BLUE = new Color(181, 216, 236);
	public final static Color PLAYER_HAND_YELLOW = new Color(252, 249, 201);
	public final static Color PLAYER_HAND_BLACK = new Color(163, 164, 167);
	public final static Color PLAYER_HAND_RED = new Color(242, 202, 186);
	
	public final static int DECK_COUNTER_X = 560;
	
	public final static int INITIAL_PLAYER_DECK_SIZE = 48;
	
	public final static int TOP_CARD_WIDTH = 121;
	public final static int TOP_CARD_HEIGHT = 167;
	public final static int TOP_CARD_Y = 36;
	
	public final static int TEXT_UPPER_Y = 115;
	public final static int TEXT_LOWER_Y = 135;
	
	public final static int TEXT_PLAYER_X = 325;
	public final static int TEXT_INFECTION_X = 1520;
	
	public final static int PLAYER_CARD_X = 425;
	public final static int PLAYER_DISCARD_X = 299;
	
	public final static int INFECTION_CARD_X = 1369;
	public final static int INFECTION_DISCARD_X = 1495;
	
	public final static int EVENT_CARD_SEPARATION = 162;
	public final static int EVENT_CARD_X = 1545;
	public final static int EVENT_CARD_Y = 822;
	
	public final static String EVENT_CARD_PATH = "images/playerDeck/eventCards/";
	public final static String CITY_CARD_PATH = "images/playerDeck/cityCards/";
}
