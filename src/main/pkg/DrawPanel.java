package main.pkg;

import java.awt.Graphics;

import javax.swing.JPanel;

public class DrawPanel extends JPanel {
	
	/*
	 * This is JPanel class for Game graphic panel 
	 * It is needed because of paintComponent method should by overwrite
	 * This component can use double buffering in paintComponent method
	 * Draw methods of players game panels should be invoke from external object like this panel
	 */
	
	private Player player;
	private int offsetx;
	private int offsety;
	
	public DrawPanel(Player player, int offsetx, int offsety){
		this.player = player;
		this.offsetx = offsetx;
		this.offsety = offsety;
	}
	
	public void paintComponent(Graphics g){
		g.setColor(getBackground());
		player.getGamePanel().draw(g, offsetx, offsety, getWidth(), getHeight());
	}
	
}
