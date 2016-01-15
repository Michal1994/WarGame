package main.pkg;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main extends JFrame implements WindowListener, ActionListener, MouseListener, MouseMotionListener {
	
	private Player player1, player2;
	private JPanel panel1, panel2;
	private JSpinner spinnerrange, spinnerminimum;
	
	private boolean playerturn;
	
	public Main(){
		
		super("War Game");
		
		//adding listener for implement closing action
		addWindowListener(this);
		
		//window can not be resized and have constant size
		setBounds(100, 100, 800, 880);
		setResizable(false);
		
		//no layout needed, components will be placed in constant coordinates
		setLayout(null);
		
		//setting look and feel
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch (ClassNotFoundException e) {}
		catch (InstantiationException e) {}
		catch (IllegalAccessException e) {}
		catch (UnsupportedLookAndFeelException e) {}
		SwingUtilities.updateComponentTreeUI(this.rootPane);
		
		//declaring buttons
		
		JButton button = new JButton("New Game");
		button.setBounds(10, 10, 100, 40);
		button.addActionListener(this);
		button.setActionCommand("newgame");
		add(button);
		
		button = new JButton("Start");
		button.setBounds(120, 10, 100, 40);
		button.addActionListener(this);
		button.setActionCommand("start");
		add(button);
		
		button = new JButton("Generate map");
		button.setBounds(230, 10, 150, 40);
		button.addActionListener(this);
		button.setActionCommand("mapgen");
		add(button);
		
		button = new JButton("Save");
		button.setBounds(510, 10, 100, 40);
		button.addActionListener(this);
		button.setActionCommand("mapsave");
		add(button);
		
		button = new JButton("Load");
		button.setBounds(620, 10, 100, 40);
		button.addActionListener(this);
		button.setActionCommand("mapload");
		add(button);
		
		//declare spinners for map generating
		spinnerrange = new JSpinner();
		Integer[] range = {3,4,5,6,7,8};
		spinnerrange.setModel(new SpinnerListModel(range));
		spinnerrange.setBounds(390, 10, 50, 40);
		add(spinnerrange);
		
		spinnerminimum = new JSpinner();
		spinnerminimum.setModel(new SpinnerListModel(range));
		spinnerminimum.setBounds(450, 10, 50, 40);
		add(spinnerminimum);
		
		//declare spinners labels
		JLabel spinnerlabel = new JLabel("Range");
		spinnerlabel.setFont( new Font("Arial", Font.PLAIN, 9) );
		spinnerlabel.setBounds(390, 0, 50, 10);
		add(spinnerlabel);
		
		spinnerlabel = new JLabel("Minimum");
		spinnerlabel.setFont( new Font("Arial", Font.PLAIN, 9) );
		spinnerlabel.setBounds(450, 0, 50, 10);
		add(spinnerlabel);
		
		//initialize drawing panel
		
		//load images to RAM
		GraphicLoader.getInstance().load();
		
		setVisible(true);
		
		PopupWindow.addMain(this);
		
	}
	
	//method for external Objects to set players
	public void setPlayers(Player player1, Player player2){
		
		if(panel1 != null && panel2 != null){
			remove(panel1);
			remove(panel2);
		}
		
		playerturn = true;
		
		this.player1 = player1;
		this.player2 = player2;
		
		panel1 = new DrawPanel(player1, 20, 20);
		panel1.setBounds(10, 60, 22*Ship.CLUSTERSIZE+220, 14*Ship.CLUSTERSIZE+40); //25 size of cluster
		//panel1.setBorder(BorderFactory.createLineBorder(Color.black, 5));
		panel1.addMouseListener(this);
		panel1.addMouseMotionListener(this);
		add(panel1);
		
		panel2 = new DrawPanel(player2, 20, 20);
		panel2.setBounds(10, 14*Ship.CLUSTERSIZE+120, 22*Ship.CLUSTERSIZE+220, 14*Ship.CLUSTERSIZE+40); //25 size of cluster
		panel2.addMouseListener(this);
		add(panel2);
		
	}
	
	public void gameEnd(){
		
		if(player1.getGamePanel().isGameEnd()){
			//first player loose
			//you win
			SoundPlayer.getInstance().play( SoundPlayer.LOSE );
			PopupWindow.makePopupWindow("You Lose", PopupWindow.LONG).makePopup();
		}
		else{
			//second player loose
			//you loose
			SoundPlayer.getInstance().play( SoundPlayer.WIN );
			PopupWindow.makePopupWindow("You Win", PopupWindow.LONG).makePopup();
		}
		
		panel1.repaint();
		panel2.repaint();
		
		remove(panel1);
		remove(panel2);
		
		panel1 = panel2 = null;
		player1 = player2 = null;
		
	}
	
	public void secoundPlayerShoot(){
		playerturn = true;
		panel1.repaint();
	}
	
	public void redraw(){
		
		if(player1 != null && player2 != null){
			panel1.repaint();
			panel2.repaint();
		}
		
	}
	
	public static void main(String[] args){
		
		//run main game window
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				
				new Main();
				
			}
		});
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getActionCommand().equals("newgame")){
			new NewGameWindow(this, 100, 100, 210, 150);
		}
		else if(e.getActionCommand().equals("start") && player1 != null && player2 != null){
			if(player1.getGamePanel().canLeaveEditMode()){
				player1.getGamePanel().leaveEditMode();
				if(player2 instanceof ComputerPlayer){
					player2.generateBoard(5, 5);
					panel2.repaint();
					if(player2 instanceof HardComputerPlayer){
						((HardComputerPlayer) player2).initialize(player1);
					}
				}
			}
		}
		else if(e.getActionCommand().equals("mapgen") && player1 != null && player2 != null){
			if(player1.getGamePanel().isEditable()){
				player1.generateBoard((Integer)spinnerrange.getValue(), (Integer)spinnerminimum.getValue());
				panel1.repaint();
			}
		}
		else if(e.getActionCommand().equals("mapsave") && player1 != null && player2 != null){
			if(player1.getGamePanel().isMapSaveable()){
				
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Save map file");
				fc.setFileFilter(new FileNameExtensionFilter("Map file (.wmap)", "wmap"));
				
				int status = fc.showSaveDialog(null);
				
				if(status == JFileChooser.APPROVE_OPTION){
					
					File f = fc.getSelectedFile();
					if( !f.getPath().endsWith(".wmap") ) f = new File( f.getPath() + ".wmap" );
					
					player1.getGamePanel().saveToFile( f );
					
				}
				
			}
			else
				PopupWindow.makePopupWindow("Add all ships first", PopupWindow.SHOTR).makePopup();
		}
		else if(e.getActionCommand().equals("mapload") && player1 != null && player2 != null){
			
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open map file");
			fc.setFileFilter(new FileNameExtensionFilter("Map file", "wmap"));
			
			int status = fc.showOpenDialog(null);
			
			if(status == JFileChooser.APPROVE_OPTION){
				
				player1.getGamePanel().loadFromFile( fc.getSelectedFile() );
				
				repaint();
				
			}
			
		}
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		//exit without error
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource().equals(panel1)){
			if(player1 != null){
				if(!player1.getGamePanel().isEditable()) return;
				if(SwingUtilities.isLeftMouseButton(arg0)) player1.getGamePanel().mouseClickLeft(arg0.getX(), arg0.getY(), 20, 20);
				else if(SwingUtilities.isRightMouseButton(arg0) && player1.getGamePanel().isElementDragged()) player1.getGamePanel().mouseClickRight(arg0.getX(), arg0.getY(), 20, 20);
				panel1.repaint();
			}
		}
		else if(arg0.getSource().equals(panel2)){
			//if player1 is not in edit mode and player2 is an opponent and it is player1 turn
			if(!player1.getGamePanel().isEditable() && playerturn && !player1.getGamePanel().isOpponent() && player2.getGamePanel().isOpponent()){
				if(SwingUtilities.isLeftMouseButton(arg0)){
					//if shoot is correct - opponent tour
					playerturn = !player2.getGamePanel().mouseClickLeft(arg0.getX(), arg0.getY(), 20, 20);
					if(player2.getGamePanel().isGameEnd()){
						gameEnd();
						return;
					}
					//player2 response
					if(player2 instanceof ComputerPlayer && !playerturn) ( (ComputerPlayer) player2 ).makeShoot(this, player1);
					if(player1.getGamePanel().isGameEnd()){
						gameEnd();
						return;
					}
				}
				//add flag
				else if(SwingUtilities.isRightMouseButton(arg0) && !player1.getGamePanel().isEditable()) player2.getGamePanel().mouseClickRight(arg0.getX(), arg0.getY(), 20, 20);
				panel2.repaint();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		if(player1 != null){
			if(player1.getGamePanel().isElementDragged()){
				player1.getGamePanel().mouseMove(e.getX(), e.getY());
				panel1.repaint();
			}
		}
	}
	
}
