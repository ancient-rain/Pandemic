package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cards.AbstractDeckCardController;
import cards.CardModel;
import cards.InfectionDeckCardController;
import cards.PlayerDeckCardController;
import characters.AbstractCharacterController;
import characters.CharacterControllerFactory;
import characters.CharacterModel;
import city.CityController;
import city.CityModel;
import diseases.DiseaseController;
import diseases.DiseaseModel;

import static constants.City.*;
import static constants.Game.*;
import static constants.Card.*;

public class GameController {

	private GameModel gameModel;
	private DiseaseController diseaseController;
	private CityController cityController;
	private AbstractDeckCardController playerDeckController;
	private AbstractDeckCardController infectionDeckController;
	private List<AbstractCharacterController> characters;
	
	public GameController(GameModel gameModel,
			DiseaseController diseaseController,
			CityController cityController,
			AbstractDeckCardController playerDeckController,
			AbstractDeckCardController infectionDeckController){
		this.gameModel = gameModel;
		this.diseaseController = diseaseController;
		this.cityController = cityController;
		this.playerDeckController = playerDeckController;
		this.infectionDeckController = infectionDeckController;
		this.characters = new ArrayList<AbstractCharacterController>();
		
		this.initializeGame();
	}	
	
	private void initializeGame(){
		this.cityController.buildResearchStation(this.cityController.getCityByName("Atlanta"));
		
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				CityModel cityToInfect = this.cityController.getCityByName(
						this.infectionDeckController.draw().getName());
				this.infectionDeckController.discard(
						this.infectionDeckController.getCityToCardMap().get(cityToInfect));
				for(int k = j; k < 3; k++){
					this.cityController.infect(cityToInfect, cityToInfect.getPrimaryDisease());
				}
			}
		}
		
		CharacterControllerFactory characterFactory = new CharacterControllerFactory();
		
		for(CharacterModel c : this.gameModel.getCharacters()){
			this.characters.add(characterFactory.createCharacterController(c));
		}
		
		for(AbstractCharacterController c : this.characters){
			for(int i = 0; i < this.gameModel.getNumberOfStartingCards(); i++){
				c.addCardToHandOfCards(this.playerDeckController.draw());
			}
		}
		
		this.playerDeckController.specialShuffle(this.gameModel.getDifficulty());
	}
	
	public boolean moveCharacter(AbstractCharacterController characterToMove, CityModel cityToMoveTo){
		boolean moved = false;
		if(characterToMove.verifyMoveWithoutCard(cityToMoveTo)){
			characterToMove.moveWithoutCard(cityToMoveTo);
			this.gameModel.setActionsLeft(this.gameModel.getActionsLeft() - 1);
			moved = true;
		} else {
			for(CardModel c : characterToMove.getCharacterModel().getHandOfCards()){
				if(characterToMove.verifyMoveWithCard(cityToMoveTo, c)){
					characterToMove.moveWithCard(cityToMoveTo, c);
					this.gameModel.setActionsLeft(this.gameModel.getActionsLeft() - 1);
					moved = true;
					break;
				}
			}
		}
		if(this.gameModel.getActionsLeft() <= 0){
			this.endOfTurn();
		}
		return moved;
	}
	
	public boolean treatCity(DiseaseModel diseaseToTreat){
		if(this.getCurrentPlayer().verifyDiseaseCanBeTreated(diseaseToTreat)){
			this.getCurrentPlayer().treat(diseaseToTreat);
			this.endOfAction();
			return true;
		}
		return false;
	}
	
	public boolean cureDisease(Set<CardModel> cardsToCureWith, DiseaseModel diseaseToCure){
		if((this.getCurrentPlayer().verifyCure(cardsToCureWith, diseaseToCure))){
			this.getCurrentPlayer().cure(cardsToCureWith, diseaseToCure);
			if(this.diseaseController.areAllDiseasesCured()){
				this.gameModel.setWon(true);
			}
			this.endOfAction();
			return true;
		}
		return false;
	}
	
	public boolean buildResearchStation(){
		if(this.getCurrentPlayer().verifyBuild(this.cityController)){
			this.getCurrentPlayer().build(this.cityController);
			this.endOfAction();
			return true;
		}
		return false;
	}
	
	public boolean shareKnowledge(AbstractCharacterController characterToShareWith, CardModel cardToShare){
		if(this.getCurrentPlayer().verifyShareKnowledge(characterToShareWith, false)){
			this.getCurrentPlayer().shareKnowledge(characterToShareWith.getCharacterModel(), cardToShare);
			this.endOfAction();
			return true;
		}
		return false;
	}
	
	public boolean specialAbility(){
		if(this.getCurrentPlayer().verifyAbility(this)){
			this.getCurrentPlayer().ability(this);
			this.endOfAction();
			return true;
		}
		return false;
	}
	
	public void endOfAction(){
		this.gameModel.setActionsLeft(this.gameModel.getActionsLeft() - 1);
		if(this.gameModel.getActionsLeft() <= 0){
			this.endOfTurn();
		}
	}
	
	public void endOfTurn(){
		if(this.playerDeckController.getNumberOfCardsInDeck() == 0) {
			this.gameModel.setLost(true);
			return;
		}
		
		CardModel drawnCard1 = this.playerDeckController.draw();
		if(this.playerDeckController.getNumberOfCardsInDeck() == 0) {
			this.gameModel.setLost(true);
			return;
		}
		
		CardModel drawnCard2 = this.playerDeckController.draw();
		if(drawnCard1.getName().equals(EPIDEMIC)){
			this.handleEpidemic();
			this.infectionDeckController.specialShuffle(0);
			this.playerDeckController.discard(drawnCard1);
		} else {
			this.getCurrentPlayer().addCardToHandOfCards(drawnCard1);
		}
		
		if(drawnCard2.getName().equals(EPIDEMIC)){
			this.handleEpidemic();
			this.infectionDeckController.specialShuffle(0);
			this.playerDeckController.discard(drawnCard2);
		} else {
			this.getCurrentPlayer().addCardToHandOfCards(drawnCard2);
		}
		
		int numberOfInfections = 0;
		if(this.gameModel.getInfectionRateIndex() < MAX_NUM_OUTBREAKS) {
			numberOfInfections = this.gameModel.getInfectionRates()[this.gameModel.getInfectionRateIndex()];
		} else {
			numberOfInfections = NUM_INFECTIONS;
		}
		
		if(this.gameModel.getQuietNightsLeft() > 0){
			numberOfInfections = 1;
		}
		
		for (int i = 0; i < numberOfInfections; i++){
			CardModel infectionCard = this.infectionDeckController.draw();
			this.infect(this.cityController.getCityByName(infectionCard.getName()), 
					this.cityController.getCityByName(infectionCard.getName()).getPrimaryDisease());
			this.infectionDeckController.discard(infectionCard);
		}
		
		this.cityController.clearOutbrokenCities();
		this.gameModel.setQuietNightsLeft(this.gameModel.getQuietNightsLeft() - 1);
		this.gameModel.setActionsLeft(ACTION_COUNT);
		this.gameModel.setTurnCounter(this.gameModel.getTurnCounter() + 1);
	}
	
	public void handleEpidemic(){
		if(this.gameModel.getInfectionRateIndex() < this.gameModel.getInfectionRates().length - 1){
			this.gameModel.setInfectionRateIndex(this.gameModel.getInfectionRateIndex() + 1);
		}
		CardModel bottom = ((InfectionDeckCardController)this.infectionDeckController).drawBottomCard();
		this.infect(this.cityController.getCityByName(bottom.getName()), 
				this.cityController.getCityByName(bottom.getName()).getPrimaryDisease());
		this.infect(this.cityController.getCityByName(bottom.getName()), 
				this.cityController.getCityByName(bottom.getName()).getPrimaryDisease());
		this.infect(this.cityController.getCityByName(bottom.getName()), 
				this.cityController.getCityByName(bottom.getName()).getPrimaryDisease());
		this.infectionDeckController.discard(bottom);
	}
	
	private void infect(CityModel cityToInfect, DiseaseModel diseaseInfecting){
		if(!diseaseInfecting.isEradicated() && !cityToInfect.isQuarentined()){
			this.cityController.infect(cityToInfect, diseaseInfecting);
			if(diseaseInfecting.getCubesLeft() < 0 || 
					this.cityController.getOutbreakCoutner() > MAX_NUM_OUTBREAKS){
				this.gameModel.setLost(true);
			}
		}
	}
	
	public boolean playEventCard(CardModel eventCardToPlay) {
		String role = eventCardToPlay.getName();
		boolean playedEvent = false;
		
		if(role.equals("Airlift")) {
			playedEvent = this.playAirlift(this.gameModel.getSelectedCharacter(), 
					this.gameModel.getSelectedCity());
		} else if(role.equals("Forecast")) {
			playedEvent = this.playForecast();
		} else if(role.equals("Government Grant")) {
			playedEvent = this.playGovernmentGrant(this.gameModel.getSelectedCity());
		} else if(role.equals("One Quiet Night")){
			playedEvent = this.playOneQuietNight();
		} else if(role.equals("Resilient Population")){
			playedEvent = this.playResilientPopulation(this.gameModel.getCardToRemoveFromInfectionDeck());
		}

		if (playedEvent) {
			removeEventCardFromHand(eventCardToPlay);
			this.playerDeckController.discard(eventCardToPlay);
		}
		return playedEvent;
	}
	
	public void removeEventCardFromHand(CardModel eventCardToPlay) {
		for(int i = 0; i < this.getPlayers().size(); i++){
			if(this.getPlayers().get(i).getCharacterModel().getHandOfCards().contains(eventCardToPlay)){
				this.getPlayers().get(i).getCharacterModel().removeCardFromHandOfCards(eventCardToPlay);
			}
		}
	}

	private boolean playAirlift(AbstractCharacterController characterToMove, CityModel cityToMoveTo){
		characterToMove.getCharacterModel().setCurrentCity(cityToMoveTo);
		return true;
	}
	
	private boolean playForecast() {
		this.gameModel.setForecastCardsLeft(FORECAST_CARDS);
		List<CardModel> toReturn = new ArrayList<CardModel>();
		for(int i = 0; i < this.gameModel.getForecastCardsLeft(); i++){
			toReturn.add(this.infectionDeckController.draw());
		}
		this.gameModel.setForecastCards(toReturn);
		return true;
	}
	
	public boolean forecastReturnCard(CardModel cardToPutBack) {
		if(this.gameModel.getForecastCardsLeft() > 0){
			((InfectionDeckCardController)this.infectionDeckController).addToTop(cardToPutBack);
			this.gameModel.setForecastCardsLeft(this.gameModel.getForecastCardsLeft() - 1);
			return true;
		}
		return false;
	}
	
	private boolean playGovernmentGrant(CityModel cityToAddResearchStation) {
		if(this.cityController.getResearchStationCounter() < MAX_RESEARCH_COUNT 
				&& !cityToAddResearchStation.hasResearchStation()){
			cityToAddResearchStation.setHasResearchStation(true);
			return true;
		}
		return false;
	}
	
	private boolean playOneQuietNight() {
		this.gameModel.setQuietNightsLeft(this.characters.size());
		return true;
	}
	
	private boolean playResilientPopulation(CardModel cardToRemove) {
		return this.getInfectionDeckController().getDiscardedCards().remove(cardToRemove);
	}
	
	public boolean checkForLoss() {
		return this.gameModel.isLost();
	}
	
	public boolean checkForWin() {
		return this.gameModel.isWon();
	}
	
	public GameModel getGameModel() {
		return this.gameModel;
	}
	
	public CityController getCityController() {
		return this.cityController;
	}
	
	public AbstractCharacterController getCurrentPlayer() {
		return this.characters.get(this.gameModel.getTurnCounter() % this.characters.size());
	}
	
	public List<AbstractCharacterController> getPlayers() {
		return this.characters;
	}
	
	public DiseaseController getDiseaseController() {
		return this.diseaseController;
	}
	
	public AbstractDeckCardController getPlayerDeckController() {
		return this.playerDeckController;
	}
	
	public AbstractDeckCardController getInfectionDeckController() {
		return this.infectionDeckController;
	}

	public void setPlayerDeck(PlayerDeckCardController deckController) {
		this.playerDeckController = deckController;
	}

	public void setInfectionDeck(InfectionDeckCardController infDeckController) {
		this.infectionDeckController = infDeckController;
	}
	
	public CardModel cardNameToCard(String cardName, List<CardModel> listOfTopCards) {
		for(int i = 0; i < listOfTopCards.size();i++){
			if(listOfTopCards.get(i).getName().equals(cardName)){
				return listOfTopCards.get(i);
			}
		}
		return null;
	}
}