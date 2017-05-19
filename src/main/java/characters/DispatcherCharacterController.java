package characters;

import java.util.HashSet;
import java.util.Set;

import cards.CardModel;
import city.CityModel;
import game.GameController;

public class DispatcherCharacterController extends AbstractCharacterController{

	public DispatcherCharacterController(CharacterModel character) {
		super(character);
	}

	@Override
	public boolean verifyAbility(GameController gameController) {
		AbstractCharacterController characterToMove = gameController.getGameModel().getSelectedCharacter();
		CityModel cityToMoveTo = gameController.getGameModel().getSelectedCity();
		
		if(characterToMove.verifyMoveWithoutCard(cityToMoveTo)){
			return true;
		}
		
		Set<AbstractCharacterController> otherCharacters = 
				new HashSet<AbstractCharacterController>(gameController.getPlayers());
		otherCharacters.remove(characterToMove);
		
		for(AbstractCharacterController otherCharacter : otherCharacters){
			if(otherCharacter.getCharacterModel().getCurrentCity().equals(cityToMoveTo)){
				return true;
			}
		}
		
		for(CardModel cardToMoveWith : this.character.getHandOfCards()){
			if(characterToMove.verifyMoveWithCard(cityToMoveTo, cardToMoveWith)){
				return true;
			}
		}
		return false;
	}

	@Override
	public void ability(GameController gameController) {
		AbstractCharacterController characterToMove = gameController.getGameModel().getSelectedCharacter();
		CityModel cityToMoveTo = gameController.getGameModel().getSelectedCity();
		
		if(characterToMove.verifyMoveWithoutCard(cityToMoveTo)){
			characterToMove.moveWithoutCard(cityToMoveTo);
			return;
		}
		
		Set<AbstractCharacterController> otherCharacters = 
				new HashSet<AbstractCharacterController>(gameController.getPlayers());
		otherCharacters.remove(characterToMove);
		for(AbstractCharacterController otherCharacter: otherCharacters){
			if(otherCharacter.getCharacterModel().getCurrentCity().equals(cityToMoveTo)){
				characterToMove.moveWithoutCard(cityToMoveTo);
				return;
			}
		}
		
		for(CardModel cardToMoveWith : this.character.getHandOfCards()){
			if(characterToMove.verifyMoveWithCard(cityToMoveTo, cardToMoveWith)){
				this.character.removeCardFromHandOfCards(cardToMoveWith);
				characterToMove.moveWithoutCard(cityToMoveTo);
				return;
			}
		}
		
		return;
	}

}