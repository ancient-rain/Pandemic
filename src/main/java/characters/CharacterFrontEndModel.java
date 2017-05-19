package characters;

import java.awt.Color;

import static constants.Game.*;
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
		if(this.model.getRole().equals(CONTINGENCY_PLANNER)){
			return CONTINGENCY_PLANNER_COLOR;
		} else if(this.model.getRole().equals(DISPATCHER)){
			return DISPATCHER_COLOR;
		} else if(this.model.getRole().equals(MEDIC)){
			return MEDIC_COLOR;
		} else if(this.model.getRole().equals(OPERATIONS_EXPERT)){
			return OPERATIONS_EXPERT_COLOR;
		} else if(this.model.getRole().equals(QUARANTINE_SPECIALIST)){
			return QUARANTINE_SPECIALIST_COLOR;
		} else if(this.model.getRole().equals(RESEARCHER)){
			return RESEARCHER_COLOR;
		} else {
			return SCIENTIST_COLOR;
		}
	}
	
	private String setImgPath() {
		if(this.model.getRole().equals(CONTINGENCY_PLANNER)){
			return CONTINGENCY_PLANNER_ICON;
		} else if(this.model.getRole().equals(DISPATCHER)){
			return DISPATCHER_ICON;
		} else if(this.model.getRole().equals(MEDIC)){
			return MEDIC_ICON;
		} else if(this.model.getRole().equals(OPERATIONS_EXPERT)){
			return OPERATIONS_EXPERT_ICON;
		} else if(this.model.getRole().equals(QUARANTINE_SPECIALIST)){
			return QUARANTINE_SPECIALIST_ICON;
		} else if(this.model.getRole().equals(RESEARCHER)){
			return RESEARCHER_ICON;
		} else {
			return SCIENTIST_ICON;
		}
	}
}
