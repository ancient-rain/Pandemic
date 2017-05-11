package constants;

import java.awt.Color;
import java.awt.Dimension;

public class Constants {
	public final static Dimension GAME_BOARD_SIZE = new Dimension(1920, 1040);
	public final static Dimension PLAYER_HEADER_SIZE = new Dimension(190, 65);
	public final static Dimension CARD_SIZE = new Dimension(121, 69);
	public final static Dimension TURN_HEADER_SIZE = new Dimension(190, 60);
	public final static Dimension SELECTED_CITY_SIZE = new Dimension(155, 30);
	public final static Dimension SPACER = new Dimension(180, 30);
	
	public final static int CITY_RADIUS = 25;
	public final static int DELTA = CITY_RADIUS / 2;
	public final static int INITIAL_PLAYER_DECK_SIZE = 48;
	public final static int TEXT_SIZE = 12;
	public final static int OFFSET_5 = 5;
	public final static int OFFSET_10 = 10;
	public final static int OFFSET_15 = 15;
	public final static int OFFSET_20 = 20;
	public final static int OFFSET_30 = 30;
	public final static int OFFSET_40 = 40;
	
	public final static Color CUSTOM_GRAY_1 = new Color(238, 238, 238);
	public final static Color CUSTOM_GRAY_2 = new Color(51, 51, 51);
	public final static Color MEDIC_COLOR = new Color(254, 84, 9);
	public final static Color DISPATCHER_COLOR = new Color(236, 81, 175);
	public final static Color QUARANTINE_SPECIALIST_COLOR = new Color(34, 108, 27);
	public final static Color CONTINGENCY_PLANNER_COLOR = new Color(55, 195, 213);
	public final static Color RESEARCHER_COLOR = new Color(126, 69, 24);
	public final static Color SCIENTIST_COLOR = new Color(195, 197, 191);
	public final static Color OPERATIONS_EXPERT_COLOR = new Color(77, 187, 1);

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
	public final static String INFECTION_IMG = "images/infectionCard.png";
	public final static String OUTBREAK_IMG = "images/outbreak.png";
	public final static String PANDEMIC_IMG = "images/pandemic.png";
	public final static String PANDEMIC_RULES = "files/pandemic.pdf";
	public final static String PLAYER_IMG = "images/playerCard.png";
	public final static String RESEARCH_STATION_IMG = "images/researchStation.png";
	public final static String BMP_FILE = ".bmp";
	
	public static String DISCARD_PILE = "Discard Pile";
	public static String PLAYER_DECK = "Player Deck";
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
