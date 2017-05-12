package cards;

import city.CityModel;

public class CardModel {
	
	public enum CardType{
		PLAYER,
		INFECTION,
		EVENT,
		EPIDEMIC
	}
	
	private String name;
	private CardType type;
	
	public CardModel(String name, CardType type){
		this.name = name;
		this.type = type;
	}
	
	public String getName(){
		return this.name;
	}
	
	public boolean sharesName(CityModel cityModel){
		return this.name.equals(cityModel.getName());
	}
	
	public CardType getType(){
		return this.type;
	}
}
