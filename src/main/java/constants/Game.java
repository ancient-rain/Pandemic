package constants;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

public class Game {
	public final static Dimension GAME_BOARD_SIZE = new Dimension(1920, 1040);
	public final static Dimension PLAYER_HEADER_SIZE = new Dimension(190, 65);
	public final static Dimension SPACER = new Dimension(180, 30);
	
	public final static Color CUSTOM_GRAY_1 = new Color(238, 238, 238);
	public final static Color CUSTOM_GRAY_2 = new Color(51, 51, 51);
	public final static Color CUSTOM_GRAY_3 = new Color(192, 192, 192);
	public final static Color CUSTOM_BLUE = new Color(153, 217, 234);
	
	public final static String INITIAL_NUM_RESEARCH_STATIONS = "1";
	public final static String INITIAL_NUM_OUTBREAKS = "0";
	public final static String INFECTION_RATE_TEXT = "2 2 2 3 3 4 4";
	
	public final static int TEXT_SIZE = 12;
	
	public final static Font FONT = new Font("Dialog", Font.BOLD, TEXT_SIZE);
	
	public final static int TOP_PANEL_TEXT_Y = 75;
	
	public final static int BLUE_FILLER_WIDTH = 208;
	public final static int BLUE_FILLER_X = 203;
	public final static int BLUE_FILLER_Y = 115;
	
	public final static int SELECTED_CITY_X = 15;
	public final static int SELECTED_CITY_Y = 980;
	public final static int SELECTED_CITY_WIDTH = 180;
	public final static int SELECTED_CITY_HEIGHT = 45;
	
	public final static int OFFSET_1 = 1;
	public final static int OFFSET_2 = 2;
	public final static int OFFSET_5 = 5;
	public final static int OFFSET_8 = 8;
	public final static int OFFSET_10 = 10;
	public final static int OFFSET_15 = 15;
	public final static int OFFSET_20 = 20;
	public final static int OFFSET_30 = 30;
	public final static int OFFSET_40 = 40;
	
	public final static int TURN_RADIUS = 14;
	public final static int TURN_PANEL_X = 213;
	public final static int TURN_PANEL_Y = 897;
	public final static int TURN_PANEL_WIDTH = 190;
	public final static int TURN_PANEL_HEIGHT = 60;
	public final static int TURN_PANEL_TEXT_X = 273;
	public final static int TURN_PANEL_TEXT_Y = 922;
	
	public final static int RESEARCH_COUNT_X = 1070;
	public final static int OUTBREAK_COUNT_X = 1175;
	public final static int INFECTION_RATE_X = 1280;
	
	public final static int FIRST_PLAYER_INDEX = 0;
	public final static int SECOND_PLAYER_INDEX = 1;
	public final static int THIRD_PLAYER_INDEX = 2;
	
	final static int PLAYER_ONE = 1;
	final static int PLAYER_TWO = 2;
	final static int PLAYER_THREE = 3;
	final static int PLAYER_FOUR = 4;
	
	public final static int TWO_PLAYERS = 2;
	public final static int THREE_PLAYERS = 3;
	final static int FOUR_PLAYERS = 4;
	
	public final static String MAP_IMG = "images/map.png";
	public final static String BIOHAZARD_IMG = "images/biohazard.PNG";
	public final static String OUTBREAK_IMG = "images/outbreak.png";
	public final static String PANDEMIC_IMG = "images/pandemic.png";
	public final static String PANDEMIC_RULES = "files/pandemic.pdf";
	public final static String RESEARCH_STATION_IMG = "images/researchStation.png";
	public final static String BMP_FILE = ".bmp";
	public final static String PNG_FILE = ".png";
	
	public static String INFECTION_DECK = "Infection Deck";
	public static String PLAYER_DECK = "Player Deck";
	public static String DISCARD_PILE = "Discard Pile";
	
	public static String MEDIC = "Medic";
	public static String DISPATCHER = "Dispatcher";
	public static String QUARANTINE_SPECIALIST = "Quarantine Specialist";
	public static String CONTINGENCY_PLANNER = "Contingency Planner";
	public static String RESEARCHER = "Researcher";
	public static String SCIENTIST = "Scientist";
	public static String OPERATIONS_EXPERT = "Operations Expert";
	
	public static String MOVE_BUTTON = "Move";
	public static String TREAT_BUTTON = "Treat";
	public static String CURE_BUTTON = "Cure";
	public static String BUILD_BUTTON = "Build";
	public static String SHARE_BUTTON = "Share";
	public static String PASS_BUTTON = "Pass";
	public static String PLAY_EVENT_BUTTON = "Play Event Card";
	public static String NO_SELECTED_CITY = "No Selected City";
	
	public static String BLUE = "Blue";
	public static String YELLOW = "Yellow";
	public static String BLACK = "Black";
	public static String RED = "Red";
	
	public static String LOST = "You Lost!";
	public static String LOST_GAME = "Lost Game!";
	public static String WON = "You Won!";
	public static String WON_GAME = "Won Game!";
	
	public static String TREAT = "Treat";
	public static String SELECT_DISEASE_TO_TREAT = "Select a diseease to treat: ";
	public static String NO_DISEASES_TO_TREAT = "No diseases to treat at current location!";
	
	public static String CURE = "Cure";
	public static String SELECT_DISEASE_TO_CURE = "Select a disease to cure: ";
	public static String ALREADY_ERADICATED = "Disease is already eradicated!";
	public static String ALREADY_CURED = "Disease is already cured!";
	public static String NOT_ENOUGH_CARDS_TO_CURE = "Not enough cards to cure selected disease!";
	public static String SELECT_CARD_TO_KEEP = "Select a card to keep in your hand: ";
	public static String NOT_AT_RESEARCH_STATION = "Must be at research station to cure disease!";
}
