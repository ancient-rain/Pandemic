package characters;

import game.GameController;

public class ResearcherCharacterController extends AbstractCharacterController{
	CharacterModel character;
	
	public ResearcherCharacterController(CharacterModel character) {
		super(character);
		this.character = character;
	}

	@Override 
	public boolean verifyShareKnowledge(AbstractCharacterController otherChar, boolean doneOtherDir){
		return this.character.getCurrentCity().equals(otherChar.getCharacterModel().getCurrentCity());
	}
	
	@Override
	public boolean verifyAbility(GameController gameController) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void ability(GameController gameController) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endTurn() {
	}

}
