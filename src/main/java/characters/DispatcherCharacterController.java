package characters;

import game.GameController;

public class DispatcherCharacterController extends AbstractCharacterController{

	public DispatcherCharacterController(CharacterModel character) {
		super(character);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean verifyAbility(GameController gameController) {
		//Verify the normal way of moving + is there a player on that spot
		return false;
	}

	@Override
	public void ability(GameController gameController) {
		//Move Players normally or to other players
		
	}

	@Override
	public void endTurn() {
		// TODO Auto-generated method stub
		
	}

}
