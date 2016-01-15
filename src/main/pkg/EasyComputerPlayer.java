package main.pkg;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EasyComputerPlayer extends ComputerPlayer {
	
	private Random r = new Random();
	private List<Point> availableshoot;
	
	public EasyComputerPlayer(GamePanel gamepanel) {	
		super(gamepanel);
		
		availableshoot = new ArrayList<Point>();
		
		for(int i = 0, size = gamepanel.getWidth()*gamepanel.getHeight(); i < size; i ++)
			availableshoot.add( new Point(i%gamepanel.getWidth(), i/gamepanel.getWidth()) );
		
	}
	
	public void makeShoot(Main main, Player player1) {
		
		//get random point
		Point p = availableshoot.get( r.nextInt( availableshoot.size() ) );
		
		while( !player1.getGamePanel().mouseClickLeft(p.x*Ship.CLUSTERSIZE, p.y*Ship.CLUSTERSIZE, 0, 0) )
			p = availableshoot.get( r.nextInt( availableshoot.size() ) );
		
		//remove used point to avoid shoot in the same place
		availableshoot.remove(p);
		
		super.makeShoot(main, player1);
		
	}

}
