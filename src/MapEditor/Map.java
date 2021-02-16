package MapEditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Map extends JPanel implements ActionListener {
	private static List<Point> joints;
	private static List<Line2D> roads;
	public static final Start START = new Start();
	public Timer refresher = new Timer(100, this);
	Point pointStart = null;
	Point pointEnd = null;
	/**
	 * Used for deleting lines
	 * 
	 */
	private Rectangle mouse;
	private boolean strtBtnSelected;
	private boolean showCourse = true;
	private int mouseX, mouseY;

	public Map() {
		setLayout(null);
		System.out.println("new map object created: " + Calendar.getInstance().getTime());
	}

	/**
	 * Add keyboard and mouse controls to the map.
	 * 
	 * @author Daniel Martin
	 */
	public void addContols() {
		refresher.start();
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				switch (key) {
				case (KeyEvent.VK_ESCAPE):
					save();
					System.exit(0);
					break;
				case (KeyEvent.VK_R): // Clears map
					int warn = JOptionPane.showConfirmDialog(null, "Are you sure you want to reset map?", "Map reset",
							JOptionPane.WARNING_MESSAGE);
					if (warn == JOptionPane.YES_OPTION) {
						joints.clear();
						roads.clear();
					}
					break;
				case (KeyEvent.VK_Q): // Rotate start object to the left
					START.setRotation(START.getRotation() - 10);
					break;
				case (KeyEvent.VK_E): // Rotate start line object to the right
					START.setRotation(START.getRotation() + 10);
				}
			}
		});

		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				if (SwingUtilities.isLeftMouseButton(e)) {
					if (strtBtnSelected) {
						START.setPlaced(true);
						START.setLocation(e.getPoint());
						return;
					}
					leftClick = true;
					if (beginDraw) {
						pointStart = null;
						pointEnd = null;
						pointStart = e.getPoint();
						beginDraw = false;
					}
				} else if (SwingUtilities.isRightMouseButton(e)) {
					rightClick = true;
					mouse = new Rectangle(e.getX(), e.getY(), 50, 50);
				}
			}

			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				if (leftClick) {
					pointEnd = e.getPoint();
					roads.add(new Line2D.Double(pointStart, pointEnd));
					beginDraw = true;

				}
				if (rightClick) {
					mouse = null;
				}
				leftClick = false;
				rightClick = false;
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}

			public void mouseDragged(MouseEvent e) {
				super.mouseMoved(e);
				if (strtBtnSelected)
					return;
				if (SwingUtilities.isLeftMouseButton(e)) {
					pointEnd = e.getPoint();
				}
				if (SwingUtilities.isRightMouseButton(e)) {

					if (mouse != null) {
						mouse.setBounds(e.getX() - 25, e.getY() - 25, 50, 50);

						for (Line2D block : new ArrayList<Line2D>(roads)) {
							if (mouse.intersectsLine(block)) {
								System.out.println("Removed " + block);
								roads.remove(block);
								pointStart = null;
							}
						}
					}
				}
			}
		});
		this.setFocusable(true);
		this.setFocusTraversalKeysEnabled(false);
		this.requestFocusInWindow();
		this.requestFocus();
		repaint();
		System.out.println(this.hasFocus());
	}

	boolean beginDraw = true, leftClick, rightClick;

	public void load() {

		try {
			joints = LoadMap.setPoints(LoadMap.getFirstLine());
			roads = LoadMap.setLines(joints);
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			joints = new ArrayList<>();
			roads = new ArrayList<>();
			System.err.print("Criticial: first line of file was empty.\nCreated new array list");
		}
		START.setLocation(LoadMap.getSavedStartLineLocation()); // Set Start line location
		START.setRotation(LoadMap.getSavedStartLineRotationData()); // set start line rotation
	}

	public static Start startLineData() {
		return START;
	}

	public void save() {
		LoadMap.saveCoordinates(roads);
		LoadMap.saveStartLocation(START.getX(), START.getY(), START.getRotation());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.fillRect(109, 109, 49, 73);
		if (mouse != null) // When R-Mouse is held down, indicating intentional deletion
			g2d.draw(mouse);

		if (pointStart != null && pointEnd != null) {
			g.setColor(Color.RED);
			g.drawLine(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y);

		}
		g2d.setColor(Color.black);

		if (strtBtnSelected)
			START.show(g2d, mouseX, mouseY);
		else
			START.show(g2d, START.getX(), START.getY());

		g2d.setColor(Color.black);

		for (Line2D l : roads) {
			g2d.draw(l);
		}
	}

	/**
	 * Displays course
	 * 
	 * @param g2d
	 */
	public void show(Graphics2D g2d) {
		if (this.isCourseShown()) {
			g2d.setColor(Color.black);
			for (Line2D line : roads) {
				g2d.draw(line);
			}
		}
		START.show(g2d);
	}

	public boolean isStrtBtnSelected() {
		return strtBtnSelected;
	}

	public void setStrtBtnSelected(boolean strtBtnSelected) {
		this.strtBtnSelected = strtBtnSelected;
	}

	public boolean isCourseShown() {
		return showCourse;
	}

	public void setShowCourse(boolean showCourse) {
		this.showCourse = showCourse;
	}

	public List<Line2D> getRoads() {
		return roads;
	}

}
