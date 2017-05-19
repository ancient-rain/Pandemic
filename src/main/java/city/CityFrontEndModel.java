package city;

import java.awt.Color;
import java.awt.Point;

public class CityFrontEndModel {
	CityModel city;
	Color color;
	Point point;

	public CityFrontEndModel(int xlocation, int ylocation) {
		this.point = new Point(xlocation, ylocation);
	}
	
	public CityFrontEndModel(CityModel city) {
		this.city = city;
		this.color = Color.WHITE;
		this.point = new Point(0, 0);
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public void setLocation(int xlocation, int ylocation) {
		this.point.setLocation(new Point(xlocation, ylocation));
	}
	
	public int getX() {
		return (int) this.point.getX();
	}
	
	public int getY() {
		return (int) this.point.getY();
	}
	
	public CityModel getCityModel() {
		return this.city;
	}
}
