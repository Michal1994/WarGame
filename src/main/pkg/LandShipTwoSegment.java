package main.pkg;

import java.awt.Graphics;
import java.awt.Point;

public class LandShipTwoSegment extends Ship {
	
	public LandShipTwoSegment(){
		super();
		point.add(new Point(0, 0));
		point.add(new Point(1, 0));
	}
	
	public boolean isOnCorrectBackground(byte[][] bg){
		for(Point p: point){
			if(bg[p.x+origin.x][p.y+origin.y] != GamePanel.GRASS) return false;
		}
		return true;
	}
	
	public boolean isBackgroundFitting(int x, int y, byte bg) {
		for(Point p: point){
			if(p.x+origin.x == x && p.y+origin.y == y && bg != GamePanel.GRASS) return false;
		}
		return true;
	}
	
	@Override
	public void draw(Graphics g, int offsetx, int offsety){
		//drawing ship using his drawing coordinates and correct image
		Point p = getDrawingCoogdinates();
		switch(degree){
			case 90:{
				g.drawImage(GraphicLoader.getInstance().getImage(GraphicLoader.LANDSHIPTWOSEGMENTIMAGE90), (p.x+1)*CLUSTERSIZE + offsetx, (p.y-1)*CLUSTERSIZE + offsety, null);
				break;
			}
			case 180:{
				g.drawImage(GraphicLoader.getInstance().getImage(GraphicLoader.LANDSHIPTWOSEGMENTIMAGE180), p.x*CLUSTERSIZE + offsetx, p.y*CLUSTERSIZE + offsety, null);
				break;
			}
			case 270:{
				g.drawImage(GraphicLoader.getInstance().getImage(GraphicLoader.LANDSHIPTWOSEGMENTIMAGE270), p.x*CLUSTERSIZE + offsetx, p.y*CLUSTERSIZE + offsety, null);
				break;
			}
			default: g.drawImage(GraphicLoader.getInstance().getImage(GraphicLoader.LANDSHIPTWOSEGMENTIMAGE), p.x*CLUSTERSIZE + offsetx, p.y*CLUSTERSIZE + offsety, null);
		}
	}
	
}
