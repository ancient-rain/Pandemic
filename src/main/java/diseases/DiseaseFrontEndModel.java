package diseases;

import java.awt.Color;

public class DiseaseFrontEndModel {
	DiseaseModel disease;
	Color color;	
	
	public DiseaseFrontEndModel(DiseaseModel disease, Color color) {
		this.disease = disease;
		this.color = color;
	}

	public DiseaseModel getDisease() {
		return this.disease;
	}
	
	public Color getColor() {
		return this.color;
	}
}
