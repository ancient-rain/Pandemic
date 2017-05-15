package city;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static constants.City.*;
import static constants.Game.OFFSET_1;
import static constants.Game.OFFSET_2;
import static constants.Game.OFFSET_40;

public class CityView {
	CityController controller;
	Map<String, CityFrontEndModel> citiesToDraw;
	
	public CityView(CityController controller) {
		this.controller = controller;
		this.citiesToDraw = new HashMap<>();
	}
	
	public CityFrontEndModel getCityToDraw(String cityName){
		return this.citiesToDraw.get(cityName);
	}
	
	public void paintCities(Graphics gr) {
		updateCitiesToDraw();
		paintNeighborPaths(gr);
		paintCityLocations(gr);
	}
	
	private void updateCitiesToDraw() {	
		for (CityModel c : this.controller.getCities()) {
			CityFrontEndModel city = new CityFrontEndModel(c);
			String name = c.getName();
			
			this.citiesToDraw.put(name, city);
		}

		updateBlueCities(Color.BLUE);
		updateYellowCities(Color.YELLOW);
		updateBlackCities(Color.BLACK);
		updateRedCities(Color.RED);
	}
	
	private void paintNeighborPaths(Graphics gr) {
		Set<String> outerNeighbors = new HashSet<>();
		
		gr.setColor(Color.WHITE);
		outerNeighbors.add(SYDNEY);
		outerNeighbors.add(TOKYO);
		outerNeighbors.add(MANILA);
		outerNeighbors.add(SAN_FRANCISCO);
		outerNeighbors.add(LOS_ANGELES);
		
		for (CityFrontEndModel city : this.citiesToDraw.values()) {
			Set<CityModel> neighbors = city.getCityModel().getNeighbors();
			String cityName = city.getCityModel().getName();
			int cityX = city.getX() + DELTA;
			int cityY = city.getY() + DELTA;
			
			for (CityModel c : neighbors) {
				String name = c.getName();
				int xloc = this.citiesToDraw.get(name).getX() + DELTA;
				int yloc = this.citiesToDraw.get(name).getY() + DELTA;
				
				if (!outerNeighbors.contains(name)) {
					gr.drawLine(cityX, cityY, xloc, yloc);
				} else if (cityName.equals(SAN_FRANCISCO) && name.equals(LOS_ANGELES)) {
					gr.drawLine(cityX, cityY, xloc, yloc);
				} else if (cityName.equals(LOS_ANGELES) && name.equals(SAN_FRANCISCO)) {
					gr.drawLine(cityX, cityY, xloc, yloc);
				} else if (cityName.equals(MANILA) && name.equals(SYDNEY)) {
					gr.drawLine(cityX, cityY, xloc, yloc);
				} else if (cityName.equals(SYDNEY) && name.equals(MANILA)) {
					gr.drawLine(cityX, cityY, xloc, yloc);
				}
			}
		}
		
		paintAcrossWorldNeighbors(gr);
	}
	
	private void paintAcrossWorldNeighbors(Graphics gr) {
		CityFrontEndModel fakeSydneyToLA = new CityFrontEndModel(1940, 630);
		CityFrontEndModel fakeLAToSydney = new CityFrontEndModel(196, 630);
		CityFrontEndModel fakeTokyoToSF = new CityFrontEndModel(1940, 368);
		CityFrontEndModel fakeSFToTokyo = new CityFrontEndModel(196, 368);
		CityFrontEndModel fakeManilaToSF = new CityFrontEndModel(1940, 487);
		CityFrontEndModel fakeSFToManila = new CityFrontEndModel(196, 487);
		CityFrontEndModel sydney = this.citiesToDraw.get(SYDNEY);
		CityFrontEndModel la = this.citiesToDraw.get(LOS_ANGELES);
		CityFrontEndModel tokyo = this.citiesToDraw.get(TOKYO);
		CityFrontEndModel sf = this.citiesToDraw.get(SAN_FRANCISCO);
		CityFrontEndModel manila = this.citiesToDraw.get(MANILA);

		gr.drawLine(fakeSydneyToLA.getX() + DELTA, fakeSydneyToLA.getY() + DELTA,
				sydney.getX() + DELTA, sydney.getY() + DELTA);
		gr.drawLine(fakeLAToSydney.getX() + DELTA, fakeLAToSydney.getY() + DELTA,
				la.getX() + DELTA, la.getY() + DELTA);
		gr.drawLine(fakeTokyoToSF.getX() + DELTA, fakeTokyoToSF.getY() + DELTA,
				tokyo.getX() + DELTA, tokyo.getY() + DELTA);
		gr.drawLine(fakeSFToTokyo.getX() + DELTA, fakeSFToTokyo.getY() + DELTA,
				sf.getX() + DELTA, sf.getY() + DELTA);
		gr.drawLine(fakeManilaToSF.getX() + DELTA, fakeManilaToSF.getY() + DELTA,
				manila.getX() + DELTA, manila.getY() + DELTA);
		gr.drawLine(fakeSFToManila.getX() + DELTA, fakeSFToManila.getY() + DELTA,
				sf.getX() + DELTA, sf.getY() + DELTA);
	}
	
	private void paintCityLocations(Graphics gr) {
		for (CityFrontEndModel city : this.citiesToDraw.values()) {
			int xloc = city.getX();
			int yloc = city.getY();
			String name = city.getCityModel().getName();
			
			gr.setColor(city.getColor());
			gr.fillOval(xloc, yloc, CITY_RADIUS, CITY_RADIUS);
			gr.setColor(Color.DARK_GRAY);
			gr.drawString(name, xloc, yloc + OFFSET_40);
			
			if (city.getCityModel().hasResearchStation()) {
				paintResearchStation(gr, city);
			}
		}
	}
	
	private void paintResearchStation(Graphics gr, CityFrontEndModel city) {
		int xloc = city.getX();
		int yloc = city.getY();
		int xleft = xloc + RESEARCH_STATION_SIDE_OFFSET;
		int xright = xloc + RESEARCH_STATION_BOTTOM_OFFSET;
		int ybottom = yloc + RESEARCH_STATION_BOTTOM_OFFSET;
		int ymiddle = yloc + RESEARCH_STATION_MIDDLE_OFFSET;
		int topX = xloc + DELTA;
		int topY = yloc + RESEARCH_STATION_TOP_OFFSET;
		int[] leftXTriangle = new int[] { xleft, topX, topX };
		int[] leftYTriangle = new int[] { ymiddle, topY, ymiddle };
		int[] rightXTriangle = new int[] { xright, topX, topX };
		int[] rightYTriangle = new int[] { ymiddle, topY, ymiddle };

		gr.setColor(Color.WHITE);
		gr.fillRect(xleft, ymiddle, xright - xleft, ybottom - ymiddle);
		gr.fillPolygon(leftXTriangle, leftYTriangle, leftXTriangle.length);
		gr.fillPolygon(rightXTriangle, rightYTriangle, rightXTriangle.length);

		gr.setColor(Color.BLACK);
		gr.drawLine(xleft, ymiddle, xleft, ybottom);
		gr.drawLine(xleft + OFFSET_1, ymiddle, xleft + OFFSET_1, ybottom);
		gr.drawLine(xright, ymiddle, xright, ybottom);
		gr.drawLine(xright - OFFSET_1, ymiddle, xright - OFFSET_1, ybottom);
		gr.drawLine(xleft, ybottom, xright, ybottom);
		gr.drawLine(xleft, ybottom - OFFSET_1, xright, ybottom - OFFSET_1);
		gr.drawLine(xleft, ymiddle, topX, topY);
		gr.drawLine(xleft + OFFSET_1, ymiddle, topX, topY + OFFSET_1);
		gr.drawLine(xleft + OFFSET_2, ymiddle, topX, topY + OFFSET_2);
		gr.drawLine(xright, ymiddle, topX, topY);
		gr.drawLine(xright - OFFSET_1, ymiddle, topX, topY + OFFSET_1);
		gr.drawLine(xright - OFFSET_2, ymiddle, topX, topY + OFFSET_2);

	}
	
	private void updateBlueCities(Color color) {
		this.citiesToDraw.get(SAN_FRANCISCO).setColor(color);
		this.citiesToDraw.get(SAN_FRANCISCO).setLocation(400, 380);
		this.citiesToDraw.get(CHICAGO).setColor(color);
		this.citiesToDraw.get(CHICAGO).setLocation(550, 320);
		this.citiesToDraw.get(ATLANTA).setColor(color);
		this.citiesToDraw.get(ATLANTA).setLocation(590, 400);
		this.citiesToDraw.get(MONTREAL).setColor(color);
		this.citiesToDraw.get(MONTREAL).setLocation(650, 310);
		this.citiesToDraw.get(WASHINGTON).setColor(color);
		this.citiesToDraw.get(WASHINGTON).setLocation(680, 390);
		this.citiesToDraw.get(NEW_YORK).setColor(color);
		this.citiesToDraw.get(NEW_YORK).setLocation(730, 355);
		this.citiesToDraw.get(LONDON).setColor(color);
		this.citiesToDraw.get(LONDON).setLocation(940, 270);
		this.citiesToDraw.get(MADRID).setColor(color);
		this.citiesToDraw.get(MADRID).setLocation(920, 365);
		this.citiesToDraw.get(PARIS).setColor(color);
		this.citiesToDraw.get(PARIS).setLocation(1010, 330);
		this.citiesToDraw.get(ESSEN).setColor(color);
		this.citiesToDraw.get(ESSEN).setLocation(1035, 255);
		this.citiesToDraw.get(MILAN).setColor(color);
		this.citiesToDraw.get(MILAN).setLocation(1080, 305);
		this.citiesToDraw.get(ST_PETERSBURG).setColor(color);
		this.citiesToDraw.get(ST_PETERSBURG).setLocation(1150, 240);
	}
	
	private void updateYellowCities(Color color) {
		this.citiesToDraw.get(LOS_ANGELES).setColor(color);
		this.citiesToDraw.get(LOS_ANGELES).setLocation(440, 450);
		this.citiesToDraw.get(MEXICO_CITY).setColor(color);
		this.citiesToDraw.get(MEXICO_CITY).setLocation(525, 490);
		this.citiesToDraw.get(MIAMI).setColor(color);
		this.citiesToDraw.get(MIAMI).setLocation(650, 480);
		this.citiesToDraw.get(BOGOTA).setColor(color);
		this.citiesToDraw.get(BOGOTA).setLocation(640, 585);
		this.citiesToDraw.get(LIMA).setColor(color);
		this.citiesToDraw.get(LIMA).setLocation(610, 710);
		this.citiesToDraw.get(SANTIAGO).setColor(color);
		this.citiesToDraw.get(SANTIAGO).setLocation(625, 850);
		this.citiesToDraw.get(BUENOS_AIRES).setColor(color);
		this.citiesToDraw.get(BUENOS_AIRES).setLocation(730, 830);
		this.citiesToDraw.get(SAO_PAULO).setColor(color);
		this.citiesToDraw.get(SAO_PAULO).setLocation(795, 735);
		this.citiesToDraw.get(JOHANNESBURG).setColor(color);
		this.citiesToDraw.get(JOHANNESBURG).setLocation(1130, 775);
		this.citiesToDraw.get(KHARTOUM).setColor(color);
		this.citiesToDraw.get(KHARTOUM).setLocation(1140, 545);
		this.citiesToDraw.get(LAGOS).setColor(color);
		this.citiesToDraw.get(LAGOS).setLocation(1000, 575);
		this.citiesToDraw.get(KINSHASA).setColor(color);
		this.citiesToDraw.get(KINSHASA).setLocation(1060, 655);
	}
	
	private void updateBlackCities(Color color) {
		this.citiesToDraw.get(CAIRO).setColor(color);
		this.citiesToDraw.get(CAIRO).setLocation(1110, 445);
		this.citiesToDraw.get(ALGIERS).setColor(color);
		this.citiesToDraw.get(ALGIERS).setLocation(1030, 415);
		this.citiesToDraw.get(ISTANBUL).setColor(color);
		this.citiesToDraw.get(ISTANBUL).setLocation(1120, 370);
		this.citiesToDraw.get(MOSCOW).setColor(color);
		this.citiesToDraw.get(MOSCOW).setLocation(1210, 310);
		this.citiesToDraw.get(BAGHDAD).setColor(color);
		this.citiesToDraw.get(BAGHDAD).setLocation(1190, 410);
		this.citiesToDraw.get(RIYADH).setColor(color);
		this.citiesToDraw.get(RIYADH).setLocation(1215, 500);
		this.citiesToDraw.get(KARACHI).setColor(color);
		this.citiesToDraw.get(KARACHI).setLocation(1300, 445);
		this.citiesToDraw.get(TEHRAN).setColor(color);
		this.citiesToDraw.get(TEHRAN).setLocation(1280, 345);
		this.citiesToDraw.get(DELHI).setColor(color);
		this.citiesToDraw.get(DELHI).setLocation(1365, 400);
		this.citiesToDraw.get(MUMBAI).setColor(color);
		this.citiesToDraw.get(MUMBAI).setLocation(1315, 520);
		this.citiesToDraw.get(CHENNAI).setColor(color);
		this.citiesToDraw.get(CHENNAI).setLocation(1395, 570);
		this.citiesToDraw.get(KOLKATA).setColor(color);
		this.citiesToDraw.get(KOLKATA).setLocation(1440, 425);
	}
	
	private void updateRedCities(Color color) {
		this.citiesToDraw.get(JAKARTA).setColor(color);
		this.citiesToDraw.get(JAKARTA).setLocation(1460, 675);
		this.citiesToDraw.get(BANGKOK).setColor(color);
		this.citiesToDraw.get(BANGKOK).setLocation(1465, 520);
		this.citiesToDraw.get(HO_CHI_MINH_CITY).setColor(color);
		this.citiesToDraw.get(HO_CHI_MINH_CITY).setLocation(1530, 610);
		this.citiesToDraw.get(HONG_KONG).setColor(color);
		this.citiesToDraw.get(HONG_KONG).setLocation(1525, 480);
		this.citiesToDraw.get(MANILA).setColor(color);
		this.citiesToDraw.get(MANILA).setLocation(1635, 605);
		this.citiesToDraw.get(TAIPEI).setColor(color);
		this.citiesToDraw.get(TAIPEI).setLocation(1615, 460);
		this.citiesToDraw.get(SHANGHAI).setColor(color);
		this.citiesToDraw.get(SHANGHAI).setLocation(1515, 395);
		this.citiesToDraw.get(BEIJING).setColor(color);
		this.citiesToDraw.get(BEIJING).setLocation(1505, 320);
		this.citiesToDraw.get(SEOUL).setColor(color);
		this.citiesToDraw.get(SEOUL).setLocation(1605, 315);
		this.citiesToDraw.get(TOKYO).setColor(color);
		this.citiesToDraw.get(TOKYO).setLocation(1680, 355);
		this.citiesToDraw.get(OSAKA).setColor(color);
		this.citiesToDraw.get(OSAKA).setLocation(1690, 430);
		this.citiesToDraw.get(SYDNEY).setColor(color);
		this.citiesToDraw.get(SYDNEY).setLocation(1715, 810);
	}

}
