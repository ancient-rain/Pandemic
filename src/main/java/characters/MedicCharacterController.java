package characters;

import city.CityModel;
import diseases.DiseaseModel;
import game.GameController;

public class MedicCharacterController extends AbstractCharacterController{

	public MedicCharacterController(CharacterModel character) {
		super(character);
	}
	
	@Override
	public void moveWithoutCard(CityModel cityToMoveTo){
		this.character.setCurrentCity(cityToMoveTo);
		for(DiseaseModel d : cityToMoveTo.getDiseases()){
			if(d.isCured()){
				d.addToCubesLeft(cityToMoveTo.getCubesByDisease(d));
				cityToMoveTo.setCubesByDisease(d, 0);
				if(d.getCubesLeft() == 24){
					d.setEradicated(true);
				}
			}
		}
	}
	
	@Override
	public void treat(DiseaseModel diseaseToTreat){
		diseaseToTreat.addToCubesLeft(this.character.getCubesByDiseaseOnCurrentCity(diseaseToTreat));
		this.character.setCubesByDiseaseOnCurrentCity(diseaseToTreat, 0);
	}

	@Override
	public boolean verifyAbility(GameController gameController) {
		return false;
	}

	@Override
	public void ability(GameController gameController) {
	}

}
