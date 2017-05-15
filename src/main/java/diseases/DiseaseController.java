package diseases;

public class DiseaseController {
	
	private DiseaseModel blueDisease;
	private DiseaseModel blackDisease;
	private DiseaseModel redDisease;
	private DiseaseModel yellowDisease;
	
	
	public DiseaseController(){
		this.blueDisease = new DiseaseModel();
		this.blackDisease = new DiseaseModel();
		this.redDisease = new DiseaseModel();
		this.yellowDisease = new DiseaseModel();
	}

	public DiseaseModel getBlueDisease(){
		return this.blueDisease;
	}
	
	public DiseaseModel getBlackDisease(){
		return this.blackDisease;
	}
	
	public DiseaseModel getRedDisease(){
		return this.redDisease;
	}
	
	public DiseaseModel getYellowDisease(){
		return this.yellowDisease;
	}
	
//	public int getBlueDiseaseCubes(){
//		return this.blueDisease.getCubesLeft();
//	}
	
	public boolean areAllDiseasesCured(){
		return this.blueDisease.isCured() 
				&& this.blackDisease.isCured() 
				&& this.redDisease.isCured() 
				&& this.yellowDisease.isCured();
	}
}
