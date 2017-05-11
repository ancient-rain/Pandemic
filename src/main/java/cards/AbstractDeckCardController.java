package cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import city.CityController;
import city.CityModel;

public abstract class AbstractDeckCardController {
	
	protected List<CardModel> deckCards;
	protected List<CardModel> discardCards;
	protected Map<CityModel, CardModel> cityToCardMap;
	
	public AbstractDeckCardController(CityController cityController){
		this.deckCards = new ArrayList<>();
		this.discardCards = new ArrayList<>();
		this.initializeDeck(cityController);
		Collections.shuffle(deckCards);
	}
	
	public int getNumberOfCardsInDeck(){
		return this.deckCards.size();
	}
	
	public int getNumberOfCardsInDiscard(){
		return this.discardCards.size();
	}
	
	public CardModel draw(){
		return this.deckCards.remove(this.deckCards.size() - 1);
	}
	
	public List<CardModel> drawNumberOfCards(int numberOfCardsToDraw){
		List<CardModel> cardsToReturn = new ArrayList<>();
		for(int i = 0; i < numberOfCardsToDraw; i++){
			cardsToReturn.add(this.draw());
		}
		return cardsToReturn;
	}
	
	public void discard(CardModel cardToDiscard){
		this.discardCards.add(cardToDiscard);
	}
	
	public List<CardModel> getDiscardedCards(){
		return this.discardCards;
	}
	
	protected void initializeDeck(CityController cityController){
		for(CityModel c : cityController.getCities()){
			CardModel cardToAdd = new CardModel(c.getName());
			this.deckCards.add(cardToAdd);
			this.cityToCardMap.put(c, cardToAdd);
		}
	}
	
	public Map<CityModel, CardModel> getCityToCardMap(){
		return this.cityToCardMap;
	}
	
	public abstract void specialShuffle(int numberToUseInShuffle);
}
