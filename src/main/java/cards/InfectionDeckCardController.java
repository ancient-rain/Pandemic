package cards;

import java.util.Collections;

import city.CityController;

public class InfectionDeckCardController extends AbstractDeckCardController{

	public InfectionDeckCardController(CityController cityController) {
		super(cityController);
	}

	@Override
	public void specialShuffle(int numberToUseInShuffle) {
		Collections.shuffle(this.discardCards);
		this.deckCards.addAll(discardCards);
		this.discardCards.clear();
	}

	public void addToTop(CardModel cardToPutOnTop){
		this.deckCards.add(cardToPutOnTop);
	}
	
	public CardModel drawBottomCard(){
		return this.deckCards.remove(0);
	}
}