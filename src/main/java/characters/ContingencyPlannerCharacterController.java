package characters;

import cards.CardModel;
import game.GameController;

public class ContingencyPlannerCharacterController extends AbstractCharacterController{

	private CardModel contingencyPlan;
	
	public ContingencyPlannerCharacterController(CharacterModel character) {
		super(character);
		this.contingencyPlan = new CardModel("", CardModel.CardType.EVENT);
		
	}

	@Override
	public boolean verifyAbility(GameController gameController) {
		
		return false;
	}

	@Override
	public void ability(GameController gameController) {
		if(this.contingencyPlan.getName().equals("")){
			this.contingencyPlan = gameController.getGameModel().getSelectedContingencyPlan();
		} else {
			gameController.playEventCard(contingencyPlan);
			this.contingencyPlan = new CardModel("", CardModel.CardType.EVENT);
		}
	}

	@Override
	public void endTurn() {
	}

	//@Override
	//anything having to do with his hand
}
