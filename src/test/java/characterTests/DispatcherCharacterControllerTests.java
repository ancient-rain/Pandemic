package characterTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import cards.CardModel;
import characters.AbstractCharacterController;
import characters.CharacterModel;
import characters.DispatcherCharacterController;
import city.CityModel;
import game.GameController;
import game.GameModel;

public class DispatcherCharacterControllerTests{
	public GameModel gameModel;
	public GameController gameController;
	public CityModel cityToMoveFrom;
	public CityModel cityToMoveTo;
	public CardModel cardToMoveWith;
	public AbstractCharacterController dispatcher;
	public CharacterModel dispatcherModel;
	public AbstractCharacterController characterToMove;
	public CharacterModel characterToMoveModel;
	public AbstractCharacterController characterToMoveTo;
	public CharacterModel characterToMoveToModel;
	public List<AbstractCharacterController> characters;
	public List<CharacterModel> characterModels;

	@Before
	public void init() {
		this.gameModel = EasyMock.createNiceMock(GameModel.class);
		this.gameController = EasyMock.createNiceMock(GameController.class);
		
		this.cityToMoveFrom = EasyMock.createNiceMock(CityModel.class);
		this.cityToMoveTo = EasyMock.createNiceMock(CityModel.class);

		this.cardToMoveWith = EasyMock.createNiceMock(CardModel.class);
		
		this.characters = new ArrayList<AbstractCharacterController>();
		this.characterModels = new ArrayList<CharacterModel>();
		this.characterToMoveModel = EasyMock.partialMockBuilder(CharacterModel.class)
				.withConstructor("testRole", this.cityToMoveFrom)
				.createNiceMock();
		this.characterToMove = EasyMock.partialMockBuilder(AbstractCharacterController.class)
				.withConstructor(this.characterToMoveModel)
				.addMockedMethod("verifyMoveWithoutCard")
				.addMockedMethod("verifyMoveWithCard")
				.createNiceMock();
		this.characterToMoveToModel = EasyMock.createNiceMock(CharacterModel.class);
		this.characterToMoveTo = EasyMock.createNiceMock(AbstractCharacterController.class);
		this.dispatcherModel = EasyMock.createMock(CharacterModel.class);
		this.dispatcher = new DispatcherCharacterController(this.dispatcherModel);
		this.characters.add(characterToMove);
		this.characters.add(this.dispatcher);
		this.characters.add(this.characterToMoveTo);
		this.characterModels.add(this.characterToMoveModel);
		this.characterModels.add(this.dispatcherModel);
		this.characterModels.add(this.characterToMoveToModel);
	}
	
	@Test
	public void testVerifyAbilityMoveNormalTrue(){
		EasyMock.expect(this.gameController.getGameModel()).andStubReturn(this.gameModel);
		EasyMock.expect(this.gameModel.getSelectedCity()).andStubReturn(this.cityToMoveTo);
		EasyMock.expect(this.gameModel.getSelectedCharacter()).andStubReturn(this.characterToMove);
		
		EasyMock.expect(this.characterToMove.verifyMoveWithoutCard(this.cityToMoveTo)).andStubReturn(true);
				
		EasyMock.replay(gameModel, gameController, cityToMoveFrom, cityToMoveTo, cardToMoveWith, 
				characterToMoveModel, characterToMove, characterToMoveTo, characterToMoveToModel, 
				dispatcherModel);
		
		boolean verified = this.dispatcher.verifyAbility(this.gameController);
		
		EasyMock.verify(gameModel, gameController, cityToMoveFrom, cityToMoveTo, cardToMoveWith, 
				characterToMoveModel, characterToMove, characterToMoveTo, characterToMoveToModel, 
				dispatcherModel);
		assertTrue(verified);
	}
	
	@Test
	public void testVerifyAbilityMoveByPawnTrue(){
		EasyMock.expect(this.gameController.getGameModel()).andStubReturn(this.gameModel);
		EasyMock.expect(this.gameModel.getSelectedCity()).andStubReturn(this.cityToMoveTo);
		EasyMock.expect(this.gameModel.getSelectedCharacter()).andStubReturn(this.characterToMove);
		
		EasyMock.expect(this.characterToMove.verifyMoveWithoutCard(this.cityToMoveTo)).
			andStubReturn(false);		
		
		EasyMock.expect(this.gameController.getPlayers()).andStubReturn(this.characters);
		this.characters.remove(this.characterToMove);
		EasyMock.expect(this.dispatcherModel.getCurrentCity()).andStubReturn(this.cityToMoveFrom);
		EasyMock.expect(this.characterToMoveTo.getCharacterModel()).andStubReturn(this.characterToMoveToModel);
		EasyMock.expect(this.characterToMoveToModel.getCurrentCity()).andStubReturn(this.cityToMoveTo);
		
		EasyMock.replay(gameModel, gameController, cityToMoveFrom, cityToMoveTo, cardToMoveWith, 
				characterToMoveModel, characterToMove, characterToMoveTo, characterToMoveToModel, 
				dispatcherModel);
		
		boolean verified = this.dispatcher.verifyAbility(this.gameController);
		
		EasyMock.verify(gameModel, gameController, cityToMoveFrom, cityToMoveTo, cardToMoveWith, 
				characterToMoveModel, characterToMove, characterToMoveTo, characterToMoveToModel, 
				dispatcherModel);
		assertTrue(verified);	
	}
	
	@Test
	public void testVerifyAbilityMoveByCardTrue(){
		EasyMock.expect(this.gameController.getGameModel()).andStubReturn(this.gameModel);
		EasyMock.expect(this.gameModel.getSelectedCity()).andStubReturn(this.cityToMoveTo);
		EasyMock.expect(this.gameModel.getSelectedCharacter()).andStubReturn(this.characterToMove);
		
		EasyMock.expect(this.characterToMove.verifyMoveWithoutCard(this.cityToMoveTo)).andStubReturn(false);
		
		EasyMock.expect(this.gameController.getPlayers()).andStubReturn(this.characters);
		this.characters.remove(this.characterToMove);
		EasyMock.expect(this.dispatcherModel.getCurrentCity()).andStubReturn(this.cityToMoveFrom);
		EasyMock.expect(this.characterToMoveTo.getCharacterModel()).andStubReturn(this.characterToMoveToModel);
		EasyMock.expect(this.characterToMoveToModel.getCurrentCity()).andStubReturn(this.cityToMoveFrom);
		
		Set<CardModel> stubHand = new HashSet<CardModel>();
		stubHand.add(this.cardToMoveWith);
		EasyMock.expect(this.dispatcherModel.getHandOfCards()).andStubReturn(stubHand);
		EasyMock.expect(this.characterToMove.verifyMoveWithCard(this.cityToMoveTo, 
				this.cardToMoveWith)).andStubReturn(true);
		
		EasyMock.replay(gameModel, gameController, cityToMoveFrom, cityToMoveTo, cardToMoveWith, 
				characterToMoveModel, characterToMove, characterToMoveTo, characterToMoveToModel, 
				dispatcherModel);
		
		boolean verified = this.dispatcher.verifyAbility(this.gameController);
		
		EasyMock.verify(gameModel, gameController, cityToMoveFrom, cityToMoveTo, cardToMoveWith, 
				characterToMoveModel, characterToMove, characterToMoveTo, characterToMoveToModel, 
				dispatcherModel);
		assertTrue(verified);	
	}
	
	@Test
	public void testVerifyAbilityFalse(){
		EasyMock.expect(this.gameController.getGameModel()).andStubReturn(this.gameModel);
		EasyMock.expect(this.gameModel.getSelectedCity()).andStubReturn(this.cityToMoveTo);
		EasyMock.expect(this.gameModel.getSelectedCharacter()).andStubReturn(this.characterToMove);
		
		EasyMock.expect(this.characterToMove.verifyMoveWithoutCard(this.cityToMoveTo)).andStubReturn(false);
		
		EasyMock.expect(this.gameController.getPlayers()).andStubReturn(this.characters);
		this.characters.remove(this.characterToMove);
		EasyMock.expect(this.dispatcherModel.getCurrentCity()).andStubReturn(this.cityToMoveFrom);
		EasyMock.expect(this.characterToMoveTo.getCharacterModel()).andStubReturn(this.characterToMoveToModel);
		EasyMock.expect(this.characterToMoveToModel.getCurrentCity()).andStubReturn(
				EasyMock.createNiceMock(CityModel.class));

		Set<CardModel> stubHand = new HashSet<CardModel>();
		stubHand.add(this.cardToMoveWith);
		EasyMock.expect(this.dispatcherModel.getHandOfCards()).andStubReturn(stubHand);
		EasyMock.expect(this.characterToMove.verifyMoveWithCard(this.cityToMoveTo, 
				this.cardToMoveWith)).andStubReturn(false);
		
		EasyMock.replay(gameModel, gameController, cityToMoveFrom, cityToMoveTo, cardToMoveWith, 
				characterToMoveModel, characterToMove, characterToMoveTo, characterToMoveToModel, 
				dispatcherModel);
		
		boolean verified = this.dispatcher.verifyAbility(this.gameController);
		
		EasyMock.verify(gameModel, gameController, cityToMoveFrom, cityToMoveTo, cardToMoveWith, 
				characterToMoveModel, characterToMove, characterToMoveTo, characterToMoveToModel, 
				dispatcherModel);
		assertFalse(verified);
	}
	
	@Test
	public void testAbilityMoveNormal(){
		EasyMock.expect(this.gameController.getGameModel()).andStubReturn(this.gameModel);
		EasyMock.expect(this.gameModel.getSelectedCity()).andStubReturn(this.cityToMoveTo);
		EasyMock.expect(this.gameModel.getSelectedCharacter()).andStubReturn(this.characterToMove);
		

		EasyMock.expect(this.characterToMove.verifyMoveWithoutCard(this.cityToMoveTo)).andStubReturn(true);
		
		EasyMock.replay(gameModel, gameController, cityToMoveFrom, cityToMoveTo, cardToMoveWith, 
				characterToMoveModel, characterToMove, characterToMoveTo, characterToMoveToModel, 
				dispatcherModel);
		
		this.dispatcher.ability(this.gameController);
		CityModel movedTo = this.characterToMove.getCharactersCurrentCity();
		
		EasyMock.verify(gameModel, gameController, cityToMoveFrom, cityToMoveTo, cardToMoveWith, 
				characterToMoveModel, characterToMove, characterToMoveTo, characterToMoveToModel, 
				dispatcherModel);
		assertEquals(this.cityToMoveTo, movedTo);
	}
	
	@Test
	public void testAbilityMoveByPawn(){
		EasyMock.expect(this.gameController.getGameModel()).andStubReturn(this.gameModel);
		EasyMock.expect(this.gameModel.getSelectedCity()).andStubReturn(this.cityToMoveTo);
		EasyMock.expect(this.gameModel.getSelectedCharacter()).andStubReturn(this.characterToMove);
		
		EasyMock.expect(this.characterToMove.verifyMoveWithoutCard(this.cityToMoveTo)).andStubReturn(false);
		
		EasyMock.expect(this.gameController.getPlayers()).andStubReturn(this.characters);
		this.characters.remove(this.characterToMove);
		EasyMock.expect(this.dispatcherModel.getCurrentCity()).andStubReturn(this.cityToMoveFrom);
		EasyMock.expect(this.characterToMoveTo.getCharacterModel()).andStubReturn(this.characterToMoveToModel);
		EasyMock.expect(this.characterToMoveToModel.getCurrentCity()).andStubReturn(this.cityToMoveTo);
		
		EasyMock.replay(gameModel, gameController, cityToMoveFrom, cityToMoveTo, cardToMoveWith, 
				characterToMoveModel, characterToMove, characterToMoveTo, characterToMoveToModel, 
				dispatcherModel);
		
		this.dispatcher.ability(this.gameController);
		CityModel movedTo = this.characterToMove.getCharactersCurrentCity();
		
		EasyMock.verify(gameModel, gameController, cityToMoveFrom, cityToMoveTo, cardToMoveWith, 
				characterToMoveModel, characterToMove, characterToMoveTo, characterToMoveToModel, 
				dispatcherModel);
		assertEquals(this.cityToMoveTo, movedTo);
	}
	
	@Test
	public void testAbilityMoveByCard(){
		EasyMock.expect(this.gameController.getGameModel()).andStubReturn(this.gameModel);
		EasyMock.expect(this.gameModel.getSelectedCity()).andStubReturn(this.cityToMoveTo);
		EasyMock.expect(this.gameModel.getSelectedCharacter()).andStubReturn(this.characterToMove);
		
		EasyMock.expect(this.characterToMove.verifyMoveWithoutCard(this.cityToMoveTo)).andStubReturn(false);
		
		EasyMock.expect(this.gameController.getPlayers()).andStubReturn(this.characters);
		this.characters.remove(this.characterToMove);
		EasyMock.expect(this.dispatcherModel.getCurrentCity()).andStubReturn(this.cityToMoveFrom);
		EasyMock.expect(this.characterToMoveTo.getCharacterModel()).andStubReturn(this.characterToMoveToModel);
		EasyMock.expect(this.characterToMoveToModel.getCurrentCity()).andStubReturn(this.cityToMoveFrom);
		
		Set<CardModel> stubHand = new HashSet<CardModel>();
		stubHand.add(this.cardToMoveWith);
		EasyMock.expect(this.dispatcherModel.getHandOfCards()).andStubReturn(stubHand);
		EasyMock.expect(this.characterToMove.verifyMoveWithCard(this.cityToMoveTo, 
				this.cardToMoveWith)).andStubReturn(true);
		this.dispatcherModel.removeCardFromHandOfCards(cardToMoveWith);
		EasyMock.expectLastCall();
		
		EasyMock.replay(gameModel, gameController, cityToMoveFrom, cityToMoveTo, cardToMoveWith, 
				characterToMoveModel, characterToMove, characterToMoveTo, characterToMoveToModel, 
				dispatcherModel);

		this.dispatcher.ability(this.gameController);
		CityModel movedTo = this.characterToMove.getCharactersCurrentCity();
		
		EasyMock.verify(gameModel, gameController, cityToMoveFrom, cityToMoveTo, cardToMoveWith, 
				characterToMoveModel, characterToMove, characterToMoveTo, characterToMoveToModel, 
				dispatcherModel);
		assertEquals(this.cityToMoveTo, movedTo);
	}
	
	@Test
	public void testAbilityFalse(){
		EasyMock.expect(this.gameController.getGameModel()).andStubReturn(this.gameModel);
		EasyMock.expect(this.gameModel.getSelectedCity()).andStubReturn(this.cityToMoveTo);
		EasyMock.expect(this.gameModel.getSelectedCharacter()).andStubReturn(this.characterToMove);
		
		EasyMock.expect(this.characterToMove.verifyMoveWithoutCard(this.cityToMoveTo)).andStubReturn(false);
		
		EasyMock.expect(this.gameController.getPlayers()).andStubReturn(this.characters);
		this.characters.remove(this.characterToMove);
		EasyMock.expect(this.dispatcherModel.getCurrentCity()).andStubReturn(this.cityToMoveFrom);
		EasyMock.expect(this.characterToMoveTo.getCharacterModel()).andStubReturn(this.characterToMoveToModel);
		EasyMock.expect(this.characterToMoveToModel.getCurrentCity()).andStubReturn(this.cityToMoveFrom);

		Set<CardModel> stubHand = new HashSet<CardModel>();
		stubHand.add(this.cardToMoveWith);
		EasyMock.expect(this.dispatcherModel.getHandOfCards()).andStubReturn(stubHand);
		EasyMock.expect(this.characterToMove.verifyMoveWithCard(this.cityToMoveTo, this.cardToMoveWith))
			.andStubReturn(false);
		
		EasyMock.replay(gameModel, gameController, cityToMoveFrom, cityToMoveTo, cardToMoveWith, 
				characterToMoveModel, characterToMove, characterToMoveTo, characterToMoveToModel, 
				dispatcherModel);

		this.dispatcher.ability(this.gameController);
		CityModel movedTo = this.characterToMove.getCharactersCurrentCity();
		
		EasyMock.verify(gameModel, gameController, cityToMoveFrom, cityToMoveTo, cardToMoveWith, 
				characterToMoveModel, characterToMove, characterToMoveTo, 
				characterToMoveToModel, dispatcherModel);
		assertEquals(this.cityToMoveFrom, movedTo);
	}
}