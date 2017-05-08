package cards;

import city.CityModel;

public class CardModel {
	
	private String name;
	
	public CardModel(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public boolean sharesName(CityModel cityModel){
		return this.name.equals(cityModel.getName());
	}
}
