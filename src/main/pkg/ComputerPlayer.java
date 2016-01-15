package main.pkg;

public class ComputerPlayer extends Player {
	
	public ComputerPlayer(GamePanel gamepanel){
		super("Computer", gamepanel);
	}
	
	public void makeShoot(Main main, Player player1){
		
		main.secoundPlayerShoot();
		
	}
	
}
