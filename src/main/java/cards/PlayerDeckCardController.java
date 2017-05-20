package cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import city.CityController;

public class PlayerDeckCardController extends AbstractDeckCardController{

	public PlayerDeckCardController(CityController cityController) {
		super(cityController);
	}

	@Override
	public void specialShuffle(int numberOfEpidemics) {
		this.specialShuffle(numberOfEpidemics, new Random(System.nanoTime()));
	}
	
	public void specialShuffle(int numberOfEpidemics, Random seed){
		ArrayList<ArrayList<CardModel>> deckPartitions = new ArrayList<ArrayList<CardModel>>();
		int partitionSize = this.deckCards.size()/numberOfEpidemics;
		for(int i = 0; i <= this.deckCards.size(); i+=(partitionSize+1)){
			int end = Math.min(this.deckCards.size(), i + partitionSize + 1);
			ArrayList<CardModel> partition = new ArrayList<>(this.deckCards.subList(i, end));
			partition.add(new CardModel(constants.Card.EPIDEMIC, CardModel.CardType.EPIDEMIC));
			Collections.shuffle(partition, seed);
			deckPartitions.add(partition);
		}
		ArrayList<CardModel> newDeck = new ArrayList<>();
		for(int i = 0; i < deckPartitions.size(); i++){
			newDeck.addAll(deckPartitions.get(i));
		}
		this.deckCards = newDeck;
	}

	@Override
	protected void initializeDeck(CityController cityController) {
		super.initializeDeck(cityController);
		this.deckCards.add(new CardModel(constants.Card.AIRLIFT, CardModel.CardType.EVENT));
		this.deckCards.add(new CardModel(constants.Card.FORECAST, CardModel.CardType.EVENT));
		this.deckCards.add(new CardModel(constants.Card.GOVERNMENT_GRANT, CardModel.CardType.EVENT));
		this.deckCards.add(new CardModel(constants.Card.ONE_QUIET_NIGHT, CardModel.CardType.EVENT));
		this.deckCards.add(new CardModel(constants.Card.RESILIENT_POPULATION, CardModel.CardType.EVENT));
	}

}
