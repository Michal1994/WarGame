package main.pkg;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

public class PopupWindow extends JFrame {
	
	public static final int LONG = 3000;
	public static final int SHOTR = 1000;
	
	private static Main main;
	
	private String text;
	private int time;
	
	private PopupWindow(String text, int time){
		
		super();
		
		this.text = text;
		this.time = time;
		
		setLayout(null);
		getContentPane().setBackground(Color.white);
		//window hasn't top bar, and can't be focused
		setUndecorated(true);
		setFocusable(false);
		setFocusableWindowState(false);
		
	}
	
	public void makePopup(){
		
		setVisible(true);
		
		JLabel label = new JLabel(text);
		label.setFont( new Font("Consolas", Font.BOLD, 12) );
		label.setBackground(Color.red);
		add(label);
		int width = label.getGraphics().getFontMetrics().stringWidth(text);
		
		//make window transparent
		//setBackground(new Color(0,0,0,0));
		
		setBounds(main.getX() + (main.getWidth()/2 - width/2 - 10), main.getY() + (main.getHeight()/2 - 30), width + 20, 60);
		
		label.setBounds(10, 10, width + 20, 40);
		
		
		Timer t = new Timer(time, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				
			}
		});
		
		t.start();
		
	}
	
	public static void addMain(Main m){
		
		main = m;
		
	}
	
	public static PopupWindow makePopupWindow(String text, int time){
		
		return new PopupWindow(text, time);
		
	}
	
}
