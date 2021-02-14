package MapEditor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import Infrastructure.Car;
import Infrastructure.RaceTrack;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateMap extends JPanel implements ActionListener {
	public static List<Rectangle> road = new ArrayList<>();
	private static final int TILE_HEIGHT = 10;
	private static final int TILE_WIDTH = 10;
	public static int mouseX, mouseY;
	public static Start start;
	public Timer time = new Timer(100,this);
	//public Menu menu = new Menu();
	public static int t = 0;
	public static List<Line2D.Double>borders = new ArrayList<>();

	public static boolean connectLines = true;
	public static void load() {
		
	}
	public CreateMap() {
		this.setLayout(null);
		//setSize(menu.frame.getSize());
		//menu.frame.setContentPane(this);
		time.start();
		this.grabFocus();
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				super.mouseDragged(e);
				t++;
				if(SwingUtilities.isRightMouseButton(e)) {
					for (Rectangle block : new ArrayList<Rectangle>(road)) {
						if (Math.abs(block.x - e.getX()) <= 20
								&& Math.abs(block.y - e.getY()) <= 20) {
							System.out.println("Removed " + block);
							road.remove(block);
						}
					}
				
				}else {
					if(t%7==0) 
						{road.add(new Rectangle(e.getX(),e.getY(),TILE_WIDTH, TILE_HEIGHT));}
				}
				
			}

			
			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseMoved(e);
				mouseX = e.getX();
				mouseY = e.getY();
//				if(Menu.placeStartBtn.isSelected() && Start.isPlaced() == false) {
//					Start.instance(getMousePosition()).setX(mouseX);
//					Start.instance(getMousePosition()).setY(mouseY);
//				}
			}
		});
//		addMouseListener(new MouseAdapter() {
//			public void mousePressed(MouseEvent e) {
//				// TODO Auto-generated method stub
//				super.mousePressed(e);
////				if(!Menu.placeStartBtn.isSelected())
////					road.add(new Rectangle(e.getX(), e.getY(), TILE_WIDTH, TILE_HEIGHT));
//				else if(Menu.placeStartBtn.isSelected()) {
////					Start.instance(getMousePosition()).placed();
//					Menu.placeStartBtn.setSelected(false);
//				}
//			}
//		});
		addKeyListener(new MenuInput());
	
		
	}
	public static Point getCurrentMousePosition() {
		return new Point(mouseX, mouseY);
	}
	public static void addpoints(int xAddition, int yAddition) {
		Rectangle tile = new Rectangle(xAddition,yAddition, TILE_WIDTH,TILE_HEIGHT);
		road.add(tile);
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		if(!road.isEmpty())
			showRoad(g);
	}
	/**
	 * @param g
	 */
	public static void drawRoad(Graphics g) {
//		Graphics2D g2 = (Graphics2D) g;
//		float thickness = 2;
//		Stroke oldStroke = g2.getStroke();
//		Start.instance().show(g);
//		g.setColor(Color.BLACK);
//		g2.setStroke(new BasicStroke(thickness));
//		
//			for (Rectangle s : road) {
//				g2.drawOval(s.x, s.y, s.width, s.height);
//			} 
//		
//		g2.setStroke(oldStroke);
//		if(connectLines) 
//			connectLines();
//		for (Rectangle roadPoint: road) {
//			for(int line = 0; line < borders.size(); line++) {
//				if(borders.get(line).intersects(roadPoint)) {
//					double x1 = borders.get(line).getX1();
//					double y1 = borders.get(line).getY1();
//					double x2 = borders.get(line).getX2();
//					double y2 = borders.get(line).getY2();
//					g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
//				}
//			}
//		}
		g.setColor(Color.BLACK);
	}
	public void showRoad(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
	}

	/**
	 * @param g
	 */
	public static void connectLines() {
		//g.drawRect(Menu.frame.getWidth()-250,Menu.frame.getHeight()-50, 150, 150);
		int prevRectX = road.get(road.size()-1).x, prevRectY = road.get(road.size()-1).y;
		for(Rectangle road: road) {
			if(Math.abs(road.x - prevRectX) <= 150 && Math.abs(prevRectY - road.y) <= 150) {
				borders.add(new Line2D.Double(prevRectX, prevRectY, road.x,road.y));
			}
			prevRectX = road.x;
			prevRectY = road.y;
		}


	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		repaint();
	}
	public static void saveFunction() {
		ArrayList<Rectangle> copy = new ArrayList<>(road);
		for(Rectangle s : copy) {
			for(Rectangle r : copy) {
				if(s.intersects(r) && !s.equals(r)) {
					road.remove(s);
				}
			}
		}
//		LoadMap.saveData(road);	
//		LoadMap.saveStartLocation(Start.instance().getX(),Start.instance().getY(),Start.rotation);
	}


}
class MenuInput extends KeyAdapter{
	@Override
	public void keyPressed(KeyEvent e) {
		//TODO auto-generated thing I typed this the computer did nothing lol 
		super.keyPressed(e);
		int key = e.getKeyCode();
		
			switch(key) {
			case(KeyEvent.VK_ESCAPE):
				CreateMap.saveFunction();
				System.exit(0);
			break;
			case(KeyEvent.VK_R):
				CreateMap.road.clear();
				break;
			case(KeyEvent.VK_C):
				CreateMap.connectLines = !CreateMap.connectLines;
				break;
			case(KeyEvent.VK_Q):
//				Start.rotation+=10;
				break;
			case(KeyEvent.VK_E):
//				Start.rotation-=10;
			}
	}
}
