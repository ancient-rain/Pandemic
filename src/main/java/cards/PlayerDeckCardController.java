package cards;

import java.util.ArrayList;
import java.util.Collections;

import city.CityController;

public class PlayerDeckCardController extends AbstractDeckCardController{

	public PlayerDeckCardController(CityController cityController) {
		super(cityController);
	}

	@Override
	public void specialShuffle(int numberOfEpidemics) {
		ArrayList<ArrayList<CardModel>> deckPartitions = new ArrayList<ArrayList<CardModel>>();
		Collections.shuffle(this.deckCards);
		int partitionSize = this.deckCards.size()/numberOfEpidemics;
		for(int i = 0; i < this.deckCards.size(); i+=(partitionSize+1)){
			int end = Math.min(this.deckCards.size(), i + partitionSize + 1);
			ArrayList<CardModel> partition = new ArrayList<>(this.deckCards.subList(i, end));
			partition.add(new CardModel("Epidemic", CardModel.CardType.EPIDEMIC));
			Collections.shuffle(partition);
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
		this.deckCards.add(new CardModel("Airlift", CardModel.CardType.EVENT));
		this.deckCards.add(new CardModel("Forecast", CardModel.CardType.EVENT));
		this.deckCards.add(new CardModel("Government Grant", CardModel.CardType.EVENT));
		this.deckCards.add(new CardModel("One Quiet Night", CardModel.CardType.EVENT));
		this.deckCards.add(new CardModel("Resilient Population", CardModel.CardType.EVENT));
	}

}
