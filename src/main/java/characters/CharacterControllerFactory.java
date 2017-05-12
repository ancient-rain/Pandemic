package characters;

public class CharacterControllerFactory {

	public CharacterControllerFactory(){
		
	}
	
	public AbstractCharacterController createCharacterController(CharacterModel characterToCreate){
		if(characterToCreate.getRole().equals("Contigency Planner")){
			return new ContingencyPlannerCharacterController(characterToCreate);
		}
		else if (characterToCreate.getRole().equals("Dispatcher")) {
			return new DispatcherCharacterController(characterToCreate);
		}
		else if (characterToCreate.getRole().equals("Medic")) {
			return new MedicCharacterController(characterToCreate);
		}
		else if (characterToCreate.getRole().equals("Operations Expert")) {
			return new OperationsExpertCharacterController(characterToCreate);
		}
		else if (characterToCreate.getRole().equals("Quarentine Specialist")) {
			return new QuarentineSpecialistCharacterController(characterToCreate);
		}
		else if (characterToCreate.getRole().equals("Researcher")) {
			return new ResearcherCharacterController(characterToCreate);
		}
		else {
			return new ScientistCharacterController(characterToCreate);
		}
	}
}
