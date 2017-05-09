package characters;

import java.util.HashSet;
import java.util.Set;

import cards.CardModel;
import city.CityModel;
import diseases.DiseaseModel;

public class CharacterModel {

	private String name;
	private Set<CardModel> handOfCards;
	private CityModel currentCity;
	
	public CharacterModel(String name, CityModel currentCity){
		this.name = name;
		this.handOfCards = new HashSet<>();
		this.currentCity = currentCity;
	}
	
	public String getName(){
		return this.name;
	}
	
	public Set<CardModel> getHandOfCards(){
		return this.handOfCards;
	}
	
	public void addCardToHandOfCards(CardModel cardToAdd){
		this.handOfCards.add(cardToAdd);
	}
	
	public void removeCardFromHandOfCards(CardModel cardToRemove){
		this.handOfCards.remove(cardToRemove);
	}
	
	public boolean isInHand(CardModel cardToCheck){
		return this.handOfCards.contains(cardToCheck);
	}
	
	public int getHandSize(){
		return this.handOfCards.size();
	}
	
	public CityModel getCurrentCity(){
		return this.currentCity;
	}
	
	public String getNameOfCurrentCity(){
		return this.currentCity.getName();
	}
	
	public Set<CityModel> getCurrentNeighbors(){
		return this.currentCity.getNeighbors();
	}
	
	public int getCubesByDiseaseOnCurrentCity(DiseaseModel disease){
		return this.currentCity.getCubesByDisease(disease);
	}
	
	public void setCurrentCity(CityModel cityToMoveTo){
		this.currentCity = cityToMoveTo;
	}
	
	public void setCubesByDiseaseOnCurrentCity(DiseaseModel disease, int numCubes){
		this.currentCity.setCubesByDisease(disease, numCubes);
	}
	
	public void removeCubesByDiseaseOnCurrentCity(DiseaseModel disease, int numCubes){
		this.currentCity.setCubesByDisease(disease, this.currentCity.getCubesByDisease(disease) - numCubes);
	}
	
	public boolean isAtResearchStation(){
		return this.currentCity.isHasResearchStation();
	}
	
	public void setHasResearchStationAtCurrentCity(boolean hasResearchStation){
		this.currentCity.setHasResearchStation(hasResearchStation);
	}
	
	public boolean isCurrentCityQuarentined(){
		return this.currentCity.isQuarentined();
	}
	
	public void setCurrentCityQuarentined(boolean quarentined){
		this.currentCity.setQuarentined(quarentined);
	}
}
