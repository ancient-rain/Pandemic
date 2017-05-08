package city;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import diseases.DiseaseController;
import diseases.DiseaseModel;

public class CityController {
	
	private Map<String, CityModel> cities;
	private Set<CityModel> outbrokenCities;
	private int researchStationCounter;
	private int outbreakCounter;
	
	public CityController(DiseaseController diseaseController){
		this.cities = new HashMap<String, CityModel>();
		this.outbrokenCities = new HashSet<CityModel>();
		this.outbreakCounter = 0;
		this.initializeCities(diseaseController);
		this.initializeCityDiseases(diseaseController);
	}
	
	public Set<CityModel> getCities(){
		return new HashSet<CityModel>(this.cities.values());
	}
	
	public CityModel getCityByName(String cityName){
		return this.cities.get(cityName);
	}
	
	public Set<CityModel> getOutbrokenCities(){
		return this.outbrokenCities;
	}
	
	public void clearOutbrokenCities(){
		this.outbrokenCities.clear();
	}
	
	public int getResearchStationCounter() {
		return researchStationCounter;
	}

	public void setResearchStationCounter(int researchStationCounter) {
		this.researchStationCounter = researchStationCounter;
	}
	
	public void incrementResearchStationCounter(int researchStationsToAdd){
		this.researchStationCounter += researchStationsToAdd;
	}
	
	public int getOutbreakCoutner() {
		return this.outbreakCounter;
	}
	
	public void infect(CityModel cityToInfect, DiseaseModel diseaseInfecting){
		int currentCubes = cityToInfect.getCubesByDisease(diseaseInfecting);
		if(currentCubes > 2){
			cityToInfect.setCubesByDisease(diseaseInfecting, 3);
			this.outbreak(cityToInfect, diseaseInfecting);
		} else {
			diseaseInfecting.removeFromCubesLeft(1);
			cityToInfect.setCubesByDisease(diseaseInfecting, currentCubes + 1);
		}
	}
	
	public void outbreak(CityModel cityToOutbreak, DiseaseModel diseaseOutbreaking){
		for(CityModel c : cityToOutbreak.getNeighbors()){
			if(!this.outbrokenCities.contains(c) && !c.isQuarentined()){
				this.infect(c, diseaseOutbreaking);
			}
		}
		this.outbreakCounter++;
	}
	
	public void buildResearchStation(CityModel cityToBuildOn){
		cityToBuildOn.setHasResearchStation(true);
	}
	
	private void initializeCities(DiseaseController diseaseController){
		CityModel sanFran = new CityModel("San Francisco", diseaseController.getBlueDisease());
		CityModel chicago = new CityModel("Chicago", diseaseController.getBlueDisease());
		CityModel atlanta = new CityModel("Atlanta", diseaseController.getBlueDisease());
		CityModel mont = new CityModel("Montreal", diseaseController.getBlueDisease());
		CityModel wash = new CityModel("Washington", diseaseController.getBlueDisease());
		CityModel nyc = new CityModel("New York", diseaseController.getBlueDisease());
		CityModel london = new CityModel("London", diseaseController.getBlueDisease());
		CityModel madrid = new CityModel("Madrid", diseaseController.getBlueDisease());
		CityModel paris = new CityModel("Paris", diseaseController.getBlueDisease());
		CityModel essen = new CityModel("Essen", diseaseController.getBlueDisease());
		CityModel milan = new CityModel("Milan", diseaseController.getBlueDisease());
		CityModel stPete = new CityModel("St. Petersburg", diseaseController.getBlueDisease());
		
		CityModel algi = new CityModel("Algiers", diseaseController.getBlackDisease());
		CityModel cairo = new CityModel("Cairo", diseaseController.getBlackDisease());
		CityModel istan = new CityModel("Istanbul", diseaseController.getBlackDisease());
		CityModel moscow = new CityModel("Moscow", diseaseController.getBlackDisease());
		CityModel baghdad = new CityModel("Baghdad", diseaseController.getBlackDisease());
		CityModel ridya = new CityModel("Riyadh", diseaseController.getBlackDisease());
		CityModel tehran = new CityModel("Tehran", diseaseController.getBlackDisease());
		CityModel karachi = new CityModel("Karachi", diseaseController.getBlackDisease());
		CityModel mumbai = new CityModel("Mumbai", diseaseController.getBlackDisease());
		CityModel delhi = new CityModel("Delhi", diseaseController.getBlackDisease());
		CityModel chenn = new CityModel("Chennai", diseaseController.getBlackDisease());
		CityModel kolkata = new CityModel("Kolkata", diseaseController.getBlackDisease());
		
		CityModel bangkok = new CityModel("Bangkok", diseaseController.getRedDisease());
		CityModel jakarta = new CityModel("Jakarta", diseaseController.getRedDisease());
		CityModel hoChi = new CityModel("Ho Chi Minh CityModel", diseaseController.getRedDisease());
		CityModel hongKong = new CityModel("Hong Kong", diseaseController.getRedDisease());
		CityModel beijing = new CityModel("Beijing", diseaseController.getRedDisease());
		CityModel seoul = new CityModel("Seoul", diseaseController.getRedDisease());
		CityModel taipei = new CityModel("Taipei", diseaseController.getRedDisease());
		CityModel manila = new CityModel("Manila", diseaseController.getRedDisease());
		CityModel tokyo = new CityModel("Tokyo", diseaseController.getRedDisease());
		CityModel osaka = new CityModel("Osaka", diseaseController.getRedDisease());
		CityModel sydney = new CityModel("Sydney", diseaseController.getRedDisease());
		CityModel shang = new CityModel("Shanghai", diseaseController.getRedDisease());
		
		CityModel la = new CityModel("Los Angeles", diseaseController.getYellowDisease());
		CityModel mexicoCity= new CityModel("Mexico CityModel", diseaseController.getYellowDisease());
		CityModel miami = new CityModel("Miami", diseaseController.getYellowDisease());
		CityModel bogata = new CityModel("Bogota", diseaseController.getYellowDisease());
		CityModel lima = new CityModel("Lima", diseaseController.getYellowDisease());
		CityModel santi = new CityModel("Santiago", diseaseController.getYellowDisease());
		CityModel bueno = new CityModel("Buenos Aires", diseaseController.getYellowDisease());
		CityModel saoPalo = new CityModel("Sao Paulo", diseaseController.getYellowDisease());
		CityModel lago = new CityModel("Lagos", diseaseController.getYellowDisease());
		CityModel kinsha = new CityModel("Kinshasa", diseaseController.getYellowDisease());
		CityModel johann = new CityModel("Johannesburg", diseaseController.getYellowDisease());
		CityModel khar = new CityModel("Khartoum", diseaseController.getYellowDisease());
		
		sanFran.setNeighbors(new HashSet<CityModel>(Arrays.asList(chicago, la, manila, tokyo)));
		chicago.setNeighbors(new HashSet<CityModel>(Arrays.asList(sanFran, atlanta, la, mexicoCity, mont)));
		atlanta.setNeighbors(new HashSet<CityModel>(Arrays.asList(wash, chicago, miami)));
		mont.setNeighbors(new HashSet<CityModel>(Arrays.asList(nyc, wash, chicago)));
		wash.setNeighbors(new HashSet<CityModel>(Arrays.asList(nyc, mont, miami, atlanta)));
		nyc.setNeighbors(new HashSet<CityModel>(Arrays.asList(madrid, london, wash, mont)));
		london.setNeighbors(new HashSet<CityModel>(Arrays.asList(nyc, madrid, paris, essen)));
		madrid.setNeighbors(new HashSet<CityModel>(Arrays.asList(nyc, london, paris, algi, saoPalo)));
		paris.setNeighbors(new HashSet<CityModel>(Arrays.asList(milan, essen, london, madrid, algi)));
		essen.setNeighbors(new HashSet<CityModel>(Arrays.asList(london, paris, milan, stPete)));
		milan.setNeighbors(new HashSet<CityModel>(Arrays.asList(paris, essen, istan)));
		stPete.setNeighbors(new HashSet<CityModel>(Arrays.asList(essen, istan, moscow)));
		
		algi.setNeighbors(new HashSet<CityModel>(Arrays.asList(madrid, paris, istan, cairo)));
		cairo.setNeighbors(new HashSet<CityModel>(Arrays.asList(algi, baghdad, istan, ridya, khar)));
		istan.setNeighbors(new HashSet<CityModel>(Arrays.asList(milan, stPete, algi, cairo, baghdad, moscow)));
		moscow.setNeighbors(new HashSet<CityModel>(Arrays.asList(stPete, istan, tehran)));
		baghdad.setNeighbors(new HashSet<CityModel>(Arrays.asList(tehran, istan, cairo, ridya, karachi)));
		ridya.setNeighbors(new HashSet<CityModel>(Arrays.asList(cairo, baghdad, karachi)));
		tehran.setNeighbors(new HashSet<CityModel>(Arrays.asList(moscow, baghdad, karachi, delhi)));
		karachi.setNeighbors(new HashSet<CityModel>(Arrays.asList(ridya, baghdad, delhi, mumbai, tehran)));
		mumbai.setNeighbors(new HashSet<CityModel>(Arrays.asList(karachi, delhi, chenn)));
		delhi.setNeighbors(new HashSet<CityModel>(Arrays.asList(tehran, karachi, mumbai, chenn, kolkata)));
		chenn.setNeighbors(new HashSet<CityModel>(Arrays.asList(mumbai, delhi, kolkata, bangkok, jakarta)));
		kolkata.setNeighbors(new HashSet<CityModel>(Arrays.asList(delhi, chenn, bangkok, hongKong)));
		
		bangkok.setNeighbors(new HashSet<CityModel>(Arrays.asList(kolkata, chenn, jakarta, hoChi, hongKong)));
		jakarta.setNeighbors(new HashSet<CityModel>(Arrays.asList(chenn, bangkok, hoChi, sydney)));
		hoChi.setNeighbors(new HashSet<CityModel>(Arrays.asList(jakarta, bangkok, hongKong, manila)));
		hongKong.setNeighbors(new HashSet<CityModel>(Arrays.asList(kolkata, bangkok, hoChi, shang, taipei, manila)));
		beijing.setNeighbors(new HashSet<CityModel>(Arrays.asList(shang, seoul)));
		seoul.setNeighbors(new HashSet<CityModel>(Arrays.asList(shang, tokyo, beijing)));
		taipei.setNeighbors(new HashSet<CityModel>(Arrays.asList(hongKong, shang, osaka, manila)));
		manila.setNeighbors(new HashSet<CityModel>(Arrays.asList(sanFran, taipei, hongKong, hoChi, sydney)));
		tokyo.setNeighbors(new HashSet<CityModel>(Arrays.asList(sanFran, seoul, osaka, shang)));
		osaka.setNeighbors(new HashSet<CityModel>(Arrays.asList(tokyo, taipei)));
		sydney.setNeighbors(new HashSet<CityModel>(Arrays.asList(manila, jakarta, la)));
		shang.setNeighbors(new HashSet<CityModel>(Arrays.asList(beijing, seoul, tokyo, taipei, hongKong)));
		
		la.setNeighbors(new HashSet<CityModel>(Arrays.asList(sanFran, chicago, sydney, mexicoCity)));
		mexicoCity.setNeighbors(new HashSet<CityModel>(Arrays.asList(la, chicago, miami, bogata, lima)));
		miami.setNeighbors(new HashSet<CityModel>(Arrays.asList(bogata, mexicoCity, atlanta, wash)));
		bogata.setNeighbors(new HashSet<CityModel>(Arrays.asList(lima, mexicoCity, miami, saoPalo, bueno)));
		lima.setNeighbors(new HashSet<CityModel>(Arrays.asList(bogata, mexicoCity, santi)));
		santi.setNeighbors(new HashSet<CityModel>(Arrays.asList(lima)));
		bueno.setNeighbors(new HashSet<CityModel>(Arrays.asList(bogata, saoPalo)));
		saoPalo.setNeighbors(new HashSet<CityModel>(Arrays.asList(bogata, bueno, madrid, lago)));
		lago.setNeighbors(new HashSet<CityModel>(Arrays.asList(saoPalo, kinsha, khar)));
		kinsha.setNeighbors(new HashSet<CityModel>(Arrays.asList(lago, khar, johann)));
		johann.setNeighbors(new HashSet<CityModel>(Arrays.asList(kinsha, khar)));
		khar.setNeighbors(new HashSet<CityModel>(Arrays.asList(lago, kinsha, johann, cairo)));
		
		sanFran.setNeighbors(new HashSet<CityModel>(Arrays.asList(chicago, la, manila, tokyo)));
		chicago.setNeighbors(new HashSet<CityModel>(Arrays.asList(sanFran, atlanta, la, mexicoCity, mont)));
		atlanta.setNeighbors(new HashSet<CityModel>(Arrays.asList(wash, chicago, miami)));
		mont.setNeighbors(new HashSet<CityModel>(Arrays.asList(nyc, wash, chicago)));
		wash.setNeighbors(new HashSet<CityModel>(Arrays.asList(nyc, mont, miami, atlanta)));
		nyc.setNeighbors(new HashSet<CityModel>(Arrays.asList(madrid, london, wash, mont)));
		london.setNeighbors(new HashSet<CityModel>(Arrays.asList(nyc, madrid, paris, essen)));
		madrid.setNeighbors(new HashSet<CityModel>(Arrays.asList(nyc, london, paris, algi, saoPalo)));
		paris.setNeighbors(new HashSet<CityModel>(Arrays.asList(milan, essen, london, madrid, algi)));
		essen.setNeighbors(new HashSet<CityModel>(Arrays.asList(london, paris, milan, stPete)));
		milan.setNeighbors(new HashSet<CityModel>(Arrays.asList(paris, essen, istan)));
		stPete.setNeighbors(new HashSet<CityModel>(Arrays.asList(essen, istan, moscow)));
		
		algi.setNeighbors(new HashSet<CityModel>(Arrays.asList(madrid, paris, istan, cairo)));
		cairo.setNeighbors(new HashSet<CityModel>(Arrays.asList(algi, baghdad, istan, ridya, khar)));
		istan.setNeighbors(new HashSet<CityModel>(Arrays.asList(milan, stPete, algi, cairo, baghdad, moscow)));
		moscow.setNeighbors(new HashSet<CityModel>(Arrays.asList(stPete, istan, tehran)));
		baghdad.setNeighbors(new HashSet<CityModel>(Arrays.asList(tehran, istan, cairo, ridya, karachi)));
		ridya.setNeighbors(new HashSet<CityModel>(Arrays.asList(cairo, baghdad, karachi)));
		tehran.setNeighbors(new HashSet<CityModel>(Arrays.asList(moscow, baghdad, karachi, delhi)));
		karachi.setNeighbors(new HashSet<CityModel>(Arrays.asList(ridya, baghdad, delhi, mumbai, tehran)));
		mumbai.setNeighbors(new HashSet<CityModel>(Arrays.asList(karachi, delhi, chenn)));
		delhi.setNeighbors(new HashSet<CityModel>(Arrays.asList(tehran, karachi, mumbai, chenn, kolkata)));
		chenn.setNeighbors(new HashSet<CityModel>(Arrays.asList(mumbai, delhi, kolkata, bangkok, jakarta)));
		kolkata.setNeighbors(new HashSet<CityModel>(Arrays.asList(delhi, chenn, bangkok, hongKong)));
		
		bangkok.setNeighbors(new HashSet<CityModel>(Arrays.asList(kolkata, chenn, jakarta, hoChi, hongKong)));
		jakarta.setNeighbors(new HashSet<CityModel>(Arrays.asList(chenn, bangkok, hoChi, sydney)));
		hoChi.setNeighbors(new HashSet<CityModel>(Arrays.asList(jakarta, bangkok, hongKong, manila)));
		hongKong.setNeighbors(new HashSet<CityModel>(Arrays.asList(kolkata, bangkok, hoChi, shang, taipei, manila)));
		beijing.setNeighbors(new HashSet<CityModel>(Arrays.asList(shang, seoul)));
		seoul.setNeighbors(new HashSet<CityModel>(Arrays.asList(shang, tokyo, beijing)));
		taipei.setNeighbors(new HashSet<CityModel>(Arrays.asList(hongKong, shang, osaka, manila)));
		manila.setNeighbors(new HashSet<CityModel>(Arrays.asList(sanFran, taipei, hongKong, hoChi, sydney)));
		tokyo.setNeighbors(new HashSet<CityModel>(Arrays.asList(sanFran, seoul, osaka, shang)));
		osaka.setNeighbors(new HashSet<CityModel>(Arrays.asList(tokyo, taipei)));
		sydney.setNeighbors(new HashSet<CityModel>(Arrays.asList(manila, jakarta, la)));
		shang.setNeighbors(new HashSet<CityModel>(Arrays.asList(beijing, seoul, tokyo, taipei, hongKong)));
		
		la.setNeighbors(new HashSet<CityModel>(Arrays.asList(sanFran, chicago, sydney, mexicoCity)));
		mexicoCity.setNeighbors(new HashSet<CityModel>(Arrays.asList(la, chicago, miami, bogata, lima)));
		miami.setNeighbors(new HashSet<CityModel>(Arrays.asList(bogata, mexicoCity, atlanta, wash)));
		bogata.setNeighbors(new HashSet<CityModel>(Arrays.asList(lima, mexicoCity, miami, saoPalo, bueno)));
		lima.setNeighbors(new HashSet<CityModel>(Arrays.asList(bogata, mexicoCity, santi)));
		santi.setNeighbors(new HashSet<CityModel>(Arrays.asList(lima)));
		bueno.setNeighbors(new HashSet<CityModel>(Arrays.asList(bogata, saoPalo)));
		saoPalo.setNeighbors(new HashSet<CityModel>(Arrays.asList(bogata, bueno, madrid, lago)));
		lago.setNeighbors(new HashSet<CityModel>(Arrays.asList(saoPalo, kinsha, khar)));
		kinsha.setNeighbors(new HashSet<CityModel>(Arrays.asList(lago, khar, johann)));
		johann.setNeighbors(new HashSet<CityModel>(Arrays.asList(kinsha, khar)));
		khar.setNeighbors(new HashSet<CityModel>(Arrays.asList(lago, kinsha, johann, cairo)));
		
		cities.put(sanFran.getName(), sanFran);
		cities.put(chicago.getName(), chicago);
		cities.put(atlanta.getName(), atlanta);
		cities.put(mont.getName(), mont);
		cities.put(wash.getName(), wash);
		cities.put(nyc.getName(), nyc);
		cities.put(london.getName(), london);
		cities.put(madrid.getName(), madrid);
		cities.put(paris.getName(), paris);
		cities.put(essen.getName(), essen);
		cities.put(milan.getName(), milan);
		cities.put(stPete.getName(), stPete);
		
		cities.put(algi.getName(), algi);
		cities.put(cairo.getName(), cairo);
		cities.put(istan.getName(), istan);
		cities.put(moscow.getName(), moscow);
		cities.put(baghdad.getName(), baghdad);
		cities.put(ridya.getName(), ridya);
		cities.put(tehran.getName(), tehran);
		cities.put(karachi.getName(), karachi);
		cities.put(mumbai.getName(), mumbai);
		cities.put(delhi.getName(), delhi);
		cities.put(chenn.getName(), chenn);
		cities.put(kolkata.getName(), kolkata);
		
		cities.put(bangkok.getName(), bangkok);
		cities.put(jakarta.getName(), jakarta);
		cities.put(hoChi.getName(), hoChi);
		cities.put(hongKong.getName(), hongKong);
		cities.put(beijing.getName(), beijing);
		cities.put(seoul.getName(), seoul);
		cities.put(taipei.getName(), taipei);
		cities.put(manila.getName(), manila);
		cities.put(tokyo.getName(), tokyo);
		cities.put(osaka.getName(), osaka);
		cities.put(sydney.getName(), sydney);
		cities.put(shang.getName(), shang);
		
		cities.put(la.getName(), la);
		cities.put(mexicoCity.getName(), mexicoCity);
		cities.put(miami.getName(), miami);
		cities.put(bogata.getName(), bogata);
		cities.put(lima.getName(), lima);
		cities.put(santi.getName(), santi);
		cities.put(bueno.getName(), bueno);
		cities.put(saoPalo.getName(), saoPalo);
		cities.put(lago.getName(), lago);
		cities.put(kinsha.getName(), kinsha);
		cities.put(johann.getName(), johann);
		cities.put(khar.getName(), khar);
	}
	
	private void initializeCityDiseases(DiseaseController diseaseController){
		for(CityModel c : this.cities.values()){
			c.setCubesByDisease(diseaseController.getBlueDisease(), 0);
			c.setCubesByDisease(diseaseController.getBlackDisease(), 0);
			c.setCubesByDisease(diseaseController.getRedDisease(), 0);
			c.setCubesByDisease(diseaseController.getYellowDisease(), 0);
		}
	}
}
