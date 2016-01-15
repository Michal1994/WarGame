package main.pkg;

import java.awt.Graphics;
import java.awt.Point;

public class SeaShipOneSegment extends Ship {
	
	public SeaShipOneSegment(){
		super();
		point.add(new Point(0, 0));
	}
	
	public boolean isOnCorrectBackground(byte[][] bg){
		for(Point p: point){
			if(bg[p.x+origin.x][p.y+origin.y] != GamePanel.WATER) return false;
		}
		return true;
	}
	
	public boolean isBackgroundFitting(int x, int y, byte bg) {
		for(Point p: point){
			if(p.x+origin.x == x && p.y+origin.y == y && bg != GamePanel.WATER) return false;
		}
		return true;
	}
	
	@Override
	public void draw(Graphics g, int offsetx, int offsety){
		//drawing ship using his drawing coordinates and correct image
		Point p = getDrawingCoogdinates();
		switch(degree){
			case 90:{
				g.drawImage(GraphicLoader.getInstance().getImage(GraphicLoader.SEASHIPONESEGMENTIMAGE90), p.x*CLUSTERSIZE + offsetx, p.y*CLUSTERSIZE + offsety, null);
				break;
			}
			case 180:{
				g.drawImage(GraphicLoader.getInstance().getImage(GraphicLoader.SEASHIPONESEGMENTIMAGE180), p.x*CLUSTERSIZE + offsetx, p.y*CLUSTERSIZE + offsety, null);
				break;
			}
			case 270:{
				g.drawImage(GraphicLoader.getInstance().getImage(GraphicLoader.SEASHIPONESEGMENTIMAGE270), p.x*CLUSTERSIZE + offsetx, p.y*CLUSTERSIZE + offsety, null);
				break;
			}
			default: g.drawImage(GraphicLoader.getInstance().getImage(GraphicLoader.SEASHIPONESEGMENTIMAGE), p.x*CLUSTERSIZE + offsetx, p.y*CLUSTERSIZE + offsety, null);
		}
	}
	
}
