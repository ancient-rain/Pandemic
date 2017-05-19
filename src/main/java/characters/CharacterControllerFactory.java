package characters;

import static constants.Game.*;

public class CharacterControllerFactory {

	public CharacterControllerFactory(){
		
	}
	
	public AbstractCharacterController createCharacterController(CharacterModel characterToCreate){
		if(characterToCreate.getRole().equals(CONTINGENCY_PLANNER)){
			return new ContingencyPlannerCharacterController(characterToCreate);
		}
		else if (characterToCreate.getRole().equals(DISPATCHER)) {
			return new DispatcherCharacterController(characterToCreate);
		}
		else if (characterToCreate.getRole().equals(MEDIC)) {
			return new MedicCharacterController(characterToCreate);
		}
		else if (characterToCreate.getRole().equals(OPERATIONS_EXPERT)) {
			return new OperationsExpertCharacterController(characterToCreate);
		}
		else if (characterToCreate.getRole().equals(QUARANTINE_SPECIALIST)) {
			return new QuarentineSpecialistCharacterController(characterToCreate);
		}
		else if (characterToCreate.getRole().equals(RESEARCHER)) {
			return new ResearcherCharacterController(characterToCreate);
		}
		else {
			return new ScientistCharacterController(characterToCreate);
		}
	}
}
