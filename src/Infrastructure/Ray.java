package Infrastructure;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.LinkedList;
import static java.lang.Math.*;

public class Ray {
	private double angle; 
	public Ray(double x, double y, double angle) {
		this.angle = angle; 
	}
	public double getAngle() {
		return angle;
	}
	public void setAngle(double angle) {
		this.angle = angle;
	}
}
