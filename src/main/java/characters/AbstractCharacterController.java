package characters;

import java.util.Set;

import cards.CardModel;
import city.CityController;
import city.CityModel;
import diseases.DiseaseModel;
import game.GameController;

public abstract class AbstractCharacterController {

	protected CharacterModel character;
	
	public AbstractCharacterController(CharacterModel character){
		this.character = character;
	}
	
	public CharacterModel getCharacterModel(){
		return this.character;
	}
	
	public CityModel getCharactersCurrentCity(){
		return this.character.getCurrentCity();
	}
	
	public boolean verifyMoveWithoutCard(CityModel cityToMoveTo){
		return this.character.getCurrentNeighbors().contains(cityToMoveTo) 
				|| (this.character.isAtResearchStation() && cityToMoveTo.hasResearchStation());

	}
	
	public void moveWithoutCard(CityModel cityToMoveTo){
		this.character.setCurrentCity(cityToMoveTo);
	}
	
	public boolean verifyMoveWithCard(CityModel cityToMoveTo, CardModel cardToMoveWith){
		return this.hasCardForCurrentCity() || cardToMoveWith.sharesName(cityToMoveTo);
	}
	
	public void moveWithCard(CityModel cityToMoveTo, CardModel cardToMoveWith){
		this.character.removeCardFromHandOfCards(cardToMoveWith);
		this.moveWithoutCard(cityToMoveTo);
	}
	
	public boolean verifyTreat(DiseaseModel diseaseToTreat){
		return this.character.getCubesByDiseaseOnCurrentCity(diseaseToTreat) > 0;
	}
	
	public void treat(DiseaseModel diseaseToTreat){
		if(diseaseToTreat.isCured()){
			diseaseToTreat.addToCubesLeft(this.character.getCubesByDiseaseOnCurrentCity(diseaseToTreat));
			this.character.setCubesByDiseaseOnCurrentCity(diseaseToTreat, 0);
			if(diseaseToTreat.getCubesLeft() == 24){
				diseaseToTreat.setEradicated(true);
			}
		} else {
			diseaseToTreat.addToCubesLeft(1);
			this.character.removeCubesByDiseaseOnCurrentCity(diseaseToTreat, 1);
		}
	}
	
	public boolean verifyCure(Set<CardModel> cardsToCureWith, DiseaseModel diseaseToCure){
		if(cardsToCureWith.size() < 5 || diseaseToCure.isCured()){
			return false;
		}
		for(CardModel c : cardsToCureWith){
			if(!this.character.isInHand(c)){
				return false;
			}
		}
		return true;
	}
	
	public void cure(Set<CardModel> cardsToCureWith, DiseaseModel diseaseToCure){
		for(CardModel c : cardsToCureWith){
			this.character.removeCardFromHandOfCards(c);
		}
		diseaseToCure.setCured(true);
		if(diseaseToCure.getCubesLeft() == 24){
			diseaseToCure.setEradicated(true);
		}
	}
	
	public boolean verifyBuild(CityController cityController){
		return this.hasCardForCurrentCity() && cityController.getResearchStationCounter() < 6;
	}
	
	public void build(CityController cityController){
		for(CardModel c : this.character.getHandOfCards()){
			if(c.sharesName(this.character.getCurrentCity())){
				this.character.removeCardFromHandOfCards(c);
			}
		}
		this.character.setHasResearchStationAtCurrentCity(true);
		cityController.setResearchStationCounter(cityController.getResearchStationCounter() + 1);
	}
	
	public boolean verifyShareKnowledge(AbstractCharacterController characterToShareWith, boolean checkedOneDirection){
		return (this.hasCardForCurrentCity() && characterToShareWith.getCharactersCurrentCity().equals(this.character.getCurrentCity())) || (!checkedOneDirection && characterToShareWith.verifyShareKnowledge(this, true));
	}
	
	public void shareKnowledge(CharacterModel characterToShareWith, CardModel cardToShare){
		if(this.character.isInHand(cardToShare)){
			this.character.removeCardFromHandOfCards(cardToShare);
			characterToShareWith.addCardToHandOfCards(cardToShare);
		} else {
			this.character.addCardToHandOfCards(cardToShare);
			characterToShareWith.removeCardFromHandOfCards(cardToShare);
		}
	}
	
	public abstract boolean verifyAbility(GameController gameController);
	public abstract void ability(GameController gameController);
	public abstract void endTurn();
	
	protected boolean hasCardForCurrentCity(){
		for(CardModel c : character.getHandOfCards()){
			if(c.sharesName(character.getCurrentCity())){
				return true;
			}
		}
		return false;
	}
	
	public void addCardToHandOfCards(CardModel cardToAdd){
		this.character.addCardToHandOfCards(cardToAdd);
	}
}
