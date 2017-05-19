package characters;

import game.GameController;

public class ContingencyPlannerCharacterController extends AbstractCharacterController{

	
	public ContingencyPlannerCharacterController(CharacterModel character) {
		super(character);
	}
	
	@Override
	public boolean verifyAbility(GameController gameController) {
		return gameController.getGameModel().getSelectedContingencyPlan().getName().equals("") && 
				!gameController.getGameModel().getSelectedCard().getName().equals("");
	}

	@Override
	public void ability(GameController gameController) {
		gameController.getGameModel().setSelectedContingencyPlan(
				gameController.getGameModel().getSelectedCard());
	}
	
}
