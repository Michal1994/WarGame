package main.pkg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRadioButton;

public class NewGameWindow extends JFrame implements ActionListener {
	
	private Main main;
	private JRadioButton[] radiobutton;
	private ButtonGroup bg;
	
	public NewGameWindow(Main main, int x, int y, int width, int height){
		
		super("New Game");
		
		this.main = main;
		
		//set window position
		setBounds(x, y, width, height);
		
		setLayout(null);
		
		bg = new ButtonGroup();
		
		radiobutton = new JRadioButton[2];
		
		//declare button for easy computer player
		radiobutton[0] = new JRadioButton("Easy Computer Player");
		radiobutton[0].setMnemonic(KeyEvent.VK_E);
		radiobutton[0].setBounds(10, 10, 200, 20);
		radiobutton[0].setSelected(true);
		bg.add(radiobutton[0]);
		
		//declare button for hard computer player
		radiobutton[1] = new JRadioButton("Hard Computer Player");
		radiobutton[1].setMnemonic(KeyEvent.VK_H);
		radiobutton[1].setBounds(10, 40, 200, 20);
		bg.add(radiobutton[1]);
		
		add(radiobutton[0]);
		add(radiobutton[1]);
		
		JButton button = new JButton("OK");
		button.setBounds(10, 80, 80, 20);
		button.addActionListener(this);
		button.setActionCommand("ok");
		add(button);
		
		button = new JButton("Cancel");
		button.setBounds(100, 80, 80, 20);
		button.addActionListener(this);
		button.setActionCommand("cancel");
		add(button);
		
		setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getActionCommand().equals("ok")){
			
			Player player1, player2;
			
			player1 = new Player("Player1", new GamePanel(Ship.BOARDWIDTH, Ship.BOARDHEIGHT, Ship.CLUSTERSIZE, false));
			player2 = null;
			
			if(radiobutton[0].isSelected()){
				player2 = new EasyComputerPlayer(new GamePanel(Ship.BOARDWIDTH, Ship.BOARDHEIGHT, Ship.CLUSTERSIZE, true));
			}
			else if(radiobutton[1].isSelected()){
				player2 = new HardComputerPlayer(new GamePanel(Ship.BOARDWIDTH, Ship.BOARDHEIGHT, Ship.CLUSTERSIZE, true), player1);
			}
			
			main.setPlayers(player1, player2);
			main.redraw();
			
			dispose();
			
		}
		else if(e.getActionCommand().equals("cancel")){
			dispose();
		}
		
	}
	
}
