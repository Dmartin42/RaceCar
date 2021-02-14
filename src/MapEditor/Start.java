package MapEditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicArrowButton;

import Infrastructure.Runner;

public class Start {
	public  boolean placed = false;
	private int x, y;
	private int width = 75;
	private int height = 150;
	public static final int TILE_WIDTH = 20;
	public static final int TILE_HEIGHT = 20;
	private double rotation = 0;
	
	
	public double getRotation() {
		return rotation;
	}




	public void setRotation(double rotation) {
		this.rotation = rotation;
	}
	public List<Rectangle> blueTiles = new ArrayList<>();
	public static Rectangle[] tiles = new Rectangle[12];
	

	public Start() {}




	public void show(Graphics g, int x, int y) {
		drawTag((Graphics2D) g,x,y);
	}
	public void show(Graphics g) {
		drawTag((Graphics2D)g,this.x,this.y);
	}

	private void drawTag(Graphics2D g2d, int tempX, int tempY) {
		if(this.isPlaced()) {
			tempX = this.getX();
			tempY = this.getY();
		}
			
		Graphics2D g = (Graphics2D) g2d; 
		AffineTransform old = g.getTransform();
		g.rotate(Math.toRadians(rotation), tempX + this.width / 2,  tempY + this.height / 2);
		g.setColor(Color.BLUE);
		for (int i = 0; i < tiles.length; i ++) {
			Color color = Color.BLACK;
			if ((i % 2 == 0 && i <= 5) || (i % 2 == 1 && i > 5)) {
				color = Color.WHITE;
			} else if ((i % 2 == 1 && i <= 5) || (i % 2 == 0 && i > 5)) {
				color = Color.BLACK;
			}
			g.setColor(color);
			if (i == 0) {
				tiles[i] = new Rectangle(tempX, tempY, TILE_WIDTH, TILE_HEIGHT);
			} else if (i <= 5) {
				tiles[i] = new Rectangle(tiles[(i - 1)].x + TILE_WIDTH, tiles[(i - 1)].y, TILE_WIDTH,TILE_HEIGHT);
			} else if (i == 6) {
				tiles[i] = new Rectangle(tempX, tempY + TILE_HEIGHT, TILE_WIDTH,TILE_HEIGHT);
			} else if (i <= 11) {
				tiles[i] = new Rectangle(tiles[(i - 1)].x + TILE_WIDTH, tiles[(i - 1)].y, TILE_WIDTH,TILE_HEIGHT);
			}
			g.fill(tiles[i]);
			g.setColor(Color.CYAN);
		}
		g.drawRect(tempX, tempY, 180,60);
		g.setTransform(old);
	}

	public void setPlaced(boolean p) {
		placed = p;
	}
	public boolean isPlaced() {
		return placed;
	}
	public void setLocation(Point p) {
		this.setX(p.x);
		this.setY(p.y);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	public  Rectangle getBounds() {
		return new Rectangle(getX(),getY(),getWidth(),getHeight());
	}

}
