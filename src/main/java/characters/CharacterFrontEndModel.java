package characters;

import java.awt.Color;

import static constants.Character.*;

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
		if(this.model.getRole().equals("Contingency Planner")){
			return CONTINGENCY_PLANNER_COLOR;
		} else if(this.model.getRole().equals("Dispatcher")){
			return DISPATCHER_COLOR;
		} else if(this.model.getRole().equals("Medic")){
			return MEDIC_COLOR;
		} else if(this.model.getRole().equals("Operations Expert")){
			return OPERATIONS_EXPERT_COLOR;
		} else if(this.model.getRole().equals("Quarentine Specialist")){
			return QUARANTINE_SPECIALIST_COLOR;
		} else if(this.model.getRole().equals("Researcher")){
			return RESEARCHER_COLOR;
		} else {
			return SCIENTIST_COLOR;
		}
	}
	
	private String setImgPath() {
		if(this.model.getRole().equals("Contingency Planner")){
			return CONTINGENCY_PLANNER_ICON;
		} else if(this.model.getRole().equals("Dispatcher")){
			return DISPATCHER_ICON;
		} else if(this.model.getRole().equals("Medic")){
			return MEDIC_ICON;
		} else if(this.model.getRole().equals("Operations Expert")){
			return OPERATIONS_EXPERT_ICON;
		} else if(this.model.getRole().equals("Quarentine Specialist")){
			return QUARANTINE_SPECIALIST_ICON;
		} else if(this.model.getRole().equals("Researcher")){
			return RESEARCHER_ICON;
		} else {
			return SCIENTIST_ICON;
		}
	}
}
