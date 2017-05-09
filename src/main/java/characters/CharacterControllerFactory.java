package characters;

public class CharacterControllerFactory {

	public CharacterControllerFactory(){
		
	}
	
	public AbstractCharacterController createCharacterController(CharacterModel characterToCreate){
		switch(characterToCreate.getName()){
		case "Contingency Planner":
			return new ContingencyPlannerCharacterController(characterToCreate);
		case "Dispatcher":
			return new DispatcherCharacterController(characterToCreate);
		case "Medic":
			return new MedicCharacterController(characterToCreate);
		case "Operations Expert":
			return new OperationsExpertCharacterController(characterToCreate);
		case "Quarentine Specialist":
			return new QuarentineSpecialistCharacterController(characterToCreate);
		case "Researcher":
			return new ResearcherCharacterController(characterToCreate);
		default:
			return new ScientistCharacterController(characterToCreate);
		}
	}
}
