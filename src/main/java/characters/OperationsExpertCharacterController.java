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
	
	public boolean getMovedFromResearchStationWithcard(){
		return this.movedFromResearchStationWithCard;
	}

	public boolean verifyMoveWithCard(CityModel cityToMoveTo, CardModel cardToMoveWith){
		if(this.hasCardForCurrentCity()){
			return true;
		} else if(cardToMoveWith.sharesName(cityToMoveTo)){
			return true;
		} else if(!this.movedFromResearchStationWithCard) {
			if(this.character.isAtResearchStation()){
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	@Override
	public void moveWithCard(CityModel cityToMoveTo, CardModel cardToMoveWith){
		//System.out.println(cardToMoveWith.getName());
		//System.out.println(this.character.getCurrentCity().getName());
		if(this.character.isAtResearchStation()){
			if(!cardToMoveWith.sharesName(cityToMoveTo)){
				if(cardToMoveWith.sharesName(this.character.getCurrentCity())){
					this.movedFromResearchStationWithCard = true;
				}
			}
		}
		// ask if this should be inside the statements
		this.character.removeCardFromHandOfCards(cardToMoveWith);
		this.character.setCurrentCity(cityToMoveTo);
	}
	
	@Override
	public boolean verifyBuild(CityController cityController){
		if(cityController.getResearchStationCounter() < 6){
			if(!this.character.isAtResearchStation()){
				return true;
			}
		}
		return false;
		
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
