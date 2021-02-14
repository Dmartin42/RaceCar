package Infrastructure;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

import MapEditor.CreateMap;
import MapEditor.Map;
import MapEditor.Start;

public class RaceTrack extends JPanel implements ActionListener {
	public final static String NAME = "Race Track";
	public Timer time = new Timer(100, this);


	public static double theta = 90;
	public static Car player1;
	public static Car player2;
	public Input keyboard;
	private final Map map= new Map();
	public static Timers timer = new Timers();
	private final List<Line2D>walls;
	

	private static boolean game = false;
	public RaceTrack() {
		super.repaint();
		setSize(Runner.frame.getSize());
		map.load();
		walls = map.getRoads();
		setName(NAME);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		time.start();
		//player1 = new Car(map.START.getX()-10,map.START.getY());
		player1 = new Car(700,250);
		player1.addRays();
		player2 = new Car((int) Map.START.getX()+player1.getWidth(),Map.START.getY());
		player2.setRotation(Map.START.getRotation()-360);
		player1.setRotation(Map.START.getRotation()-360);
		player2.addRays();
		player2.setPlayerNumber(2);
		player1.setPlayerNumber(1);
		keyboard = new Input();
		this.addKeyListener(keyboard);
		JButton countdwn = new JButton("START");
		countdwn.setBounds(100, 100, 100, 100);
		this.add(countdwn);
		countdwn.setFocusable(false);
		countdwn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				countdwn.setVisible(false);
				synchronized (time) {
					timer.countDown(5);
				}
			}
		});
		this.requestFocusInWindow();
		
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		map.show((Graphics2D) g); //Displays background map
		
		g.setColor(Color.GRAY);
		g.setFont(new Font("Times New Roman", Font.BOLD, 100));
		g.drawString(timer.getMinutes()+":"+timer.getSeconds(), this.getWidth() / 24, 125);
		
		g.setColor(Color.BLACK);
		player1.show((Graphics2D) g);
		try {
			player1.look(walls, g);
		} catch (NullPointerException e) {
			
		}
		player2.show((Graphics2D) g);
		try {
			player2.look(walls, g);
		}catch	(NullPointerException e) {
			
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (theta >= 360 || theta <= 0) //Ensures degrees in interval [0,360]
			theta = 0;
		this.setSize(Runner.frame.getSize());
		if(timer.isFinished()) {
			game = true;
			timer.countUp();
			timer.setFinished(false);
		}
		
		if (game) {
			keyboard.keyActions();
			player1.move();
			player2.move();
		}
		
		
		repaint();
	}
	public static void finishedTimer() {
		game = true;
	}
	public static boolean finished() {
		return !game;
	}

}
