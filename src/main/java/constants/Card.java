package constants;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

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

	public static String EPIDEMIC = "Epidemic";
	public static String AIRLIFT = "Airlift";
	public static String FORECAST = "Forecast";
	public static String GOVERNMENT_GRANT = "Government Grant";
	public static String ONE_QUIET_NIGHT = "One Quiet Night";
	public static String RESILIENT_POPULATION = "Resilient Population";
	
	final static String MEDIC_CARD = "images/characters/cards/medic.png";
	final static String DISPATCHER_CARD = "images/characters/cards/dispatcher.png";
	final static String QUARANTINE_SPECIALIST_CARD = "images/characters/cards/quarantineSpecialist.png";
	final static String CONTINGENCY_PLANNER_CARD = "images/characters/cards/contingencyPlanner.png";
	final static String RESEARCHER_CARD = "images/characters/cards/researcher.png";
	final static String SCIENTIST_CARD = "images/characters/cards/scientist.png";
	final static String OPERATIONS_EXPERT_CARD = "images/characters/cards/operationsExpert.png";

	public static final List<String> CHARACTERS_CARD_LIST = new ArrayList<>(
			Arrays.asList(MEDIC_CARD, DISPATCHER_CARD, QUARANTINE_SPECIALIST_CARD, CONTINGENCY_PLANNER_CARD,
					RESEARCHER_CARD, SCIENTIST_CARD, OPERATIONS_EXPERT_CARD));

	
	public static void updateConstants(ResourceBundle resourceBundle) {
		EPIDEMIC = resourceBundle.getString("EPIDEMIC");
		AIRLIFT = resourceBundle.getString("AIRLIFT");
		FORECAST = resourceBundle.getString("FORECAST");
		GOVERNMENT_GRANT = resourceBundle.getString("GOVERNMENT_GRANT");
		ONE_QUIET_NIGHT = resourceBundle.getString("ONE_QUIET_NIGHT");
		RESILIENT_POPULATION = resourceBundle.getString("RESILIENT_POPULATION");
	}
}
