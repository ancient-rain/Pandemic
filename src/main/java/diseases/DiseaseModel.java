package diseases;

public class DiseaseModel {

	private int cubesLeft;
	private boolean cured;
	private boolean eradicated;
	
	public DiseaseModel(){
		this.cubesLeft = 24;
		this.cured = false;
		this.eradicated = false;
	}

	public int getCubesLeft() {
		return cubesLeft;
	}

	public void setCubesLeft(int cubesLeft) {
		this.cubesLeft = cubesLeft;
	}
	
	public void addToCubesLeft(int cubesLeft) {
		this.cubesLeft += cubesLeft;
	}
	
	public void removeFromCubesLeft(int cubesLeft) {
		this.cubesLeft -= cubesLeft;
	}

	public boolean isCured() {
		return cured;
	}

	public void setCured(boolean cured) {
		this.cured = cured;
	}

	public boolean isEradicated() {
		return eradicated;
	}

	public void setEradicated(boolean eradicated) {
		this.eradicated = eradicated;
	}
	
	
}
