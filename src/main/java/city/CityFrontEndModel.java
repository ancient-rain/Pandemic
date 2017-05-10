package city;

import java.awt.Color;
import java.awt.Point;

public class CityFrontEndModel {
	CityModel city;
	Color color;
	Point point;
	// Might need something for diseases

	public CityFrontEndModel(int x, int y) {
		this.point = new Point(x, y);
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
	
	public void setLocation(int x, int y) {
		this.point.setLocation(new Point(x, y));
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
