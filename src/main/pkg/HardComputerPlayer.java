package main.pkg;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HardComputerPlayer extends ComputerPlayer {
	
	private Random r = new Random();
	
	//point lists for shoots
	private List<Point> waterpoints;
	private List<Point> landpoints;
	private List<Point> excludedpoints;
	
	//already used
	private List<Point> waterpointsused;
	private List<Point> landpointsused;
	
	//last ship count
	private int lastshipcount;
	
	//additional variables
	private Point lastshoot;
	private List<Point> currenttarget;
	
	byte[][] bg;
	
	public HardComputerPlayer(GamePanel gamepanel, Player player1) {
		super(gamepanel);
		
		waterpoints = new ArrayList<Point>();
		
		landpoints = new ArrayList<Point>();
		
		excludedpoints = new ArrayList<Point>();
		
		waterpointsused = new ArrayList<Point>();
		
		landpointsused = new ArrayList<Point>();
		
	}
	
	public void initialize(Player player1){
		
		GamePanel oppanel = player1.getGamePanel();
				
		
		bg = oppanel.getBackgroundView();
		
		int width = oppanel.getWidth();
		int height = oppanel.getHeight();
		
		for(int i = 0, size = width*height; i < size; i++){
			switch( bg[i/height][i%height] ){
				case GamePanel.WATER:{
					waterpoints.add( new Point(i/height, i%height) );
					break;
				}
				case GamePanel.GRASS:{
					landpoints.add( new Point(i/height, i%height) );
					break;
				}
			}
		}
		
		//looking for points on land that should be excluded
		for(int i = 0; i < landpoints.size(); i++){
			if(isExcludable( landpoints.get(i) )){
				excludedpoints.add( landpoints.remove( i ) );
				i--;
			}
		}
		
		lastshoot = null;
		currenttarget = new ArrayList<Point>();
	}
	
	private boolean isExcludable(Point p){
		
		if(landpoints.contains(p)){
			
			//point on top
			for(int i = 0, size = landpoints.size(); i < size; i++)
				if(p.x == landpoints.get(i).x && p.y == landpoints.get(i).y - 1) return false;
			
			//point on bottom
			for(int i = 0, size = landpoints.size(); i < size; i++)
				if(p.x == landpoints.get(i).x && p.y == landpoints.get(i).y + 1) return false;
			
			//point on right
			for(int i = 0, size = landpoints.size(); i < size; i++)
				if(p.x == landpoints.get(i).x - 1 && p.y == landpoints.get(i).y) return false;
			
			//point on left
			for(int i = 0, size = landpoints.size(); i < size; i++)
				if(p.x == landpoints.get(i).x + 1 && p.y == landpoints.get(i).y) return false;
			
			return true;
			
		}
		else if(waterpoints.contains(p)){
			
			//point on top
			for(int i = 0, size = waterpoints.size(); i < size; i++)
				if(p.x == waterpoints.get(i).x && p.y == waterpoints.get(i).y - 1) return false;
			
			//point on bottom
			for(int i = 0, size = waterpoints.size(); i < size; i++)
				if(p.x == waterpoints.get(i).x && p.y == waterpoints.get(i).y + 1) return false;
			
			//point on right
			for(int i = 0, size = waterpoints.size(); i < size; i++)
				if(p.x == waterpoints.get(i).x - 1 && p.y == waterpoints.get(i).y) return false;
			
			//point on left
			for(int i = 0, size = waterpoints.size(); i < size; i++)
				if(p.x == waterpoints.get(i).x + 1 && p.y == waterpoints.get(i).y) return false;
			
			return true;
			
		}
		else return false;
		
	}
	
	private List<Point> getFourNeighbors(Point p){
		
		//diagonal
		
		List<Point> l = new ArrayList<Point>();
		
		if(bg[p.x][p.y] == GamePanel.GRASS){
			
			for(int i = 0, size = landpoints.size(); i < size; i++)
				if(p.x == landpoints.get(i).x - 1 && p.y == landpoints.get(i).y - 1){
					l.add( landpoints.get(i) );
					break;
				}
			
			for(int i = 0, size = landpoints.size(); i < size; i++)
				if(p.x == landpoints.get(i).x + 1 && p.y == landpoints.get(i).y + 1){
					l.add( landpoints.get(i) );
					break;
				}
			
			for(int i = 0, size = landpoints.size(); i < size; i++)
				if(p.x == landpoints.get(i).x - 1 && p.y == landpoints.get(i).y + 1){
					l.add( landpoints.get(i) );
					break;
				}
			
			for(int i = 0, size = landpoints.size(); i < size; i++)
				if(p.x == landpoints.get(i).x + 1 && p.y == landpoints.get(i).y - 1){
					l.add( landpoints.get(i) );
					break;
				}
			
		}
		else if(bg[p.x][p.y] == GamePanel.WATER){
			
			for(int i = 0, size = waterpoints.size(); i < size; i++)
				if(p.x == waterpoints.get(i).x - 1 && p.y == waterpoints.get(i).y - 1){
					l.add( waterpoints.get(i) );
					break;
				}
			
			for(int i = 0, size = waterpoints.size(); i < size; i++)
				if(p.x == waterpoints.get(i).x + 1 && p.y == waterpoints.get(i).y + 1){
					l.add( waterpoints.get(i) );
					break;
				}
			
			for(int i = 0, size = waterpoints.size(); i < size; i++)
				if(p.x == waterpoints.get(i).x - 1 && p.y == waterpoints.get(i).y + 1){
					l.add( waterpoints.get(i) );
					break;
				}
			
			for(int i = 0, size = waterpoints.size(); i < size; i++)
				if(p.x == waterpoints.get(i).x + 1 && p.y == waterpoints.get(i).y - 1){
					l.add( waterpoints.get(i) );
					break;
				}
			
		}
		
		return l;
		
	}
	
	private int getAllShipsCount(Player player1){
		
		int[] count = player1.getGamePanel().getAliveShipCount();
		
		int ret = 0;
		
		for(int i:count)
			ret += i;
		
		return ret;
	}
	
	private List<Point> getAvailableShoots(){
		
		List<Point> l = new ArrayList<Point>();
		
		if(currenttarget.size() == 1){
			
			Point p = currenttarget.get(0);
			
			//search on land
			if( bg[p.x][p.y] == GamePanel.GRASS){
				
				//point on top
				for(int i = 0, size = landpoints.size(); i < size; i++)
					if(p.x == landpoints.get(i).x && p.y == landpoints.get(i).y - 1){
						l.add(landpoints.get(i));
						break;
					}
				
				//point on bottom
				for(int i = 0, size = landpoints.size(); i < size; i++)
					if(p.x == landpoints.get(i).x && p.y == landpoints.get(i).y + 1){
						l.add(landpoints.get(i));
						break;
					}
				
				//point on right
				for(int i = 0, size = landpoints.size(); i < size; i++)
					if(p.x == landpoints.get(i).x - 1 && p.y == landpoints.get(i).y){
						l.add(landpoints.get(i));
						break;
					}
				
				//point on left
				for(int i = 0, size = landpoints.size(); i < size; i++)
					if(p.x == landpoints.get(i).x + 1 && p.y == landpoints.get(i).y){
						l.add(landpoints.get(i));
						break;
					}
				
			}
			//search on water
			else{
				
				//point on top
				for(int i = 0, size = waterpoints.size(); i < size; i++)
					if(p.x == waterpoints.get(i).x && p.y == waterpoints.get(i).y - 1){
						l.add(waterpoints.get(i));
						break;
					}
				
				//point on bottom
				for(int i = 0, size = waterpoints.size(); i < size; i++)
					if(p.x == waterpoints.get(i).x && p.y == waterpoints.get(i).y + 1){
						l.add(waterpoints.get(i));
						break;
					}
				
				//point on right
				for(int i = 0, size = waterpoints.size(); i < size; i++)
					if(p.x == waterpoints.get(i).x - 1 && p.y == waterpoints.get(i).y){
						l.add(waterpoints.get(i));
						break;
					}
				
				//point on left
				for(int i = 0, size = waterpoints.size(); i < size; i++)
					if(p.x == waterpoints.get(i).x + 1 && p.y == waterpoints.get(i).y){
						l.add(waterpoints.get(i));
						break;
					}
				
			}
			
			if(l.size() == 0){
				
				//search in all
				
				//land
				
				//point on top
				for(int i = 0, size = landpoints.size(); i < size; i++)
					if(p.x == landpoints.get(i).x && p.y == landpoints.get(i).y - 1){
						l.add(landpoints.get(i));
						break;
					}
				
				//point on bottom
				for(int i = 0, size = landpoints.size(); i < size; i++)
					if(p.x == landpoints.get(i).x && p.y == landpoints.get(i).y + 1){
						l.add(landpoints.get(i));
						break;
					}
				
				//point on right
				for(int i = 0, size = landpoints.size(); i < size; i++)
					if(p.x == landpoints.get(i).x - 1 && p.y == landpoints.get(i).y){
						l.add(landpoints.get(i));
						break;
					}
				
				//point on left
				for(int i = 0, size = landpoints.size(); i < size; i++)
					if(p.x == landpoints.get(i).x + 1 && p.y == landpoints.get(i).y){
						l.add(landpoints.get(i));
						break;
					}
				
				//water
				
				//point on top
				for(int i = 0, size = waterpoints.size(); i < size; i++)
					if(p.x == waterpoints.get(i).x && p.y == waterpoints.get(i).y - 1){
						l.add(waterpoints.get(i));
						break;
					}
				
				//point on bottom
				for(int i = 0, size = waterpoints.size(); i < size; i++)
					if(p.x == waterpoints.get(i).x && p.y == waterpoints.get(i).y + 1){
						l.add(waterpoints.get(i));
						break;
					}
				
				//point on right
				for(int i = 0, size = waterpoints.size(); i < size; i++)
					if(p.x == waterpoints.get(i).x - 1 && p.y == waterpoints.get(i).y){
						l.add(waterpoints.get(i));
						break;
					}
				
				//point on left
				for(int i = 0, size = waterpoints.size(); i < size; i++)
					if(p.x == waterpoints.get(i).x + 1 && p.y == waterpoints.get(i).y){
						l.add(waterpoints.get(i));
						break;
					}
				
				//excluded
				
				//point on top
				for(int i = 0, size = excludedpoints.size(); i < size; i++)
					if(p.x == excludedpoints.get(i).x && p.y == excludedpoints.get(i).y - 1){
						l.add(excludedpoints.get(i));
						break;
					}
				
				//point on bottom
				for(int i = 0, size = excludedpoints.size(); i < size; i++)
					if(p.x == excludedpoints.get(i).x && p.y == excludedpoints.get(i).y + 1){
						l.add(excludedpoints.get(i));
						break;
					}
				
				//point on right
				for(int i = 0, size = excludedpoints.size(); i < size; i++)
					if(p.x == excludedpoints.get(i).x - 1 && p.y == excludedpoints.get(i).y){
						l.add(excludedpoints.get(i));
						break;
					}
				
				//point on left
				for(int i = 0, size = excludedpoints.size(); i < size; i++)
					if(p.x == excludedpoints.get(i).x + 1 && p.y == excludedpoints.get(i).y){
						l.add(excludedpoints.get(i));
						break;
					}
				
			}
			
		}
		
		//more points
		else{
			
			//horizontal
			if( (currenttarget.get(0).y - currenttarget.get(1).y) == 0){
				
				Point pmin = currenttarget.get(0);
				Point pmax = currenttarget.get(0);
				
				for(Point p:currenttarget){
					if(p.x < pmin.x) pmin = p;
					else if(p.x > pmax.x) pmax = p;
				}
				
				//search on land
				if( bg[pmin.x][pmin.y] == GamePanel.GRASS){
					
					//point on left
					for(int i = 0, size = landpoints.size(); i < size; i++)
						if(pmin.x == landpoints.get(i).x + 1 && pmin.y == landpoints.get(i).y){
							l.add(landpoints.get(i));
							break;
						}
					
				}
				//search on water
				else{
					
					//point on left
					for(int i = 0, size = waterpoints.size(); i < size; i++)
						if(pmin.x == waterpoints.get(i).x + 1 && pmin.y == waterpoints.get(i).y){
							l.add(waterpoints.get(i));
							break;
						}
					
				}
				
				//search on land
				if( bg[pmax.x][pmax.y] == GamePanel.GRASS){
					
					//point on right
					for(int i = 0, size = landpoints.size(); i < size; i++)
						if(pmax.x == landpoints.get(i).x - 1 && pmax.y == landpoints.get(i).y){
							l.add(landpoints.get(i));
							break;
						}
					
				}
				//search on water
				else{
					
					//point on right
					for(int i = 0, size = waterpoints.size(); i < size; i++)
						if(pmax.x == waterpoints.get(i).x - 1 && pmax.y == waterpoints.get(i).y){
							l.add(waterpoints.get(i));
							break;
						}
					
				}
				
				if(l.size() == 0){
					
					//search in all
					
					//land
					
					//point on right
					for(int i = 0, size = landpoints.size(); i < size; i++)
						if(pmax.x == landpoints.get(i).x - 1 && pmax.y == landpoints.get(i).y){
							l.add(landpoints.get(i));
							break;
						}
					
					//point on left
					for(int i = 0, size = landpoints.size(); i < size; i++)
						if(pmin.x == landpoints.get(i).x + 1 && pmin.y == landpoints.get(i).y){
							l.add(landpoints.get(i));
							break;
						}
					
					//water
					
					//point on right
					for(int i = 0, size = waterpoints.size(); i < size; i++)
						if(pmax.x == waterpoints.get(i).x - 1 && pmax.y == waterpoints.get(i).y){
							l.add(waterpoints.get(i));
							break;
						}
					
					//point on left
					for(int i = 0, size = waterpoints.size(); i < size; i++)
						if(pmin.x == waterpoints.get(i).x + 1 && pmin.y == waterpoints.get(i).y){
							l.add(waterpoints.get(i));
							break;
						}
					
					//excluded
					
					//point on right
					for(int i = 0, size = excludedpoints.size(); i < size; i++)
						if(pmax.x == excludedpoints.get(i).x - 1 && pmax.y == excludedpoints.get(i).y){
							l.add(excludedpoints.get(i));
							break;
						}
					
					//point on left
					for(int i = 0, size = excludedpoints.size(); i < size; i++)
						if(pmin.x == excludedpoints.get(i).x + 1 && pmin.y == excludedpoints.get(i).y){
							l.add(excludedpoints.get(i));
							break;
						}
					
				}
				
				//air ship
				if(l.size() == 0 && currenttarget.size() == 3){
				
					Point p = new Point( pmin.x + 1, pmin.y );
					
					//search in all
					
					//land
					
					//point on bottom
					for(int i = 0, size = landpoints.size(); i < size; i++)
						if(p.x == landpoints.get(i).x && p.y == landpoints.get(i).y - 1){
							l.add(landpoints.get(i));
							break;
						}
					
					//point on top
					for(int i = 0, size = landpoints.size(); i < size; i++)
						if(p.x == landpoints.get(i).x && p.y == landpoints.get(i).y + 1){
							l.add(landpoints.get(i));
							break;
						}
					
					//water
					
					//point on bottom
					for(int i = 0, size = waterpoints.size(); i < size; i++)
						if(p.x == waterpoints.get(i).x && p.y == waterpoints.get(i).y - 1){
							l.add(waterpoints.get(i));
							break;
						}
					
					//point on top
					for(int i = 0, size = waterpoints.size(); i < size; i++)
						if(p.x == waterpoints.get(i).x && p.y == waterpoints.get(i).y + 1){
							l.add(waterpoints.get(i));
							break;
						}
					
					//excluded
					
					//point on bottom
					for(int i = 0, size = excludedpoints.size(); i < size; i++)
						if(p.x == excludedpoints.get(i).x && p.y == excludedpoints.get(i).y - 1){
							l.add(excludedpoints.get(i));
							break;
						}
					
					//point on top
					for(int i = 0, size = excludedpoints.size(); i < size; i++)
						if(p.x == excludedpoints.get(i).x && p.y == excludedpoints.get(i).y + 1){
							l.add(excludedpoints.get(i));
							break;
						}
					
				}
				
			}
			//vertical
			else{
				
				Point pmin = currenttarget.get(0);
				Point pmax = currenttarget.get(0);
				
				for(Point p:currenttarget){
					if(p.y < pmin.y) pmin = p;
					else if(p.y > pmax.y) pmax = p;
				}
				
				//search on land
				if( bg[pmin.x][pmin.y] == GamePanel.GRASS){
					
					//point on top
					for(int i = 0, size = landpoints.size(); i < size; i++)
						if(pmin.x == landpoints.get(i).x && pmin.y == landpoints.get(i).y + 1){
							l.add(landpoints.get(i));
							break;
						}
					
				}
				//search on water
				else{
					
					//point on top
					for(int i = 0, size = waterpoints.size(); i < size; i++)
						if(pmin.x == waterpoints.get(i).x && pmin.y == waterpoints.get(i).y + 1){
							l.add(waterpoints.get(i));
							break;
						}
					
				}
				
				//search on land
				if( bg[pmax.x][pmax.y] == GamePanel.GRASS){
					
					//point on bottom
					for(int i = 0, size = landpoints.size(); i < size; i++)
						if(pmax.x == landpoints.get(i).x && pmax.y == landpoints.get(i).y - 1){
							l.add(landpoints.get(i));
							break;
						}
					
				}
				//search on water
				else{
					
					//point on bottom
					for(int i = 0, size = waterpoints.size(); i < size; i++)
						if(pmax.x == waterpoints.get(i).x && pmax.y == waterpoints.get(i).y - 1){
							l.add(waterpoints.get(i));
							break;
						}
					
				}
				
				if(l.size() == 0){
					
					//search in all
					
					//land
					
					//point on bottom
					for(int i = 0, size = landpoints.size(); i < size; i++)
						if(pmax.x == landpoints.get(i).x && pmax.y == landpoints.get(i).y - 1){
							l.add(landpoints.get(i));
							break;
						}
					
					//point on top
					for(int i = 0, size = landpoints.size(); i < size; i++)
						if(pmin.x == landpoints.get(i).x && pmin.y == landpoints.get(i).y + 1){
							l.add(landpoints.get(i));
							break;
						}
					
					//water
					
					//point on bottom
					for(int i = 0, size = waterpoints.size(); i < size; i++)
						if(pmax.x == waterpoints.get(i).x && pmax.y == waterpoints.get(i).y - 1){
							l.add(waterpoints.get(i));
							break;
						}
					
					//point on top
					for(int i = 0, size = waterpoints.size(); i < size; i++)
						if(pmin.x == waterpoints.get(i).x && pmin.y == waterpoints.get(i).y + 1){
							l.add(waterpoints.get(i));
							break;
						}
					
					//excluded
					
					//point on bottom
					for(int i = 0, size = excludedpoints.size(); i < size; i++)
						if(pmax.x == excludedpoints.get(i).x && pmax.y == excludedpoints.get(i).y - 1){
							l.add(excludedpoints.get(i));
							break;
						}
					
					//point on top
					for(int i = 0, size = excludedpoints.size(); i < size; i++)
						if(pmin.x == excludedpoints.get(i).x && pmin.y == excludedpoints.get(i).y + 1){
							l.add(excludedpoints.get(i));
							break;
						}
					
				}
				
				//air ship
				if(l.size() == 0 && currenttarget.size() == 3){
				
					Point p = new Point( pmin.x, pmin.y + 1 );
					
					//search in all
					
					//land
					
					//point on right
					for(int i = 0, size = landpoints.size(); i < size; i++)
						if(p.x == landpoints.get(i).x - 1 && p.y == landpoints.get(i).y){
							l.add(landpoints.get(i));
							break;
						}
					
					//point on left
					for(int i = 0, size = landpoints.size(); i < size; i++)
						if(p.x == landpoints.get(i).x + 1 && p.y == landpoints.get(i).y){
							l.add(landpoints.get(i));
							break;
						}
					
					//water
					
					//point on right
					for(int i = 0, size = waterpoints.size(); i < size; i++)
						if(p.x == waterpoints.get(i).x - 1 && p.y == waterpoints.get(i).y){
							l.add(waterpoints.get(i));
							break;
						}
					
					//point on left
					for(int i = 0, size = waterpoints.size(); i < size; i++)
						if(p.x == waterpoints.get(i).x + 1 && p.y == waterpoints.get(i).y){
							l.add(waterpoints.get(i));
							break;
						}
					
					//excluded
					
					//point on right
					for(int i = 0, size = excludedpoints.size(); i < size; i++)
						if(p.x == excludedpoints.get(i).x - 1 && p.y == excludedpoints.get(i).y){
							l.add(excludedpoints.get(i));
							break;
						}
					
					//point on left
					for(int i = 0, size = excludedpoints.size(); i < size; i++)
						if(p.x == excludedpoints.get(i).x + 1 && p.y == excludedpoints.get(i).y){
							l.add(excludedpoints.get(i));
							break;
						}
					
					
				}
				
			}
			
		}
		
		if(l.size() == 0){
			
			double d;
			
			Point p1;
			
			//less then or equals sqrt(2)
			for(int i = 0; i < landpoints.size(); i++){
				
				p1 = landpoints.get(i);
				
				for(Point p2: currenttarget){
					
					d = Math.sqrt( ( p2.x - p1.x ) * ( p2.x - p1.x ) + ( p2.y - p1.y ) * ( p2.y - p1.y ) );
					d -= 0.0001; //to prevent calculation fail
					
					if(d <= Math.sqrt(2)){
						l.add(p1);
						break;
					}
					
				}
				
			}
			
			for(int i = 0; i < waterpoints.size(); i++){
				
				p1 = waterpoints.get(i);
				
				for(Point p2: currenttarget){
					
					d = Math.sqrt( ( p2.x - p1.x ) * ( p2.x - p1.x ) + ( p2.y - p1.y ) * ( p2.y - p1.y ) );
					d -= 0.0001; //to prevent calculation fail
					
					if(d <= Math.sqrt(2)){
						l.add(p1);
						break;
					}
					
				}
				
			}

			for(int i = 0; i < excludedpoints.size(); i++){
		
				p1 = excludedpoints.get(i);
		
				for(Point p2: currenttarget){
			
					d = Math.sqrt( ( p2.x - p1.x ) * ( p2.x - p1.x ) + ( p2.y - p1.y ) * ( p2.y - p1.y ) );
					d -= 0.0001; //to prevent calculation fail
			
					if(d <= Math.sqrt(2)){
						l.add(p1);
						break;
					}
			
				}
		
			}
			
		}
		
		return l;
		
	}
	
	private void excludePointsAroundShip(){
		
		double d;
		
		Point p1;
		
		//less then or equals sqrt(2)
		for(int i = 0; i < landpoints.size(); i++){
			
			p1 = landpoints.get(i);
			
			for(Point p2: currenttarget){
				
				d = Math.sqrt( ( p2.x - p1.x ) * ( p2.x - p1.x ) + ( p2.y - p1.y ) * ( p2.y - p1.y ) );
				d -= 0.0001; //to prevent calculation fail
				
				if(d <= Math.sqrt(2)){
					landpoints.remove(p1);
					i--;
					break;
				}
				
			}
			
		}
		
		for(int i = 0; i < waterpoints.size(); i++){
			
			p1 = waterpoints.get(i);
			
			for(Point p2: currenttarget){
				
				d = Math.sqrt( ( p2.x - p1.x ) * ( p2.x - p1.x ) + ( p2.y - p1.y ) * ( p2.y - p1.y ) );
				d -= 0.0001; //to prevent calculation fail
				
				if(d <= Math.sqrt(2)){
					waterpoints.remove(p1);
					i--;
					break;
				}
				
			}
			
		}

		for(int i = 0; i < excludedpoints.size(); i++){
	
			p1 = excludedpoints.get(i);
	
			for(Point p2: currenttarget){
		
				d = Math.sqrt( ( p2.x - p1.x ) * ( p2.x - p1.x ) + ( p2.y - p1.y ) * ( p2.y - p1.y ) );
				d -= 0.0001; //to prevent calculation fail
		
				if(d <= Math.sqrt(2)){
					excludedpoints.remove(p1);
					i--;
					break;
				}
		
			}
	
		}
		
	}
	
	public void makeShoot(Main main, Player player1) {
		
		boolean ctrl = true;
		
		while(ctrl){
		
			ctrl = false;	
				
			byte[][] shoot;
			
			lastshipcount = getAllShipsCount(player1);
			
			//if last shoot is null there are some water or land points
			
			//shoot when current target exist
			if(lastshoot == null && currenttarget.size() > 0){
				
				List<Point> availableshoots = getAvailableShoots();
				
				Point p; 
				
				if(availableshoots.size() > 0){
					p = availableshoots.get( r.nextInt(availableshoots.size()) );
				}
				else{
					List<Point> allpoints = new ArrayList<Point>();
					allpoints.addAll(landpoints);
					allpoints.addAll(waterpoints);
					allpoints.addAll(excludedpoints);
					p = allpoints.get( r.nextInt( allpoints.size() ) );
				}
				
				ctrl = !player1.getGamePanel().mouseClickLeft(p.x*Ship.CLUSTERSIZE, p.y*Ship.CLUSTERSIZE, 0, 0);
				shoot = player1.getGamePanel().getShootView();
				if(shoot[p.x][p.y] == GamePanel.HIT)
					currenttarget.add(p);
				
				landpoints.remove(p);
				waterpoints.remove(p);
				excludedpoints.remove(p);
				
				super.makeShoot(main, player1);
				
			}
			//land shoot when no last shoot
			else if(lastshoot == null && landpoints.size() > 0){
				lastshoot = landpoints.remove(r.nextInt( landpoints.size() ));
				landpointsused.add(lastshoot);
				ctrl = !player1.getGamePanel().mouseClickLeft(lastshoot.x*Ship.CLUSTERSIZE, lastshoot.y*Ship.CLUSTERSIZE, 0, 0);
				shoot = player1.getGamePanel().getShootView();
				if(shoot[lastshoot.x][lastshoot.y] == GamePanel.HIT){
					currenttarget.add(lastshoot);
					lastshoot = null;
				}
				super.makeShoot(main, player1);
			}
			//water shoot when no last shoot
			else if(lastshoot == null && waterpoints.size() > 0){
				lastshoot = waterpoints.remove(r.nextInt( waterpoints.size() ));
				waterpointsused.add(lastshoot);
				ctrl = !player1.getGamePanel().mouseClickLeft(lastshoot.x*Ship.CLUSTERSIZE, lastshoot.y*Ship.CLUSTERSIZE, 0, 0);
				shoot = player1.getGamePanel().getShootView();
				if(shoot[lastshoot.x][lastshoot.y] == GamePanel.HIT){
					currenttarget.add(lastshoot);
					lastshoot = null;
				}
				super.makeShoot(main, player1);
			}
			//if last shoot exist
			else if(lastshoot != null){
				
				
				
				//land
				if(bg[lastshoot.x][lastshoot.y] == GamePanel.GRASS && landpoints.size() > 0){
					
					List<Point> neighbors = getFourNeighbors(lastshoot);
					
					//have neighbors
					if(neighbors.size() > 0){
						
						lastshoot = neighbors.remove(r.nextInt( neighbors.size() ));
						landpoints.remove(lastshoot);
						landpointsused.add(lastshoot);
						ctrl = !player1.getGamePanel().mouseClickLeft(lastshoot.x*Ship.CLUSTERSIZE, lastshoot.y*Ship.CLUSTERSIZE, 0, 0);
						shoot = player1.getGamePanel().getShootView();
						if(shoot[lastshoot.x][lastshoot.y] == GamePanel.HIT){
							currenttarget.add(lastshoot);
							lastshoot = null;
						}
						super.makeShoot(main, player1);
						
					}
					// no neighbors
					else{
						
						int i = 0;
						for(int size = landpointsused.size(); i < size; i++){
							
							neighbors = getFourNeighbors(landpointsused.get(i));
							
							if(neighbors.size() > 0){
								
								lastshoot = neighbors.remove(r.nextInt( neighbors.size() ));
								landpoints.remove(lastshoot);
								landpointsused.add(lastshoot);
								ctrl = !player1.getGamePanel().mouseClickLeft(lastshoot.x*Ship.CLUSTERSIZE, lastshoot.y*Ship.CLUSTERSIZE, 0, 0);
								shoot = player1.getGamePanel().getShootView();
								if(shoot[lastshoot.x][lastshoot.y] == GamePanel.HIT){
									currenttarget.add(lastshoot);
									lastshoot = null;
								}
								super.makeShoot(main, player1);
								break;
								
							}
							
						}
						
						if(i == landpointsused.size()){
							
							//random
							lastshoot = landpoints.remove(r.nextInt( landpoints.size() ));
							landpointsused.add(lastshoot);
							ctrl = !player1.getGamePanel().mouseClickLeft(lastshoot.x*Ship.CLUSTERSIZE, lastshoot.y*Ship.CLUSTERSIZE, 0, 0);
							shoot = player1.getGamePanel().getShootView();
							if(shoot[lastshoot.x][lastshoot.y] == GamePanel.HIT){
								currenttarget.add(lastshoot);
								lastshoot = null;
							}
							super.makeShoot(main, player1);
							
						}
							
					}
					
				}
				
				
				//water
				//from land
				else if(bg[lastshoot.x][lastshoot.y] == GamePanel.GRASS && landpoints.size() == 0 && waterpoints.size() > 0){
					
					//random
					lastshoot = waterpoints.remove(r.nextInt( waterpoints.size() ));
					waterpointsused.add(lastshoot);
					ctrl = !player1.getGamePanel().mouseClickLeft(lastshoot.x*Ship.CLUSTERSIZE, lastshoot.y*Ship.CLUSTERSIZE, 0, 0);
					shoot = player1.getGamePanel().getShootView();
					if(shoot[lastshoot.x][lastshoot.y] == GamePanel.HIT){
						currenttarget.add(lastshoot);
						lastshoot = null;
					}
					super.makeShoot(main, player1);
					
				}
				
				//from water
				else if(bg[lastshoot.x][lastshoot.y] == GamePanel.WATER && waterpoints.size() > 0){
					
					//if no one segment
					if(player1.getGamePanel().getShipCount(ShipType.SeaShipOneSegment) == 0){
						List<Point> neighbors = getFourNeighbors(lastshoot);
						
						//have neighbors
						if(neighbors.size() > 0){
							
							lastshoot = neighbors.remove(r.nextInt( neighbors.size() ));
							waterpoints.remove(lastshoot);
							waterpointsused.add(lastshoot);
							ctrl = !player1.getGamePanel().mouseClickLeft(lastshoot.x*Ship.CLUSTERSIZE, lastshoot.y*Ship.CLUSTERSIZE, 0, 0);
							shoot = player1.getGamePanel().getShootView();
							if(shoot[lastshoot.x][lastshoot.y] == GamePanel.HIT){
								currenttarget.add(lastshoot);
								lastshoot = null;
							}
							super.makeShoot(main, player1);
							
						}
						// no neighbors
						else{
							
							int i = 0;
							for(int size = waterpointsused.size(); i < size; i++){
								
								neighbors = getFourNeighbors(waterpointsused.get(i));
								
								if(neighbors.size() > 0){
									
									lastshoot = neighbors.remove(r.nextInt( neighbors.size() ));
									waterpoints.remove(lastshoot);
									waterpointsused.add(lastshoot);
									ctrl = !player1.getGamePanel().mouseClickLeft(lastshoot.x*Ship.CLUSTERSIZE, lastshoot.y*Ship.CLUSTERSIZE, 0, 0);
									shoot = player1.getGamePanel().getShootView();
									if(shoot[lastshoot.x][lastshoot.y] == GamePanel.HIT){
										currenttarget.add(lastshoot);
										lastshoot = null;
									}
									super.makeShoot(main, player1);
									break;
									
								}
								
							}
							
							if(i == waterpointsused.size()){
								
								//random
								lastshoot = waterpoints.remove(r.nextInt( waterpoints.size() ));
								waterpointsused.add(lastshoot);
								ctrl = !player1.getGamePanel().mouseClickLeft(lastshoot.x*Ship.CLUSTERSIZE, lastshoot.y*Ship.CLUSTERSIZE, 0, 0);
								shoot = player1.getGamePanel().getShootView();
								if(shoot[lastshoot.x][lastshoot.y] == GamePanel.HIT){
									currenttarget.add(lastshoot);
									lastshoot = null;
								}
								super.makeShoot(main, player1);
								
							}
								
						}
					}
					else{
						
						//random
						lastshoot = waterpoints.remove(r.nextInt( waterpoints.size() ));
						waterpointsused.add(lastshoot);
						ctrl = !player1.getGamePanel().mouseClickLeft(lastshoot.x*Ship.CLUSTERSIZE, lastshoot.y*Ship.CLUSTERSIZE, 0, 0);
						shoot = player1.getGamePanel().getShootView();
						if(shoot[lastshoot.x][lastshoot.y] == GamePanel.HIT){
							currenttarget.add(lastshoot);
							lastshoot = null;
						}
						super.makeShoot(main, player1);
						
					}
					
				}
				
				//excluded
				else{
					lastshoot = excludedpoints.remove(r.nextInt( excludedpoints.size() ));
					landpointsused.add(lastshoot);
					ctrl = !player1.getGamePanel().mouseClickLeft(lastshoot.x*Ship.CLUSTERSIZE, lastshoot.y*Ship.CLUSTERSIZE, 0, 0);
					shoot = player1.getGamePanel().getShootView();
					if(shoot[lastshoot.x][lastshoot.y] == GamePanel.HIT){
						currenttarget.add(lastshoot);
						lastshoot = null;
					}
					super.makeShoot(main, player1);
				}
				
				
			}
			
			if(lastshipcount != getAllShipsCount(player1)){
				excludePointsAroundShip();
				currenttarget.clear();
			}
			
			//check if exist points for exclude
			//looking for points on land that should be excluded
			for(int i = 0; i < landpoints.size(); i++){
				if(isExcludable( landpoints.get(i) )){
					excludedpoints.add( landpoints.remove( i ) );
					i--;
				}
			}
			
			int[] count = player1.getGamePanel().getAliveShipCount();
			
			if( count[ComputerPlayerViewAdapter.LANDSHIPTWOSEGMENTINDEX] + count[ComputerPlayerViewAdapter.LANDSHIPTHREESEGMENTINDEX] + count[ComputerPlayerViewAdapter.LANDSHIPFOURSEGMENTINDEX] == 0){
				
				excludedpoints.addAll( landpoints );
				landpoints.clear();
				
			}
			
			if(count[ComputerPlayerViewAdapter.SEASHIPONESEGMENTINDEX] == 0){
				//looking for points on water that should be excluded
				for(int i = 0; i < waterpoints.size(); i++){
					if(isExcludable( waterpoints.get(i) )){
						excludedpoints.add( waterpoints.remove( i ) );
						i--;
					}
				}
			}
			
			
			
		}
		
	}
	
}
