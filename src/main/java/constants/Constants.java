package constants;

import java.awt.Color;
import java.awt.Dimension;

public class Constants {
	public final static Dimension GAME_BOARD_SIZE = new Dimension(1920, 1040);
	public final static Dimension CARD_SIZE = new Dimension(121, 69);
	
	public final static Color CUSTOM_GRAY_1 = new Color(238, 238, 238);
	
	public final static int INITIAL_PLAYER_DECK_SIZE = 48;
	public final static int OFFSET_10 = 10;
	public final static int OFFSET_30 = 30;
	
	public final static String INITIAL_NUM_RESEARCH_STATIONS = "1";
	public final static String INITIAL_NUM_OUTBREAKS = "0";
	public final static String INFECTION_RATE_TEXT = "2 2 2 3 3 4 4";
	
	public final static String BLACK_OUTLINE = "/images/cureMarkers/blackOutline.png";
	public final static String BLUE_OUTLINE = "/images/cureMarkers/blueOutline.png";
	public final static String RED_OUTLINE = "/images/cureMarkers/redOutline.png";
	public final static String YELLOW_OUTLINE = "/images/cureMarkers/yellowOutline.png";
	public final static String BLACK_CURED = "/images/cureMarkers/black.png";
	public final static String BLUE_CURED = "/images/cureMarkers/blue.png";
	public final static String RED_CURED = "/images/cureMarkers/red.png";
	public final static String YELLOW_CURED = "/images/cureMarkers/yellow.png";
	public final static String BLACK_ERADICATED = "/images/cureMarkers/blackEradicated.png";
	public final static String BLUE_ERADICATED = "/images/cureMarkers/blueEradicated.png";
	public final static String RED_ERADICATED = "/images/cureMarkers/redEradicated.png";
	public final static String YELLOW_ERADICATED = "/images/cureMarkers/yellowEradicated.png";
	
	public final static String MAP_IMG = "/images/map.png";
	public final static String BIOHAZARD_IMG = "/images/biohazard.PNG";
	public final static String INFECTION_CARD_IMG = "/images/infectionCard.png";
	public final static String OUTBREAK_IMG = "/images/outbreak.png";
	public final static String PANDEMIC_IMG = "/images/pandemic.png";
	public final static String PANDEMIC_RULES = "/files/pandemic.pdf";
	public final static String PLAYER_CARD_IMG = "/images/playerCard.png";
	public final static String RESEARCH_STATION_IMG = "/images/researchStation.png";
	public final static String BMP_FILE = ".bmp";
	
	public final static String PLAYER_DISCARD_PILE = "<html>Player Deck<br>" + "<center>Discard Pile</center></html>";
}
