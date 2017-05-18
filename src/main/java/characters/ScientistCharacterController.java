package characters;

import java.util.Set;

import cards.CardModel;
import diseases.DiseaseModel;
import game.GameController;

public class ScientistCharacterController extends AbstractCharacterController{

	public ScientistCharacterController(CharacterModel character) {
		super(character);
	}

	@Override 
	public boolean verifyCure(Set<CardModel> cardsToCureWith, DiseaseModel diseaseToCure){
		if(cardsToCureWith.size() < 4 || diseaseToCure.isCured()){
			return false;
		}
		for(CardModel c : cardsToCureWith){
			if(!this.character.isInHand(c)){
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean verifyAbility(GameController gameController) {
		return false;
	}

	@Override
	public void ability(GameController gameController) {
	}

}
