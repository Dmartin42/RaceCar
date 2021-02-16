package MapEditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToggleButton;

public class Menu {
	

	public static Map map = new Map();
	private static final JToggleButton strtSel = new JToggleButton("Place Start Line");
	
	public final static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	public static  JFrame frame = new JFrame();
	
	public static void main(String[]args) {
		frame.setSize(dim);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		
		JButton strtBtn = new JButton("Begin!");
			strtBtn.setBounds(dim.width/2-frame.getWidth()/2,dim.height/2-frame.getHeight()/2,250,250 );
			strtBtn.setBounds(50, 50, 500, 61);
			
			strtBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					strtBtn.setVisible(false);
					strtSel.setVisible(true);
					map.load();
					frame.setFocusable(false);
					map.setFocusable(true);
					map.addContols();
					frame.setContentPane(map);
					map.grabFocus();
				
				}
			});
		strtSel.setBackground(Color.black);
		strtSel.setBounds(30, 65, 150, 50);
		strtSel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				map.setStrtBtnSelected(true);
				strtSel.setFocusable(false);
			}
		});
		map.add(strtSel);
		frame.add(strtBtn);
		frame.setVisible(true);
		frame.setFocusable(false);
		strtBtn.setFocusable(false);
	}

	
	
 
	}


