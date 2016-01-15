package main.pkg;

public class Player {
	
	private String name;
	private GamePanel gamepanel;
	
	public Player(String name, GamePanel gamepanel){
		this.name = name;
		this.gamepanel = gamepanel; 
	}
	
	public GamePanel getGamePanel(){
		return gamepanel;
	}
	
	public void generateBoard(int r, int min){
		gamepanel.shipPlacer(r, min);
	}
	
	public String getName(){
		return name;
	}
	
}
