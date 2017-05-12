package constants;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

public class Constants {
	public final static Dimension GAME_BOARD_SIZE = new Dimension(1920, 1040);
	public final static Dimension PLAYER_HEADER_SIZE = new Dimension(190, 65);
	public final static Dimension CARD_SIZE = new Dimension(121, 69);
	public final static Dimension SELECTED_CITY_SIZE = new Dimension(155, 30);
	public final static Dimension SPACER = new Dimension(180, 30);
	
	public final static int CITY_RADIUS = 25;
	public final static int TURN_RADIUS = 14;
	public final static int DELTA = CITY_RADIUS / 2;
	public final static int INITIAL_PLAYER_DECK_SIZE = 48;
	public final static int TEXT_SIZE = 12;
	
	public final static int OFFSET_1 = 1;
	public final static int OFFSET_2 = 2;
	public final static int OFFSET_5 = 5;
	public final static int OFFSET_8 = 8;
	public final static int OFFSET_10 = 10;
	public final static int OFFSET_15 = 15;
	public final static int OFFSET_20 = 20;
	public final static int OFFSET_30 = 30;
	public final static int OFFSET_40 = 40;
	
	public final static int RESEARCH_STATION_TOP_OFFSET = 2;
	public final static int RESEARCH_STATION_SIDE_OFFSET = 5;
	public final static int RESEARCH_STATION_MIDDLE_OFFSET = 9;
	public final static int RESEARCH_STATION_BOTTOM_OFFSET = 19;
	
	public final static int TURN_PANEL_X = 213;
	public final static int TURN_PANEL_Y = 897;
	public final static int TURN_PANEL_WIDTH = 190;
	public final static int TURN_PANEL_HEIGHT = 60;
	public final static int TURN_PANEL_TEXT_X = 273;
	public final static int TURN_PANEL_TEXT_Y = 922;
	
	public final static int BLUE_FILLER_WIDTH = 208;
	public final static int BLUE_FILLER_X = 203;
	public final static int BLUE_FILLER_Y = 115;
	
	public final static int TOP_PANEL_TEXT_Y = 75;
	public final static int DECK_COUNTER_X = 560;
	public final static int BLUE_COUNTER_X = 673;
	public final static int YELLOW_COUNTER_X = 771;
	public final static int BLACK_COUNTER_X = 870;
	public final static int RED_COUNTER_X = 967;
	public final static int DISEASE_Y = 36;
	public final static int BLUE_DISEASE_X = 613;
	public final static int YELLOW_DISEASE_X = 711;
	public final static int BLACK_DISEASE_X = 810;
	public final static int RED_DISEASE_X = 907;
	public final static int RESEARCH_COUNT_X = 1080;
	public final static int OUTBREAK_COUNT_X = 1185;
	public final static int INFECTION_RATE_X = 1290;
	public final static int CARD_WIDTH = 121;
	public final static int CARD_HEIGHT = 167;
	public final static int PLAYER_DISCARD_X = 299;
	public final static int CARD_Y = 36;
	public final static int TEXT_PLAYER_X = 325;
	public final static int TEXT_INFECTION_X = 1520;
	public final static int TEXT_UPPER_Y = 115;
	public final static int TEXT_LOWER_Y = 135;
	public final static int PLAYER_CARD_X = 425;
	public final static int INFECTION_CARD_X = 1374;
	public final static int INFECTION_DISCARD_X = 1500;
	
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
	
	public final static int PLAYER_HAND_HEIGHT = 20;
	public final static int PLAYER_HAND_WIDTH = 120;
	public final static int PLAYER_HAND_X = 13;
	public final static int PLAYER_ONE_Y = 180;
	public final static int PLAYER_TWO_TWO_PLAYERS_Y = 607;
	public final static int PLAYER_TWO_THREE_PLAYERS_Y = 465;
	public final static int PLAYER_TWO_FOUR_PLAYERS_Y = 393;
	public final static int PLAYER_THREE_THREE_PLAYERS_Y = 749;
	public final static int PLAYER_THREE_FOUR_PLAYERS_Y = 607;
	public final static int PLAYER_FOUR_PLAYERS_Y = 820;
	
	public final static Font FONT = new Font("Dialog", Font.BOLD, TEXT_SIZE);
	
	public final static Color CUSTOM_GRAY_1 = new Color(238, 238, 238);
	public final static Color CUSTOM_GRAY_2 = new Color(51, 51, 51);
	public final static Color CUSTOM_GRAY_3 = new Color(192, 192, 192);
	public final static Color CUSTOM_BLUE = new Color(153, 217, 234);
	public final static Color MEDIC_COLOR = new Color(254, 84, 9);
	public final static Color DISPATCHER_COLOR = new Color(236, 81, 175);
	public final static Color QUARANTINE_SPECIALIST_COLOR = new Color(34, 108, 27);
	public final static Color CONTINGENCY_PLANNER_COLOR = new Color(55, 195, 213);
	public final static Color RESEARCHER_COLOR = new Color(126, 69, 24);
	public final static Color SCIENTIST_COLOR = new Color(195, 197, 191);
	public final static Color OPERATIONS_EXPERT_COLOR = new Color(77, 187, 1);
	public final static Color PLAYER_HAND_BLUE = new Color(181, 216, 236);
	public final static Color PLAYER_HAND_YELLOW = new Color(252, 249, 201);
	public final static Color PLAYER_HAND_BLACK = new Color(163, 164, 167);
	public final static Color PLAYER_HAND_RED = new Color(242, 202, 186);

	public final static String MEDIC_ICON = "images/characters/icons/medic.png";
	public final static String DISPATCHER_ICON = "images/characters/icons/dispatcher.png";
	public final static String QUARANTINE_SPECIALIST_ICON = "images/characters/icons/quarantineSpecialist.png";
	public final static String CONTINGENCY_PLANNER_ICON = "images/characters/icons/contingencyPlanner.png";
	public final static String RESEARCHER_ICON = "images/characters/icons/researcher.png";
	public final static String SCIENTIST_ICON = "images/characters/icons/scientist.png";
	public final static String OPERATIONS_EXPERT_ICON = "images/characters/icons/operationsExpert.png";
	
	public final static String BLACK_OUTLINE = "images/cureMarkers/blackOutline.png";
	public final static String BLUE_OUTLINE = "images/cureMarkers/blueOutline.png";
	public final static String RED_OUTLINE = "images/cureMarkers/redOutline.png";
	public final static String YELLOW_OUTLINE = "images/cureMarkers/yellowOutline.png";
	public final static String BLACK_CURED = "images/cureMarkers/black.png";
	public final static String BLUE_CURED = "images/cureMarkers/blue.png";
	public final static String RED_CURED = "images/cureMarkers/red.png";
	public final static String YELLOW_CURED = "images/cureMarkers/yellow.png";
	public final static String BLACK_ERADICATED = "images/cureMarkers/blackEradicated.png";
	public final static String BLUE_ERADICATED = "images/cureMarkers/blueEradicated.png";
	public final static String RED_ERADICATED = "images/cureMarkers/redEradicated.png";
	public final static String YELLOW_ERADICATED = "images/cureMarkers/yellowEradicated.png";
	
	public final static String MAP_IMG = "images/map.png";
	public final static String BIOHAZARD_IMG = "images/biohazard.PNG";
	public final static String INFECTION_CARD_IMG = "images/infectionCard.png";
	public final static String OUTBREAK_IMG = "images/outbreak.png";
	public final static String PANDEMIC_IMG = "images/pandemic.png";
	public final static String PANDEMIC_RULES = "files/pandemic.pdf";
	public final static String PLAYER_CARD_IMG = "images/playerCard.png";
	public final static String RESEARCH_STATION_IMG = "images/researchStation.png";
	public final static String BMP_FILE = ".bmp";
	
	public final static String CARD_PATH = "images/playerDeck/cityCards/";
	public final static String ALGIERS_CARD = "images/playerDeck/cityCards/algiers.bmp";
	public final static String ATLANTA_CARD = "images/playerDeck/cityCards/atlanta.bmp";
	public final static String BAGHDAD_CARD = "images/playerDeck/cityCards/baghdad.bmp";
	public final static String BANGKOK_CARD = "images/playerDeck/cityCards/bangkok.bmp";
	public final static String BEIJING_CARD = "images/playerDeck/cityCards/beijing.bmp";
	public final static String BOGOTA_CARD = "images/playerDeck/cityCards/bogota.bmp";
	public final static String BUENOS_AIRES_CARD = "images/playerDeck/cityCards/buenos aires.bmp";
	public final static String CAIRO_CARD = "images/playerDeck/cityCards/cairo.bmp";
	public final static String CHENNAI_CARD = "images/playerDeck/cityCards/chennai.bmp";
	public final static String CHICAGO_CARD = "images/playerDeck/cityCards/chicago.bmp";
	public final static String DELHI_CARD = "images/playerDeck/cityCards/delhi.bmp";
	public final static String ESSEN_CARD = "images/playerDeck/cityCards/essen.bmp";
	public final static String HO_CHI_MINH_CITY_CARD = "images/playerDeck/cityCards/ho chi minh city.bmp";
	public final static String HONG_KONG_CARD = "images/playerDeck/cityCards/hong kong.bmp";
	public final static String ISTANBUL_CARD = "images/playerDeck/cityCards/istanbul.bmp";
	public final static String JAKARTA_CARD = "images/playerDeck/cityCards/jakarta.bmp";
	public final static String JOHANNESBURG_CARD = "images/playerDeck/cityCards/johannesburg.bmp";
	public final static String KARACHI_CARD = "images/playerDeck/cityCards/karachi.bmp";
	public final static String KHARTOUM_CARD = "images/playerDeck/cityCards/khartoum.bmp";
	public final static String KINSHASA_CARD = "images/playerDeck/cityCards/kinshasa.bmp";
	public final static String KOLKATA_CARD = "images/playerDeck/cityCards/kolkata.bmp";
	public final static String LAGOS_CARD = "images/playerDeck/cityCards/lagos.bmp";
	public final static String LIMA_CARD = "images/playerDeck/cityCards/lima.bmp";
	public final static String LONDON_CARD = "images/playerDeck/cityCards/london.bmp";
	public final static String LOS_ANGELES_CARD = "images/playerDeck/cityCards/los angeles.bmp";
	public final static String MADRID_CARD = "images/playerDeck/cityCards/madrid.bmp";
	public final static String MANILA_CARD = "images/playerDeck/cityCards/manila.bmp";
	public final static String MEXICO_CITY_CARD = "images/playerDeck/cityCards/mexico city.bmp";
	public final static String MIAMI_CARD = "images/playerDeck/cityCards/miami.bmp";
	public final static String MILAN_CARD = "images/playerDeck/cityCards/milan.bmp";
	public final static String MONTREAL_CARD = "images/playerDeck/cityCards/montreal.bmp";
	public final static String MOSCOW_CARD = "images/playerDeck/cityCards/moscow.bmp";
	public final static String MUMBAI_CARD = "images/playerDeck/cityCards/mumbai.bmp";
	public final static String NEW_YORK_CARD = "images/playerDeck/cityCards/new york.bmp";
	public final static String OSAKA_CARD = "images/playerDeck/cityCards/osaka.bmp";
	public final static String PARIS_CARD = "images/playerDeck/cityCards/paris.bmp";
	public final static String RIYADH_CARD = "images/playerDeck/cityCards/riyadh.bmp";
	public final static String SAN_FRANCISCO_CARD = "images/playerDeck/cityCards/san francisco.bmp";
	public final static String SANTIAGO_CARD = "images/playerDeck/cityCards/santiago.bmp";
	public final static String SAO_PAULO_CARD = "images/playerDeck/cityCards/sao paulo.bmp";
	public final static String SEOUL_CARD = "images/playerDeck/cityCards/seoul.bmp";
	public final static String SHANGHAI_CARD = "images/playerDeck/cityCards/shanghai.bmp";
	public final static String ST_PETERSBURG_CARD = "images/playerDeck/cityCards/st. petersburg.bmp";
	public final static String SYDNEY_CARD = "images/playerDeck/cityCards/sydney.bmp";
	public final static String TAIPEI_CARD = "images/playerDeck/cityCards/taipei.bmp";
	public final static String TEHRAN_CARD = "images/playerDeck/cityCards/tehran.bmp";
	public final static String TOKYO_CARD = "images/playerDeck/cityCards/tokyo.bmp";
	public final static String WASHINGTON_CARD = "images/playerDeck/cityCards/washington.bmp";
	
	public static String INFECTION_DECK = "Infection Deck";
	public static String PLAYER_DECK = "Player Deck";
	public static String DISCARD_PILE = "Discard Pile";
	public final static String PLAYER_DISCARD_PILE = "<html>" + PLAYER_DECK + "<br><center>" + DISCARD_PILE + "<center><html>";
	
	public final static String INITIAL_NUM_RESEARCH_STATIONS = "1";
	public final static String INITIAL_NUM_OUTBREAKS = "0";
	public final static String INFECTION_RATE_TEXT = "2 2 2 3 3 4 4";
	
	public final static String ALGIERS = "Algiers";
	public final static String ATLANTA = "Atlanta";
	public final static String BAGHDAD = "Baghdad";
	public final static String BANGKOK = "Bangkok";
	public final static String BEIJING = "Beijing";
	public final static String BOGOTA = "Bogota";
	public final static String BUENOS_AIRES = "Buenos Aires";
	public final static String CAIRO = "Cairo";
	public final static String CHENNAI = "Chennai";
	public final static String CHICAGO = "Chicago";
	public final static String DELHI = "Delhi";
	public final static String ESSEN = "Essen";
	public final static String HO_CHI_MINH_CITY = "Ho Chi Minh City";
	public final static String HONG_KONG = "Hong Kong";
	public final static String ISTANBUL = "Istanbul";
	public final static String JAKARTA = "Jakarta";
	public final static String JOHANNESBURG = "Johannesburg";
	public final static String KARACHI = "Karachi";
	public final static String KHARTOUM = "Khartoum";
	public final static String KINSHASA = "Kinshasa";
	public final static String KOLKATA = "Kolkata";
	public final static String LAGOS = "Lagos";
	public final static String LIMA = "Lima";
	public final static String LONDON = "London";
	public final static String LOS_ANGELES = "Los Angeles";
	public final static String MADRID = "Madrid";
	public final static String MANILA = "Manila";
	public final static String MEXICO_CITY = "Mexico City";
	public final static String MIAMI = "Miami";
	public final static String MILAN = "Milan";
	public final static String MONTREAL = "Montreal";
	public final static String MOSCOW = "Moscow";
	public final static String MUMBAI = "Mumbai";
	public final static String NEW_YORK = "New York";
	public final static String OSAKA = "Osaka";
	public final static String PARIS = "Paris";
	public final static String RIYADH = "Riyadh";
	public final static String SAN_FRANCISCO = "San Francisco";
	public final static String SANTIAGO = "Santiago";
	public final static String SAO_PAULO = "Sao Paulo";
	public final static String SEOUL = "Seoul";
	public	final static String SHANGHAI = "Shanghai";
	public final static String ST_PETERSBURG = "St. Petersburg";
	public final static String SYDNEY = "Sydney";
	public final static String TAIPEI = "Taipei";
	public final static String TEHRAN = "Tehran";
	public final static String TOKYO = "Tokyo";
	public final static String WASHINGTON = "Washington";

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
	public static String NO_SELECTED_CITY = "No Selected City";
}
