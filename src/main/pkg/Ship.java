package main.pkg;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import main.pkg.ShipDrawSwingAdapter;

public abstract class Ship implements ShipDrawSwingAdapter, Serializable {
	
	//all ships has types (patterns) definied by static final collections of point
	//all ships have dynamic field with points that can be rotate - points can be rotated, one rotate - 90 degrees
	
	//player can drag alredy added ship in edit mode and change it and move and rotate
	//when player try to add ship, program check collision with all other ships that are hanged in collection in GamePanel
	
	//on one side of panel there are avilable ships
	
	//ship is sticked to the cursor while editing and stick point is calculating using aproximation method. center point are calculation every pattern change or rotate 
	
	//algorithm that will place ships must calculate clusters with land and with sea, there must be clusters equals to used ship segments and should find minimum free clusters
	
	//all coordinates are counted from 0
	
	//limit variables
	public static final int LANDSHIPTWOSEGMENTLIMIT = 3;
	public static final int LANDSHIPTHREESEGMENTLIMIT = 2;
	public static final int LANDSHIPFOURSEGMENTLIMIT = 1;
	public static final int SEASHIPONESEGMENTLIMIT = 4;
	public static final int SEASHIPTWOSEGMENTLIMIT = 3;
	public static final int SEASHIPTHREESEGMENTLIMIT = 2;
	public static final int SEASHIPFOURSEGMENTLIMIT = 1;
	public static final int AIRSHIPLIMIT = 1;
	public static final int BOARDWIDTH = 22;
	public static final int BOARDHEIGHT = 14;
	public static final int CLUSTERSIZE = 25;
	
	protected List<Point> point;
	protected List<Point> destroyedpoint;
	protected Point origin;
	
	protected int degree;
	
	public Ship(){
		origin = new Point(0, 0);
		point = new ArrayList<Point>();
		destroyedpoint = new ArrayList<Point>();
		degree = 0;
	}
	
	public void rotateCounterClockwise(){
		
		degree = ( degree + 90 ) % 360;
		
		Iterator<Point> it = point.iterator();
		int x;
		Point p;
		
		//rotating each point by -90 degree but on screen it looks like 90
		while(it.hasNext()){
			p = it.next();
			x = p.x;
			p.x = p.y;
			p.y = -x;
		}
		
	}
	
	public int getDegree(){
		return degree;
	}
	
	public void move(int x, int y){ //mouse position in Ship coordinates
		
		//add new coordinates of origin
		origin.x = x;
		origin.y = y;
		
		Iterator<Point> it = point.iterator();
		Point p;
		int xmax = 0;
		int xmin = BOARDWIDTH - 1;
		int ymax = 0;
		int ymin = BOARDHEIGHT -1;
		
		//finding maximum and minimum coordinates
		while(it.hasNext()){
			p = it.next();
			xmax = (p.x + origin.x > xmax)?(p.x + origin.x):(xmax);
			xmin = (p.x + origin.x < xmin)?(p.x + origin.x):(xmin);
			ymax = (p.y + origin.y > ymax)?(p.y + origin.y):(ymax);
			ymin = (p.y + origin.y < ymin)?(p.y + origin.y):(ymin);
		}
		
		//correcting coordinates
		if(xmin < 0) origin.x -= xmin;
		else if(xmax > BOARDWIDTH - 1) origin.x -= xmax - BOARDWIDTH + 1;
		if(ymin < 0) origin.y -= ymin;
		else if(ymax > BOARDHEIGHT - 1) origin.y -= ymax - BOARDHEIGHT + 1;
		
 	}
	
	public boolean collideWith(Ship ship){
		
		//check all points, if they collide
		
		Iterator<Point> i = point.iterator();
		Iterator<Point> i2;
		Point p1, p2;
		int distx, disty;
		while(i.hasNext()){
			i2 = ship.point.iterator();
			p1 = (Point) i.next();
			while(i2.hasNext()){
				p2 = (Point) i2.next();
				distx = Math.abs(p1.x+origin.x-(p2.x+ship.origin.x));
				disty = Math.abs(p1.y+origin.y-(p2.y+ship.origin.y));
				if(Math.sqrt( distx*distx + disty*disty ) < 2.0) return true;
			}
		}
		return false;
	}
	
	public boolean shoot(int x, int y){
		//check if point x, y collide with ship and if so add this point to destroyed points
		Point p = getCollidePoint(x, y);
		if(p != null){
			destroyedpoint.add(p);
			return true;
		}
		return false;
	}
	
	public boolean isDestroyed(){
		return point.size() == destroyedpoint.size() ? true : false;
	}
	
	public void resetOrigin(){
		origin.x = 0;
		origin.y = 0;
	}
	
	public Point getCollidePoint(int x, int y){
		//check if point x, y collide with ship
		//if so, return point of the ship that cousing collision
		Iterator<Point> i = point.iterator();
		Point p;
		while(i.hasNext()){
			p = i.next();
			if(p.x+origin.x == x && p.y+origin.y == y){
				return p;
			}
		}
		return null;
	}
	
	public Point getDrawingCoogdinates(){
		//calculate minimum x and y and add origin
		int x = 0 , y = 0;
		Iterator<Point> i = point.iterator();
		Point p;
		while(i.hasNext()){
			p = i.next();
			if( p.x < x ) x = p.x;
			if( p.y < y ) x = p.y;
		}
		return new Point(x+origin.x, y+origin.y);
	}
	
	public boolean isOnCorrectBackground(byte[][] bg){
		return true;
	}
	
	public boolean isBackgroundFitting(int x, int y, byte bg){
		return true;
	}
	
	public List<Point> getPoints(){
		//points with origin added
		List<Point> list = new ArrayList<Point>();
		for(Point p:point){
			list.add( new Point( p.x + origin.x, p.y + origin.y ) );
		}
		return list;
	}
	
	public Set<Point> getAreaBeetween(Ship ship){
		
		Set<Point> set = new HashSet<Point>();
		
		double A, B, C, d;
		int x1, x2, y1, y2, x, y, xmin, ymin, dx, dy;
		
		Point p1, p2;
		Iterator<Point> it = point.iterator();
		Iterator<Point> it2;
		//iterating through each point
		while(it.hasNext()){
			it2 = ship.point.iterator();
			p1 = (Point) it.next();
			while(it2.hasNext()){
				p2 = (Point) it2.next();
				
				//calculate coordinates of elements on map
				x1 = p1.x + origin.x;
				y1 = p1.y + origin.y;
				x2 = p2.x + ship.origin.x;
				y2 = p2.y + ship.origin.y;
				
				//calculate line equation
				A = y1 - y2;
				B = x2 - x1;
				C = -y1*x2 + y2*x1;
				
				//calculate array of points to check
				xmin = Math.min(x1, x2);
				ymin = Math.min(y1, y2);
				
				dx = Math.abs(x1 - x2) + 1;
				dy = Math.abs(y1 - y2) + 1;
				
				//find points which touching line
				for(int i = 0, s = dx * dy; i < s; i++){
					x = xmin + i % dx;
					y = ymin + i / dx;
					d = Math.abs( A * x + B * y + C ) / Math.sqrt( A * A + B * B );
					//if distance is less then half of board element diagonal
					if(d < Math.sqrt(2)/2)
						set.add( new Point(x, y) );
				}
			}
		}
		return set;
		
	}
	
}
