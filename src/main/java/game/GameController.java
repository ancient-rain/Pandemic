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

public class GameController {

	private GameModel gameModel;
	private DiseaseController diseaseController;
	private CityController cityController;
	private AbstractDeckCardController playerDeckController;
	private AbstractDeckCardController infectionDeckController;
	private List<AbstractCharacterController> characters;
	
	public GameController(GameModel gameModel){
		this.gameModel = new GameModel();
		this.diseaseController = new DiseaseController();
		this.cityController = new CityController(this.diseaseController);
		this.playerDeckController = new PlayerDeckCardController(this.cityController);
		this.infectionDeckController = new InfectionDeckCardController(this.cityController);
		this.characters = new ArrayList<AbstractCharacterController>();
		
		this.initializeGame();
	}
	
	//TEST
	private void initializeGame(){
		this.cityController.buildResearchStation(this.cityController.getCityByName("Atlanta"));
		
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				//TODO: fix this to discard cards
				CityModel cityToInfect = this.cityController.getCityByName(this.infectionDeckController.draw().getName());
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
			c.moveWithoutCard(this.cityController.getCityByName("Atlanta"));
		}
		
		this.playerDeckController.specialShuffle(this.gameModel.getDifficulty());
	}
	
	//TEST
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
	
	//TEST
	public boolean treatCity(DiseaseModel diseaseToTreat){
		if(this.getCurrentPlayer().verifyTreat(diseaseToTreat)){
			this.getCurrentPlayer().treat(diseaseToTreat);
			this.endOfAction();
			return true;
		}
		return false;
	}
	
	//TEST
	public boolean cureDisease(Set<CardModel> cardsToCureWith, DiseaseModel diseaseToCure){
		if(this.getCurrentPlayer().verifyCure(cardsToCureWith, diseaseToCure)){
			this.getCurrentPlayer().cure(cardsToCureWith, diseaseToCure);
			if(this.diseaseController.areAllDiseasesCured()){
				this.gameModel.setWon(true);
			}
			this.endOfAction();
			return true;
		}
		return false;
	}
	
	//TEST
	public boolean buildResearchStation(){
		if(this.getCurrentPlayer().verifyBuild(this.cityController)){
			this.getCurrentPlayer().build(this.cityController);
			this.endOfAction();
			return true;
		}
		return false;
	}
	
	//TEST
	public boolean shareKnowledge(AbstractCharacterController characterToShareWith, CardModel cardToShare){
		if(this.getCurrentPlayer().verifyShareKnowledge(characterToShareWith, false)){
			this.getCurrentPlayer().shareKnowledge(characterToShareWith.getCharacterModel(), cardToShare);
			this.endOfAction();
			return true;
		}
		return false;
	}
	
	//TEST
	public boolean specialAbility(){
		if(this.getCurrentPlayer().verifyAbility(this)){
			this.getCurrentPlayer().ability(this);
			this.endOfAction();
			return true;
		}
		return false;
	}
	
	//TEST
	public void endOfAction(){
		this.gameModel.setActionsLeft(this.gameModel.getActionsLeft() - 1);
		if(this.gameModel.getActionsLeft() <= 0){
			this.endOfTurn();
		}
	}
	
	//TEST
	public void endOfTurn(){
		if(this.playerDeckController.getNumberOfCardsInDeck() == 0){
			this.gameModel.setLost(true);
			return;
		}
		CardModel drawnCard1 = this.playerDeckController.draw();
		if(this.playerDeckController.getNumberOfCardsInDeck() == 0){
			this.gameModel.setLost(true);
			return;
		}
		CardModel drawnCard2 = this.playerDeckController.draw();
		if(drawnCard1.getName().equals("Epidemic")){
			this.handleEpidemic();
		} else {
			this.getCurrentPlayer().addCardToHandOfCards(drawnCard1);
		}
		if(drawnCard1.getName().equals("Epidemic")){
			this.handleEpidemic();
		} else {
			this.getCurrentPlayer().addCardToHandOfCards(drawnCard2);
		}
		int numberOfInfections = this.gameModel.getInfectionRates()[this.gameModel.getInfectionRateIndex()];
		if(this.gameModel.getQuietNightsLeft() > 0){
			numberOfInfections = 1;
		}
		for(int i = 0; i < numberOfInfections; i++){
			CardModel infectionCard = this.infectionDeckController.draw();
			this.infect(this.cityController.getCityByName(infectionCard.getName()), this.cityController.getCityByName(infectionCard.getName()).getPrimaryDisease());
			this.infectionDeckController.discard(infectionCard);
		}
		this.cityController.clearOutbrokenCities();
		this.gameModel.setQuietNightsLeft(this.gameModel.getQuietNightsLeft() - 1);
		this.getCurrentPlayer().endTurn();
		this.gameModel.setTurnCounter(this.gameModel.getTurnCounter() + 1);
	}
	
	//TEST
	public void handleEpidemic(){
		if(this.gameModel.getInfectionRateIndex() < this.gameModel.getInfectionRates().length - 1){
			this.gameModel.setInfectionRateIndex(this.gameModel.getInfectionRateIndex() + 1);
		}
		CardModel bottom = ((InfectionDeckCardController)this.infectionDeckController).drawBottomCard();
		this.infect(this.cityController.getCityByName(bottom.getName()), this.cityController.getCityByName(bottom.getName()).getPrimaryDisease());
		this.infect(this.cityController.getCityByName(bottom.getName()), this.cityController.getCityByName(bottom.getName()).getPrimaryDisease());
		this.infect(this.cityController.getCityByName(bottom.getName()), this.cityController.getCityByName(bottom.getName()).getPrimaryDisease());
		this.infectionDeckController.discard(bottom);
		
		this.infectionDeckController.specialShuffle(0);
		this.gameModel.setInfectionRateIndex(this.gameModel.getInfectionRateIndex() + 1);
	}
	
	private void infect(CityModel cityToInfect, DiseaseModel diseaseInfecting){
		if(!diseaseInfecting.isEradicated() && !cityToInfect.isQuarentined()){
			this.cityController.infect(cityToInfect, diseaseInfecting);
			if(diseaseInfecting.getCubesLeft() < 0 || this.cityController.getOutbreakCoutner() > 7){
				this.gameModel.setLost(true);
			}
		}
	}
	
	//TEST
	public boolean playEventCard(CardModel eventCardToPlay){
		switch(eventCardToPlay.getName()){
		case "Airlift":
			return this.playAirlift(this.gameModel.getCharacterToBeAirlifted(), this.gameModel.getCityForEvent());
		case "Forecast":
			return this.playForecast();
		case "Government Grant":
			return this.playGovernmentGrant(this.gameModel.getCityForEvent());
		case "One Quiet Night":
			return this.playOneQuietNight();
		case "Resilient Population": 
			return this.playResilientPopulation(this.gameModel.getCardToRemoveFromInfectionDeck());
		default:
			return false;
		}
	}
	
	//TEST
	private boolean playAirlift(AbstractCharacterController characterToMove, CityModel cityToMoveTo){
		characterToMove.getCharacterModel().setCurrentCity(cityToMoveTo);
		return true;
	}
	
	//TEST
	private boolean playForecast(){
		this.gameModel.setForecastCardsLeft(6);
		List<CardModel> toReturn = new ArrayList<CardModel>();
		for(int i = 0; i < this.gameModel.getForecastCardsLeft(); i++){
			toReturn.add(this.infectionDeckController.draw());
		}
		this.gameModel.setForecastCards(toReturn);
		return true;
	}
	
	//TEST
	public boolean forecastReturnCard(CardModel cardToPutBack){
		if(this.gameModel.getForecastCardsLeft() > 0){
			((InfectionDeckCardController)this.infectionDeckController).addToTop(cardToPutBack);
			this.gameModel.setForecastCardsLeft(this.gameModel.getForecastCardsLeft() - 1);
			return true;
		}
		return false;
	}
	
	//TEST
	private boolean playGovernmentGrant(CityModel cityToAddResearchStation){
		if(this.cityController.getResearchStationCounter() < 6 && !cityToAddResearchStation.isHasResearchStation()){
			cityToAddResearchStation.setHasResearchStation(true);
			return true;
		}
		return false;
	}
	
	//TEST
	private boolean playOneQuietNight(){
		this.gameModel.setQuietNightsLeft(this.characters.size());
		return false;
	}
	
	//TEST
	private boolean playResilientPopulation(CardModel cardToRemove){
		return this.infectionDeckController.getDiscardedCards().remove(cardToRemove);
	}
	
	public boolean checkForLoss(){
		return this.gameModel.isLost();
	}
	
	public boolean checkForWin(){
		return this.gameModel.isWon();
	}
	
	public GameModel getGameModel(){
		return this.gameModel;
	}
	
	public CityController getCityController(){
		return this.cityController;
	}
	
	public AbstractCharacterController getCurrentPlayer(){
		return this.characters.get(this.gameModel.getTurnCounter() % this.characters.size());
	}
}
