package characters;

import cards.CardModel;
import city.CityController;
import city.CityModel;
import game.GameController;

public class OperationsExpertCharacterController extends AbstractCharacterController{

	private boolean movedFromResearchStationWithCard;
	
	public OperationsExpertCharacterController(CharacterModel character) {
		super(character);
		this.movedFromResearchStationWithCard = false;
	}


	public boolean verifyMoveWithCard(CityModel cityToMoveTo, CardModel cardToMoveWith){
		return this.hasCardForCurrentCity() || cardToMoveWith.sharesName(cityToMoveTo) || (!this.movedFromResearchStationWithCard && this.character.isAtResearchStation());
	}
	
	@Override
	public void moveWithCard(CityModel cityToMoveTo, CardModel cardToMoveWith){
		if(this.character.isAtResearchStation() && !cardToMoveWith.sharesName(cityToMoveTo) && cardToMoveWith.sharesName(this.character.getCurrentCity())){
			this.movedFromResearchStationWithCard = true;
		}
		this.character.removeCardFromHandOfCards(cardToMoveWith);
		this.character.setCurrentCity(cityToMoveTo);
	}
	
	@Override
	public boolean verifyBuild(CityController cityController){
		return cityController.getResearchStationCounter() < 6;
	}
	
	@Override
	public void build(CityController cityController){
		this.character.setHasResearchStationAtCurrentCity(true);
		cityController.incrementResearchStationCounter(1);
	}
	
	@Override
	public boolean verifyAbility(GameController gameController) {
		return false;
	}

	@Override
	public void ability(GameController gameController) {
	}

	@Override
	public void endTurn() {
		this.movedFromResearchStationWithCard = false;
	}

}