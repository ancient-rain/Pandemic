package game;

import java.util.ArrayList;
import java.util.List;

import cards.CardModel;
import characters.AbstractCharacterController;
import characters.CharacterModel;
import city.CityModel;

public class GameModel {
	private List<CharacterModel> characters;
	private int turnCounter;
	private int numberOfStartingCards;
	private int difficulty;
	private int actionsLeft;
	private int infectionRateIndex;
	private int[] infectionRates;
	private boolean lost;
	private boolean won;
	private int forecastCardsLeft;
	private int quietNightsLeft;
	private AbstractCharacterController characterToBeAirlifted;
	private CityModel cityForEvent;
	private CardModel cardToRemoveFromInfectionDeck;
	private List<CardModel> forecastCards;
	private CardModel selectedContingencyPlan;
	
	public GameModel(){
		this.characters = new ArrayList<CharacterModel>();
		this.turnCounter = 0;
		this.numberOfStartingCards = 0;
		this.difficulty = 1;
		this.actionsLeft = 4;
		this.infectionRateIndex = 0;
		this.infectionRates = new int[] {2, 2, 2, 3, 3, 4, 4};
		this.lost = false;
		this.won = false;
		this.forecastCardsLeft = 0;
		this.quietNightsLeft = 0;
		this.characterToBeAirlifted = null;
		this.cityForEvent = null;
		this.cardToRemoveFromInfectionDeck = null;
		this.forecastCards = new ArrayList<CardModel>();
		this.selectedContingencyPlan = null;
	}
	
	public List<CharacterModel> getCharacters(){
		return this.characters;
	}
	
	public int getTurnCounter(){
		return this.turnCounter;
	}
	
	public void setTurnCounter(int turnCounter){
		this.turnCounter = turnCounter;
	}
	
	public int getNumberOfStartingCards(){
		return this.numberOfStartingCards;
	}
	
	public int getDifficulty(){
		return this.difficulty;
	}
	
	public int getActionsLeft(){
		return this.actionsLeft;
	}
	
	public void setActionsLeft(int actionsLeft){
		this.actionsLeft = actionsLeft;
	}
	
	public int getInfectionRateIndex(){
		return this.infectionRateIndex;
	}
	
	public void setInfectionRateIndex(int infectionRateIndex){
		this.infectionRateIndex = infectionRateIndex;
	}
	
	public int[] getInfectionRates(){
		return this.infectionRates;
	}
	
	public boolean isLost(){
		return this.lost;
	}
	
	public void setLost(boolean lost){
		this.lost = lost;
	}
	
	public boolean isWon(){
		return this.won;
	}
	
	public void setWon(boolean won){
		this.won = won;
	}
	
	public int getForecastCardsLeft(){
		return this.forecastCardsLeft;
	}
	
	public void setForecastCardsLeft(int forecastCardsLeft){
		this.forecastCardsLeft = forecastCardsLeft;
	}
	
	public int getQuietNightsLeft(){
		return this.quietNightsLeft;
	}
	
	public void setQuietNightsLeft(int quietNightsLeft){
		this.quietNightsLeft = quietNightsLeft;
	}
	
	public AbstractCharacterController getCharacterToBeAirlifted(){
		return this.characterToBeAirlifted;
	}
	
	public void setCharacterToBeAirlifted(AbstractCharacterController characterToBeAirlifted){
		this.characterToBeAirlifted = characterToBeAirlifted;
	}
	
	public CityModel getCityForEvent(){
		return this.cityForEvent;
	}
	
	public void setCityForEvent(CityModel cityToBeUsedInEvent){
		this.cityForEvent = cityToBeUsedInEvent;
	}
	
	public CardModel getCardToRemoveFromInfectionDeck(){
		return this.cardToRemoveFromInfectionDeck;
	}
	
	public void setCardToRemoveFromInfectionDeck(CardModel cardToRemoveFromInfectionDeck){
		this.cardToRemoveFromInfectionDeck = cardToRemoveFromInfectionDeck;
	}
	
	public List<CardModel> getForecastCards(){
		return this.forecastCards;
	}
	
	public void setForecastCards(List<CardModel> forecastCards){
		this.forecastCards = forecastCards;
	}

	public CardModel getSelectedContingencyPlan() {
		return selectedContingencyPlan;
	}

	public void setSelectedContingencyPlan(CardModel selectedContingencyPlan) {
		this.selectedContingencyPlan = selectedContingencyPlan;
	}
	
	public void setDifficulty(int difficulty){
		this.difficulty = difficulty;
	}
	
	public void setCharacters(List<CharacterModel> characters){
		this.characters = characters;
	}
}