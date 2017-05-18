package characterTests;

import static org.junit.Assert.*;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import cards.CardModel;
import characters.AbstractCharacterController;
import characters.CharacterModel;
import characters.ContingencyPlannerCharacterController;
import game.GameController;
import game.GameModel;

public class ContingencyPlannerTests {
	
	GameModel gameModel;
	GameController gameController;
	CharacterModel contingencyPlannerModel;
	AbstractCharacterController contingencyPlanner;
	CardModel startCard;
	CardModel contPlan;
	CardModel selectedCard;
	
	@Before
	public void init(){
		this.gameModel = EasyMock.partialMockBuilder(GameModel.class)
				.addMockedMethod("getSelectedCard")
				.createNiceMock();
		this.gameController = EasyMock.createNiceMock(GameController.class);
		this.contingencyPlannerModel = EasyMock.createNiceMock(CharacterModel.class);
		this.contingencyPlanner = new ContingencyPlannerCharacterController(this.contingencyPlannerModel);
		this.startCard = EasyMock.createNiceMock(CardModel.class);
		this.contPlan = EasyMock.createNiceMock(CardModel.class);
		this.selectedCard = EasyMock.createNiceMock(CardModel.class);
		this.gameModel.setSelectedContingencyPlan(this.contPlan);
	}
	
	@Test
	public void testVerifyTrue(){
		EasyMock.expect(this.gameController.getGameModel()).andStubReturn(this.gameModel);
		EasyMock.expect(this.gameModel.getSelectedCard()).andStubReturn(this.selectedCard);
		EasyMock.expect(this.contPlan.getName()).andReturn("");
		EasyMock.expect(this.selectedCard.getName()).andReturn("testEvent");
		
		EasyMock.replay(this.gameModel, this.gameController, this.contingencyPlannerModel,
				this.startCard, this.contPlan, this.selectedCard);
		
		boolean verified = this.contingencyPlanner.verifyAbility(this.gameController);
		
		EasyMock.verify(this.gameModel, this.gameController, this.contingencyPlannerModel,
				this.startCard, this.contPlan, this.selectedCard);
		
		assertTrue(verified);
	}
	
	@Test
	public void testVerifyPlanNotEmpty(){
		EasyMock.expect(this.gameController.getGameModel()).andStubReturn(this.gameModel);
		EasyMock.expect(this.gameModel.getSelectedCard()).andStubReturn(this.selectedCard);
		EasyMock.expect(this.contPlan.getName()).andReturn("testEvent");
		
		EasyMock.replay(this.gameModel, this.gameController, this.contingencyPlannerModel,
				this.startCard, this.contPlan, this.selectedCard);
		
		boolean verified = this.contingencyPlanner.verifyAbility(this.gameController);
		
		EasyMock.verify(this.gameModel, this.gameController, this.contingencyPlannerModel,
				this.startCard, this.contPlan, this.selectedCard);
		
		assertFalse(verified);
	}
	
	@Test
	public void testVerifySlectedEmpty(){
		EasyMock.expect(this.gameController.getGameModel()).andStubReturn(this.gameModel);
		EasyMock.expect(this.gameModel.getSelectedCard()).andStubReturn(this.selectedCard);
		EasyMock.expect(this.contPlan.getName()).andReturn("");
		EasyMock.expect(this.selectedCard.getName()).andReturn("");
		
		EasyMock.replay(this.gameModel, this.gameController, this.contingencyPlannerModel,
				this.startCard, this.contPlan, this.selectedCard);

		boolean verified = this.contingencyPlanner.verifyAbility(this.gameController);
		
		EasyMock.verify(this.gameModel, this.gameController, this.contingencyPlannerModel,
				this.startCard, this.contPlan, this.selectedCard);
		
		assertFalse(verified);
	}
	
	@Test
	public void testAbility(){
		EasyMock.expect(this.gameController.getGameModel()).andStubReturn(this.gameModel);
		EasyMock.expect(this.gameModel.getSelectedCard()).andStubReturn(this.selectedCard);
		
		EasyMock.replay(this.gameModel, this.gameController, this.contingencyPlannerModel,
				this.startCard, this.contPlan, this.selectedCard);
		
		this.contingencyPlanner.ability(this.gameController);
		CardModel setCard = this.gameModel.getSelectedContingencyPlan();
		
		EasyMock.verify(this.gameModel, this.gameController, this.contingencyPlannerModel,
				this.startCard, this.contPlan, this.selectedCard);
		
		assertEquals(this.selectedCard, setCard);
	}
	
}
