package characters;

import city.CityModel;
import game.GameController;

public class QuarentineSpecialistCharacterController extends AbstractCharacterController {

	public QuarentineSpecialistCharacterController(CharacterModel character) {
		super(character);
		this.character.setCurrentCityQuarentined(true);
		for(CityModel c : this.character.getCurrentNeighbors()){
			c.setQuarentined(true);
		}
	}
	
	@Override
	public void moveWithoutCard(CityModel cityToMoveTo){
		this.character.setCurrentCityQuarentined(false);
		for(CityModel c : this.character.getCurrentNeighbors()){
			c.setQuarentined(false);
		}
		this.character.setCurrentCity(cityToMoveTo);
		this.character.setCurrentCityQuarentined(true);
		for(CityModel c : this.character.getCurrentNeighbors()){
			c.setQuarentined(true);
		}
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
	}

}
