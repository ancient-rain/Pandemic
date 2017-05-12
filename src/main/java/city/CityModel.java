package city;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import diseases.DiseaseModel;

public class CityModel {
	
	private String name;
	private DiseaseModel primaryDisease;
	private boolean hasResearchStation;
	private boolean quarentined;
	private Set<CityModel> neighbors;
	private Map<DiseaseModel, Integer> diseaseCubes;
	
	public CityModel(String name, DiseaseModel primaryDisease){
		this.name = name;
		this.primaryDisease = primaryDisease;
		this.hasResearchStation = false;
		this.neighbors = new HashSet<>();
		this.diseaseCubes = new HashMap<>();
	}

	public String getName() {
		return name;
	}
	
	public DiseaseModel getPrimaryDisease(){
		return this.primaryDisease;
	}

	public boolean hasResearchStation() {
		return hasResearchStation;
	}

	public void setHasResearchStation(boolean hasResearchStation) {
		this.hasResearchStation = hasResearchStation;
	}

	public boolean isQuarentined() {
		return quarentined;
	}

	public void setQuarentined(boolean quarentined) {
		this.quarentined = quarentined;
	}
	
	public Set<CityModel> getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(Set<CityModel> neighbors) {
		this.neighbors = neighbors;
	}
	
	public int getCubesByDisease(DiseaseModel disease){
		return this.diseaseCubes.get(disease);
	}

	public void setCubesByDisease(DiseaseModel disease, int numCubes){
		this.diseaseCubes.put(disease, numCubes);
	}
	
	public Set<DiseaseModel> getDiseases(){
		return this.diseaseCubes.keySet();
	}	
}