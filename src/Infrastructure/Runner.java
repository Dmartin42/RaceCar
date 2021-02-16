package Infrastructure;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;


import java.util.Calendar;

import javax.swing.JFrame;


public class Runner{
	public static final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	public static JFrame frame;
	public static final int minStart = Calendar.getInstance().get(Calendar.MINUTE);
	public static final int secStart = Calendar.getInstance().get(Calendar.SECOND);
	public static final int hourStart = Calendar.getInstance().get(Calendar.HOUR);
	public static  RaceTrack rt;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Program started: " + Calendar.getInstance().getTime());
		frame = new JFrame("RaceWay One");
		frame.setSize(dim);
		
		frame.setUndecorated(false);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		rt = new RaceTrack(); //New RaceTrack Object
		frame.add(rt);
		frame.setVisible(true);
		
		

		
	}

}
