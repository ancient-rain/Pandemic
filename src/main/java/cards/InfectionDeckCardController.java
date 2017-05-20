package cards;

import java.util.Collections;
import java.util.Random;

import city.CityController;

public class InfectionDeckCardController extends AbstractDeckCardController{

	public InfectionDeckCardController(CityController cityController) {
		super(cityController);
	}

	@Override
	public void specialShuffle(int numberToUseInShuffle) {
		this.specialShuffle(new Random(System.nanoTime()));
	}
	
	public void specialShuffle(Random seed){
		Collections.shuffle(this.discardCards, seed);
		this.deckCards.addAll(discardCards);
		this.discardCards.clear();
	}

	public void addToTop(CardModel cardToPutOnTop){
		this.deckCards.add(cardToPutOnTop);
	}
	
	public CardModel drawBottomCard(){
		return this.deckCards.remove(0);
	}
	
	public void addToBottom(CardModel cardToPutOnBottom){
		this.deckCards.add(0, cardToPutOnBottom);
	}
}
