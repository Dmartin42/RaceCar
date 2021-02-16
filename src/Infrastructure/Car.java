package Infrastructure;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

/**
 * 
 * @author Daniel Martin
 * 
 *
 */
public class Car {
	@Override
	public String toString() {
		return "Car [x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + ", velX=" + velX + ", velY="
				+ velY + ", position=" + position + ", centerPosition=" + centerPosition + "]";
	}

	public static final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	private static final double MAX_VELOCITY = 30;
	private List<Ray> rays = new ArrayList<>();
	private double x, y, width, height;
	private double rotation;
	private double velX, velY;
	private double acc;
	private String vectorVelocity = "0";
	private final static ImageIcon CAR_SOURCE = new ImageIcon("Resources/FinalCar.PNG");
	public static final boolean DEBUG = true;
	public static Image car = CAR_SOURCE.getImage();
	private int playerNumber;
	private int Lap;
	private Point position;
	private Point centerPosition;
	

	public void setPlayerNumber(int PlayerNumber) {
		playerNumber = PlayerNumber;
	}

	public void addRays() {
		for(int i = 0; i < 180; i+=5) {
			rays.add(new Ray(this.x,this.y,toRadians(-i)));
		}

	}

	public Car(int x, int y) {
		this.setPosition(x, y);
		this.setX((int) x);
		this.setY((int) y);
		this.width = 50;
		this.height = 75;
		this.rotation = 260;
		this.velX = 0;
		this.velY = 0;
		this.acc = 0;
		this.move();
	}

	public Car(Point startingPoint) {
		this.setPosition(startingPoint);
		this.x = startingPoint.getX();
		this.y = startingPoint.getY();
		this.width = 50;
		this.height = 75;
		this.rotation = 260;
		this.velX = 0;
		this.velY = 0;
		this.acc = 0;
		this.move();

	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.position.x = (int) x;
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.position.y = (int) y;
		this.y = y;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public double getRotation() {
		return rotation;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}
	public void show(Graphics2D g) {
		Graphics2D g2d = (Graphics2D) g; // Car
		AffineTransform old = g2d.getTransform();
		AffineTransform newT = g2d.getTransform();
		newT.setToRotation(toRadians(rotation), centerPosition.x, centerPosition.y);
		g2d.setTransform(newT);
		if (DEBUG) {
			g2d.setColor(Color.pink);
			g2d.drawRect(centerPosition.x,centerPosition.y, (int) width, (int) height);
		}
		g2d.drawImage(car, this.centerPosition.x, this.centerPosition.y, (int) this.width, (int) this.height, Runner.frame);
		g2d.setTransform(old);

		if (DEBUG) {
			Font oldFont = g2d.getFont();
			g2d.setFont(new Font("Bodoni MT", Font.PLAIN, 11));
			g.setColor(Color.BLACK);

			g2d.drawString("" + this.getPlayerNumber(), (int) this.x - (int) this.width / 2, (int) this.y); // Shows
																											// Player
																											// Number
			g2d.drawString("R_" + getRotation(),this.centerPosition.x, this.centerPosition.y); 				// Shows
																											// Rotation

			g2d.drawString(vectorVelocity + " ", (int) ((int) this.x + this.width), (int) this.y);
			g2d.setFont(oldFont);
		}

	}

	public int getPlayerNumber() {
		return playerNumber;
	}

	public double getVelX() {
		return velX;
	}

	public void setVelX(double velX) {
		this.velX = velX;
	}

	public double getVelY() {
		return velY;
	}

	public void setVelY(double velY) {
		this.velY = velY;
	}

	public void move() {

		if (getRotation() > 360 || getRotation() < -360)
			setRotation(0);

		this.velX += acc * Math.sin(Math.toRadians(getRotation()));
		this.velY += acc * Math.cos(Math.toRadians(getRotation()));
		if (velX < 1 && velX > -1) {
			velX = 0;
		}
		if (velY < 1 && velY > -1) {
			velY = 0;
		}

		DecimalFormat f = new DecimalFormat("##.00"); // Formats Decimal to two digits
		velX = Double.parseDouble(f.format(velX));
		velY = Double.parseDouble(f.format(velY));
		this.checkVelocity();

		if (DEBUG)
			System.out.println(this.toString());

		this.y -= this.velY;
		this.x += this.velX;
		velX *= .800;
		velY *= .750;

		this.setPosition(x, y);
		// update center position of vehicle
		this.centerPosition = new Point((int) (this.x + this.width/2), (int) (this.y + this.height/2));

	}

	/**
	 * Checks if car is moving too fast (ie above maxVelocity)
	 * 
	 * @value Max_velocity = 30;
	 */

	private void checkVelocity() {
		if (velY >= MAX_VELOCITY)
			velY = MAX_VELOCITY;

		if (velY <= -MAX_VELOCITY)
			velY = -MAX_VELOCITY;

		if (velX >= MAX_VELOCITY)
			velX = MAX_VELOCITY;

		if (velX <= -MAX_VELOCITY)
			velX = -MAX_VELOCITY;
	}

	/**
	 * Prints Velocities as a vector in component form
	 */
	private void printCompVelocities() {
		vectorVelocity = "<" + velX + "," + velY + ">";
		if (velX != 0 && velY != 0)
			System.out.println("Velocity <" + velX + "," + velY + ">");
	}

	public double getAcc() {
		return acc;
	}

	public void setAcc(double acc) {
		this.acc = acc;
	}

	private Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, (int) width, (int) height);
	}

	public Rectangle2D getCarBounds2D() {
		return getBounds().getBounds2D();
	}

	public boolean checkCollision(Rectangle object) {
		return getBounds().intersects(object);

	}

	public int getLap() {
		return Lap;
	}

	public void setLap(int lap) {
		Lap = lap;
	}

	/**
	 * @return the position of the car
	 */
	public Point getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Point position) {
		this.position = position;
		this.x = this.position.getY();
		this.y = this.position.getY();
	}

	public void setPosition(int x, int y) {
		Point newPos = new Point(x, y);
		this.position = newPos;
		this.setX(x);
		this.setY(y);
	}

	public void setPosition(double x, double y) {
		Point newPos = new Point();
		newPos.setLocation(x, y);
		this.position = newPos;
		this.setX(x);
		this.setY(y);
	}

	public void look(List<Line2D> walls, Graphics g) {
		for (Ray ray : rays) {
			Point closest = null;
			double record = Double.POSITIVE_INFINITY;
			for (Line2D wall : walls) {
				float d = getRayCast(centerPosition.x, centerPosition.y,
						centerPosition.x + (float) cos(ray.getAngle()) * (int) record,
						centerPosition.y + (float) sin(ray.getAngle()) * (int) record, wall.getX1(), wall.getY1(),
						wall.getX2(), wall.getY2());
				if (d < record && d > 0) {
					closest = new Point();
					record = d;
					closest.setLocation(centerPosition.x + (float) cos(ray.getAngle()) * record,
							centerPosition.y + (float) sin(ray.getAngle()) * record);

				}
			}
			if (closest != null) {
				
				g.drawLine(centerPosition.x, centerPosition.y, closest.x, closest.y);
				g.fillOval(closest.x, closest.y, 10, 10);
			}
		}
	}

	private float getRayCast(double x2, double y2, double d, double e, double x1, double y1, double x22, double y22) {
		return getRayCast((float) x2, (float) y2, (float) d, (float) e, (float) x1, (float) y1, (float) x22,
				(float) y22);
	}

	private static double dist(float x1, float y1, float x2, float y2) {
		return (double) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}

	private static float getRayCast(float p0_x, float p0_y, float p1_x, float p1_y, float p2_x, float p2_y, float p3_x,
			float p3_y) {
		float s1_x, s1_y, s2_x, s2_y;
		s1_x = p1_x - p0_x;
		s1_y = p1_y - p0_y;
		s2_x = p3_x - p2_x;
		s2_y = p3_y - p2_y;

		float s, t;
		s = (-s1_y * (p0_x - p2_x) + s1_x * (p0_y - p2_y)) / (-s2_x * s1_y + s1_x * s2_y);
		t = (s2_x * (p0_y - p2_y) - s2_y * (p0_x - p2_x)) / (-s2_x * s1_y + s1_x * s2_y);

		if (s >= 0 && s <= 1 && t >= 0 && t <= 1) {
			// Collision detected
			float x = p0_x + (t * s1_x);
			float y = p0_y + (t * s1_y);

			return (float) dist(p0_x, p0_y, x, y);
		}
		return -1;
	}

	public void rotation(double d) {
		for (int i = 0; i < rays.size(); i++) {
			double angle = rays.get(i).getAngle();
			rays.get(i).setAngle(angle + toRadians(d));
		}

	}

}
