package characters;

import java.awt.Color;

import static constants.Constants.CONTINGENCY_PLANNER_ICON;
import static constants.Constants.DISPATCHER_ICON;
import static constants.Constants.MEDIC_ICON;
import static constants.Constants.OPERATIONS_EXPERT_ICON;
import static constants.Constants.QUARANTINE_SPECIALIST_ICON;
import static constants.Constants.RESEARCHER_ICON;
import static constants.Constants.SCIENTIST_ICON;
import static constants.Constants.CONTINGENCY_PLANNER_COLOR;
import static constants.Constants.DISPATCHER_COLOR;
import static constants.Constants.MEDIC_COLOR;
import static constants.Constants.OPERATIONS_EXPERT_COLOR;
import static constants.Constants.QUARANTINE_SPECIALIST_COLOR;
import static constants.Constants.RESEARCHER_COLOR;
import static constants.Constants.SCIENTIST_COLOR;

public class CharacterFrontEndModel {
	CharacterModel model;
	String img = "";
	Color color = new Color(0, 0, 0);
	
	public CharacterFrontEndModel(CharacterModel model) {
		this.model = model;

		init();
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public String getImgPath() {
		return this.img;
	}
	
	private void init() {
		this.color = setColor();
		this.img = setImgPath();
	}
	
	private Color setColor() {
		switch(this.model.getRole()){
		case "Contingency Planner":
			return CONTINGENCY_PLANNER_COLOR;
		case "Dispatcher":
			return DISPATCHER_COLOR;
		case "Medic":
			return MEDIC_COLOR;
		case "Operations Expert":
			return OPERATIONS_EXPERT_COLOR;
		case "Quarentine Specialist":
			return QUARANTINE_SPECIALIST_COLOR;
		case "Researcher":
			return RESEARCHER_COLOR;
		default:
			return SCIENTIST_COLOR;
		}
	}
	
	private String setImgPath() {
		switch(this.model.getRole()){
		case "Contingency Planner":
			return CONTINGENCY_PLANNER_ICON;
		case "Dispatcher":
			return DISPATCHER_ICON;
		case "Medic":
			return MEDIC_ICON;
		case "Operations Expert":
			return OPERATIONS_EXPERT_ICON;
		case "Quarentine Specialist":
			return QUARANTINE_SPECIALIST_ICON;	
		case "Researcher":
			return RESEARCHER_ICON;
		default:
			return SCIENTIST_ICON;
		}
	}
}
