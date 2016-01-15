package main.pkg;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

public class GamePanel implements PanelSwingDrawAdapter, ComputerPlayerViewAdapter {
	
	/*
	 * This is class that contains all informations of player game board state
	 * Here is all methods to handling mouse click and move, and it should implements Adapters for drawing game board
	 */
	
	public static final byte EMPTY = 0;
	public static final byte WATER = 1;
	public static final byte GRASS = 2;
	
	public static final byte NOSHOOT = 0;
	public static final byte MISS = 1;
	public static final byte HIT = 2;
	public static final byte FLAG = 3;
	
	private int clustersize;
	private int width;
	private int height;
	private boolean editable;
	private Ship draggedship;
	private byte draggedbg;
	private int mousex;
	private int mousey;
	
	private boolean opponent;
	
	private byte[][] background;
	private byte[][] shoot;
	
	private List<Ship> ship;
	private List<Ship> shiptoadd;
	
	private BufferedImage bgimage;
	
	public GamePanel(int width, int height, int clustersize, boolean opponent){
		
		//adding variables to object
		this.width = width;
		this.height = height;
		this.clustersize = clustersize;
		this.opponent = opponent;
		editable = !opponent;
		
		ship = new ArrayList<Ship>();
		shiptoadd = new ArrayList<Ship>();
		
		background = new byte[width][height];
		shoot = new byte[width][height];
		
		initShipToAdd();
		
		for(int i = 0; i < background.length; Arrays.fill(background[i++], (byte)1));
		for(int i = 0; i < shoot.length; Arrays.fill(shoot[i++], NOSHOOT));
		
	}
	
	public void initShipToAdd(){
		
		//adding all ships to lobby, then ships will be added to the board by player using mouse events
		
		int i;
		
		for(i = 0; i < Ship.AIRSHIPLIMIT; i++)
			shiptoadd.add( ShipFactory.makeShip(ShipType.AirShip) );
		
		for(i = 0; i < Ship.SEASHIPONESEGMENTLIMIT; i++)
			shiptoadd.add( ShipFactory.makeShip(ShipType.SeaShipOneSegment) );
		
		for(i = 0; i < Ship.SEASHIPTWOSEGMENTLIMIT; i++)
			shiptoadd.add( ShipFactory.makeShip(ShipType.SeaShipTwoSegment) );
		
		for(i = 0; i < Ship.SEASHIPTHREESEGMENTLIMIT; i++)
			shiptoadd.add( ShipFactory.makeShip(ShipType.SeaShipThreeSegment) );
		
		for(i = 0; i < Ship.SEASHIPFOURSEGMENTLIMIT; i++)
			shiptoadd.add( ShipFactory.makeShip(ShipType.SeaShipFourSegment) );
		
		for(i = 0; i < Ship.LANDSHIPTWOSEGMENTLIMIT; i++)
			shiptoadd.add( ShipFactory.makeShip(ShipType.LandShipTwoSegment) );
		
		for(i = 0; i < Ship.LANDSHIPTHREESEGMENTLIMIT; i++)
			shiptoadd.add( ShipFactory.makeShip(ShipType.LandShipThreeSegment) );
		
		for(i = 0; i < Ship.LANDSHIPFOURSEGMENTLIMIT; i++)
			shiptoadd.add( ShipFactory.makeShip(ShipType.LandShipFourSegment) );
		
	}
	
	public void leaveEditMode(){
		editable = false;
	}
	
	public boolean canLeaveEditMode(){
		return (editable && shiptoadd.size() == 0 && draggedship == null)?(true):(false);
	}
	
	public boolean isEditable(){
		return editable;
	}
	
	public boolean isOpponent(){
		return opponent;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getClusterSize(){
		return clustersize;
	}
	
	public boolean isGameEnd(){
		
		//if all ships destroyed
		
		boolean ctrl = true;
		
		for(Ship s: ship)
			if(!s.isDestroyed()){
				ctrl = false;
				break;
			}
		
		return ctrl;
		
	}
	
	public int getShipCount(ShipType type){
		return countShip(ship, type);
	}
	
	public int getShipToAddCount(ShipType type){
		return countShip(shiptoadd, type);
	}
	
	private static int countShip( List<Ship> list, ShipType type ){
		
		int count = 0;
		
		Iterator<Ship> it = list.iterator();
		
		if(it == null) return 0;
		
		Ship s;
		
		switch(type){
		
			case LandShipTwoSegment:{
				while(it.hasNext()){
					s = it.next();
					if(s instanceof LandShipTwoSegment && !s.isDestroyed()) count++;
				}
				break;
			}
			case LandShipThreeSegment:{
				while(it.hasNext()){
					s = it.next();
					if(s instanceof LandShipThreeSegment && !s.isDestroyed()) count++;
				}
				break;
			}
			case LandShipFourSegment:{
				while(it.hasNext()){
					s = it.next();
					if(s instanceof LandShipFourSegment && !s.isDestroyed()) count++;
				}
				break;
			}
			case SeaShipOneSegment:{
				while(it.hasNext()){
					s = it.next();
					if(s instanceof SeaShipOneSegment && !s.isDestroyed()) count++;
				}
				break;
			}
			case SeaShipTwoSegment:{
				while(it.hasNext()){
					s = it.next();
					if(s instanceof SeaShipTwoSegment && !s.isDestroyed()) count++;
				}
				break;
			}
			case SeaShipThreeSegment:{
				while(it.hasNext()){
					s = it.next();
					if(s instanceof SeaShipThreeSegment && !s.isDestroyed()) count++;
				}
				break;
			}
			case SeaShipFourSegment:{
				while(it.hasNext()){
					s = it.next();
					if(s instanceof SeaShipFourSegment && !s.isDestroyed()) count++;
				}
				break;
			}
			case AirShip:{
				while(it.hasNext()){
					s = it.next();
					if(s instanceof AirShip && !s.isDestroyed()) count++;
				}
				break;
			}
			default: return 0;
	
		}
		
		return count;
		
	}
	
	private Ship getShipFromAddList(ShipType type){
		Ship s;
		for(int i = 0, size = shiptoadd.size(); i < size; i++){
			s = shiptoadd.get(i);
			switch(type){
			
				case LandShipTwoSegment:{
					if(s instanceof LandShipTwoSegment) return shiptoadd.remove(i);
					break;
				}
				case LandShipThreeSegment:{
					if(s instanceof LandShipThreeSegment) return shiptoadd.remove(i);
					break;
				}
				case LandShipFourSegment:{
					if(s instanceof LandShipFourSegment) return shiptoadd.remove(i);
					break;
				}
				case SeaShipOneSegment:{
					if(s instanceof SeaShipOneSegment) return shiptoadd.remove(i);
					break;
				}
				case SeaShipTwoSegment:{
					if(s instanceof SeaShipTwoSegment) return shiptoadd.remove(i);
					break;
				}
				case SeaShipThreeSegment:{
					if(s instanceof SeaShipThreeSegment) return shiptoadd.remove(i);
					break;
				}
				case SeaShipFourSegment:{
					if(s instanceof SeaShipFourSegment) return shiptoadd.remove(i);
					break;
				}
				case AirShip:{
					if(s instanceof AirShip) return shiptoadd.remove(i);
				}
				
			}
		}
		return null;
	}
	
	private boolean isCollideWithAnyShip(Ship s){
		Iterator<Ship> i = ship.iterator();
		while(i.hasNext()){
			if(i.next().collideWith(s)) return true;
		}
		return false;
	}
	
	private boolean isFittingToEachShip(int x, int y, byte bg){
		Iterator<Ship> i = ship.iterator();
		while(i.hasNext()){
			if( !(i.next().isBackgroundFitting(x, y, bg)) ) return false;
		}
		return true;
	}
	
	private Ship isClickedOnShip(int x, int y){
		for(Ship s: ship){
			if(s.getCollidePoint(x, y) != null) return s;
		}
		return null;
	}
	
	public boolean shipPlacer(int range, int minimum){
		//this method should only be invoke by computer player!
		//it contains algorithm to randomly place ships
		Random r = new Random();
		
		//reset all variables
		ship = new ArrayList<Ship>();
		shiptoadd = new ArrayList<Ship>();
		
		background = new byte[width][height];
		shoot = new byte[width][height];
		
		initShipToAdd();
		
		for(int i = 0; i < background.length; Arrays.fill(background[i++], (byte)1));
		for(int i = 0; i < shoot.length; Arrays.fill(shoot[i++], NOSHOOT));
		
		//point with coordinates from <1;20>
		Point start = new Point(1+r.nextInt(width-2), 1+r.nextInt(height-2));
		
		//get AirShip
		Ship s = getShipFromAddList(ShipType.AirShip);
		s.move(start.x, start.y);
		
		//randomly rotated
		for(int i = 0, size = r.nextInt(4); i < size; i++) s.rotateCounterClockwise();
		
		//add first ship
		ship.add(s);
		
		//point on right
		internalShipPlacer(r, new Point( start.x + minimum + r.nextInt(range), start.y ), range, minimum, 0);
		
		//point on left
		internalShipPlacer(r, new Point( start.x - minimum - r.nextInt(range), start.y ), range, minimum, 1);
		
		//point on top
		internalShipPlacer(r, new Point( start.x, start.y - minimum - r.nextInt(range) ), range, minimum, 2);
		
		//point on bottom
		internalShipPlacer(r, new Point( start.x, start.y + minimum + r.nextInt(range) ), range, minimum, 3);
		
		//fill background with nothing
		for(int i = 0; i < background.length; Arrays.fill(background[i++], (byte)0));
		
		//add water under Sea Ships
		for(Ship seaship:ship){
			if(seaship instanceof SeaShipOneSegment ||
				seaship instanceof SeaShipTwoSegment ||
				seaship instanceof SeaShipThreeSegment ||
				seaship instanceof SeaShipFourSegment){
				List<Point> point = seaship.getPoints();
				for(Point p:point)
					background[p.x][p.y] = WATER;
			}
		}
		
		//basic land adder
		landAdder();
		
		//land expansion
		expansion(1 + (int)(2 * Math.random()), 0.6, GRASS);
		
		//water expansion
		//it must be used to avoid closing water ships on small lakes inside land, it should make huge lakes, or try to connect it to the ocean
		//first empty elements of map must be filled by water, because small empty lakes also can be generated by previous algorithm - now this algorithm alco expands this lakes
		
		//fill with water
		for(int i = 0, size = width*height; i < size; i++){
			if( background[i/height][i%height] == 0 )
				background[i/height][i%height] = WATER;
		}
		
		//water expansion
		expansion(1 + (int)(1 * Math.random()), 0.3, WATER);
		
		//repaint background
		bgimage = prepareBackgroung();
		
		return false;
	}
	
	private void expansion(int depth, double chance, byte type){
		
		//this algorithm finding empty points that touching land and try to fill it
		Map<Point, Integer> pointlist = new HashMap<Point, Integer>();
		
		//repeat
		for(int rep = 0; rep < depth; rep++){
		
			//finding points to fill
			for(int i = 0, size = width*height; i < size; i++){
				if( background[i/height][i%height] == 0 ){
					
					int ctrl = 0;
					
					//check if its touching land
					for(int j = 0, x, y; j < 9; j++){
						
						x = i/height + j/3 - 1;
						y = i%height + j%3 - 1;
						
						if(x > 0 && y > 0 && x < width && y < height){
							if(background[x][y] == type)
								ctrl = 1;
							else if( background[x][y] == ( (type == GRASS)?(WATER):(GRASS) ) ){
								ctrl = 2;
								break;
							}
						}
						
					}
					
					if(ctrl != 0) pointlist.put( new Point( i/height, i%height ), ctrl );
					
				}
			}
			
			//random fill
			for(Entry<Point, Integer> p:pointlist.entrySet())
				if(Math.random() < chance/p.getValue()) background[p.getKey().x][p.getKey().y] = type;
			
		}
		
	}
	
	private void landAdder(){
		Set<Point> set;
		
		//adding land - this iteration have quadratic time complexity - O(n^2)
		for(Ship landship1:ship){
			if(landship1 instanceof LandShipTwoSegment ||
				landship1 instanceof LandShipThreeSegment ||
				landship1 instanceof LandShipFourSegment){
				
				//fill map with grass under land ship
				List<Point> point = landship1.getPoints();
				for(Point p:point)
					background[p.x][p.y] = GRASS;
				
				//internal iteration
				for(int i = ship.indexOf(landship1)+1, size = ship.size(); i < size; i++){
					Ship landship2 = ship.get(i);
					if( !landship1.equals(landship2) && (landship2 instanceof LandShipTwoSegment ||
						landship2 instanceof LandShipThreeSegment ||
						landship2 instanceof LandShipFourSegment) ){
						set = landship1.getAreaBeetween(landship2);
						
						//looking for collision with water
						for(Point p:set)
							if(background[p.x][p.y] == WATER){
								set.clear();
								break;
							}
						
						//adding land
						for(Point p:set)
							background[p.x][p.y] = GRASS;
						
					}
				}
				
			}
		}
	}
	
	private void internalShipPlacer(Random r, Point p, int range, int minimum, int prdir){
		
		if(p.x < 0 || p.x >= width || p.y < 0 || p.y >= height || shiptoadd.size() == 0)
			return;
		
		Ship s = shiptoadd.remove( r.nextInt( shiptoadd.size() ) );
		
		//randomly rotated
		for(int i = 0, size = r.nextInt(4); i < size; i++) s.rotateCounterClockwise();
		
		s.move(p.x, p.y);
		
		if(!isCollideWithAnyShip(s)){
			ship.add(s);
		}
		else{
			s.resetOrigin();
			shiptoadd.add(s);
		}
		
		//calculate next points
		
		//point on right
		if(prdir != 1) internalShipPlacer(r, new Point( p.x + minimum + r.nextInt(range), p.y + r.nextInt(range)-range/2 ), range, minimum, 0);
				
		//point on left
		if(prdir != 0) internalShipPlacer(r, new Point( p.x - minimum - r.nextInt(range), p.y + r.nextInt(range)-range/2 ), range, minimum, 1);
				
		//point on top
		if(prdir != 3) internalShipPlacer(r, new Point( p.x + r.nextInt(range)-range/2, p.y - minimum - r.nextInt(range) ), range, minimum, 2);
				
		//point on bottom
		if(prdir != 2) internalShipPlacer(r, new Point( p.x + r.nextInt(range)-range/2, p.y + minimum + r.nextInt(range) ), range, minimum, 3);
		
	}
	
	public boolean mouseClickLeft(int x, int y, int offsetx, int offsety){
		
		//save mouse pointer position
		mousex = x;
		mousey = y;
		
		//calculate board coordinates
		int boardx = (x-offsetx)/clustersize;
		int boardy = (y-offsety)/clustersize;
		
		//if clicked on board
		if(boardx >= 0 && boardx < width && boardy >= 0 && boardy < height){
			
			//adding background to board
			if(draggedbg != 0 && isFittingToEachShip(boardx, boardy, draggedbg)){
				background[boardx][boardy] = draggedbg;
				return false;
			}
			
			//dragging ship from board
			Ship s = isClickedOnShip(boardx, boardy);
			if(editable && s != null){
				if(draggedship != null) shiptoadd.add(draggedship);
				draggedship = s;
				ship.remove(s);
				draggedship.resetOrigin();
				return false;
			}
			
			//adding ship to board
			if(draggedship != null){
				draggedship.move(boardx, boardy);
				if(!isCollideWithAnyShip(draggedship) && draggedship.isOnCorrectBackground(background)){
					ship.add(draggedship);
					draggedship = null;
					return false;
				}
				else{
					PopupWindow.makePopupWindow("Ships cannot touch each other and must be placed on correct background", PopupWindow.SHOTR).makePopup();
				}
				draggedship.resetOrigin();
				return false;
			}
			
			//make shoot
			if(!editable){
				if(shoot[boardx][boardy] != NOSHOOT) return false;
				shoot[boardx][boardy] = 1;
				Iterator<Ship> i = ship.iterator();
				while(i.hasNext()){
					if(i.next().shoot(boardx, boardy)){
						shoot[boardx][boardy] = 2;
						if(opponent) SoundPlayer.getInstance().play(SoundPlayer.TARGETSHOOT);
						return false;
					}
				}
				
				if(opponent) SoundPlayer.getInstance().play( background[boardx][boardy] == WATER ? SoundPlayer.WATERSHOOT : SoundPlayer.LANDSHOOT );
				
				return true;
			}
		}
		
		if(editable){ //dragging elements
			if( x >= 2*offsetx + bgimage.getWidth() && x <= 2*offsetx + bgimage.getWidth() + clustersize && y >= offsety+clustersize && y <= offsety+2*clustersize ){
				//drag water
				if(draggedship != null) shiptoadd.add(draggedship);
				draggedship = null;
				draggedbg = WATER;
				return false;
			}
			if( x >= 6*offsetx + bgimage.getWidth() && x <= 6*offsetx + bgimage.getWidth() + clustersize && y >= offsety+clustersize && y <= offsety+2*clustersize ){
				//drag grass
				if(draggedship != null) shiptoadd.add(draggedship);
				draggedship = null;
				draggedbg = GRASS;
				return false;
			}
			if( x >= 2*offsetx + bgimage.getWidth() && x <= 2*offsetx + bgimage.getWidth() + 2*clustersize && y >= offsety+3*clustersize && y <= offsety+5*clustersize ){
				//drag airplane
				if(draggedship != null) shiptoadd.add(draggedship);
				draggedship = null;
				draggedbg = 0;
				draggedship = getShipFromAddList(ShipType.AirShip);
				return false;
			}
			if( x >= 6*offsetx + bgimage.getWidth() && x <= 6*offsetx + bgimage.getWidth() + 2*clustersize && y >= offsety+3*clustersize && y <= offsety+5*clustersize ){
				//drag two segment land ship
				if(draggedship != null) shiptoadd.add(draggedship);
				draggedship = null;
				draggedbg = 0;
				draggedship = getShipFromAddList(ShipType.LandShipTwoSegment);
				return false;
			}
			if( x >= 2*offsetx + bgimage.getWidth() && x <= 2*offsetx + bgimage.getWidth() + 2*clustersize && y >= offsety+5*clustersize && y <= offsety+7*clustersize ){
				//drag three segment land ship
				if(draggedship != null) shiptoadd.add(draggedship);
				draggedship = null;
				draggedbg = 0;
				draggedship = getShipFromAddList(ShipType.LandShipThreeSegment);
				return false;
			}
			if( x >= 6*offsetx + bgimage.getWidth() && x <= 6*offsetx + bgimage.getWidth() + 2*clustersize && y >= offsety+5*clustersize && y <= offsety+7*clustersize ){
				//drag four segment land ship
				if(draggedship != null) shiptoadd.add(draggedship);
				draggedship = null;
				draggedbg = 0;
				draggedship = getShipFromAddList(ShipType.LandShipFourSegment);
				return false;
			}
			if( x >= 2*offsetx + bgimage.getWidth() && x <= 2*offsetx + bgimage.getWidth() + 2*clustersize && y >= offsety+7*clustersize && y <= offsety+9*clustersize ){
				//drag one segment sea ship
				if(draggedship != null) shiptoadd.add(draggedship);
				draggedship = null;
				draggedbg = 0;
				draggedship = getShipFromAddList(ShipType.SeaShipOneSegment);
				return false;
			}
			if( x >= 6*offsetx + bgimage.getWidth() && x <= 6*offsetx + bgimage.getWidth() + 2*clustersize && y >= offsety+7*clustersize && y <= offsety+9*clustersize ){
				//drag two segment sea ship
				if(draggedship != null) shiptoadd.add(draggedship);
				draggedship = null;
				draggedbg = 0;
				draggedship = getShipFromAddList(ShipType.SeaShipTwoSegment);
				return false;
			}
			if( x >= 2*offsetx + bgimage.getWidth() && x <= 2*offsetx + bgimage.getWidth() + 2*clustersize && y >= offsety+9*clustersize && y <= offsety+11*clustersize ){
				//drag three segment sea ship
				if(draggedship != null) shiptoadd.add(draggedship);
				draggedship = null;
				draggedbg = 0;
				draggedship = getShipFromAddList(ShipType.SeaShipThreeSegment);
				return false;
			}
			if( x >= 6*offsetx + bgimage.getWidth() && x <= 6*offsetx + bgimage.getWidth() + 2*clustersize && y >= offsety+9*clustersize && y <= offsety+11*clustersize ){
				//drag four segment sea ship
				if(draggedship != null) shiptoadd.add(draggedship);
				draggedship = null;
				draggedbg = 0;
				draggedship = getShipFromAddList(ShipType.SeaShipFourSegment);
				return false;
			}
		}
		
		return false;
		
	}
	
	public void mouseClickRight(int x, int y, int offsetx, int offsety){
		//rotate dragged ship
		if(draggedship != null) draggedship.rotateCounterClockwise();
		else if(!editable){
			//calculate board coordinates
			int boardx = (x-offsetx)/clustersize;
			int boardy = (y-offsety)/clustersize;
			
			if(shoot[boardx][boardy] == NOSHOOT)
				shoot[boardx][boardy] = FLAG;
			else if(shoot[boardx][boardy] == FLAG)
				shoot[boardx][boardy] = NOSHOOT;
				
		}
	}
	
	public void mouseMove(int x, int y){
		mousex = x;
		mousey = y;
	}
	
	public boolean isElementDragged(){
		return (draggedship == null && draggedbg == 0) ? false : true;
	}
	
	public boolean isMapSaveable(){
		
		return shiptoadd.size() == 0 ? true : false;
		
	}
	
	public void saveToFile(File file){
		
		if(shiptoadd.size() > 0) return;
		
		ObjectOutputStream out = null;
		
		try{
			
			out = new ObjectOutputStream( new FileOutputStream(file) );
			
			out.writeObject(background);
			for(Ship s:ship)
				out.writeObject(s);
					
		}
		catch(IOException e){
			
		}
		finally{
			try {
				out.close();
			} catch (IOException e) {
				
			}
		}
		
	}
	
	public void loadFromFile(File file){
		
		ObjectInputStream in = null;
		
		shiptoadd.clear();
		
		try{
			
			in = new ObjectInputStream( new FileInputStream(file) );
			
			background = (byte[][]) in.readObject();
			ship = new ArrayList<Ship>();
			
			Object o;
			while( ( o = in.readObject() ) != null )
				if(o instanceof Ship) ship.add( (Ship) o );
					
		}
		catch(IOException | ClassNotFoundException e){
			
		}
		finally{
			try {
				in.close();
			} catch (IOException e) {
				
			}
		}
		
	}
	
	@Override
	public void draw(Graphics g, int offsetx, int offsety, int panelwidth, int panelheight) {
		// TODO Auto-generated method stub
		if(bgimage == null || editable) bgimage = prepareBackgroung();
		g.setColor(g.getColor());
		g.fillRect(0, 0, panelwidth, panelheight);
		g.drawImage(bgimage, offsetx, offsety, null);
		
		//drawing ships on board
		Iterator<Ship> i = ship.iterator();
		Ship s;
		while(i.hasNext()){
			s = i.next();
			if( !opponent || (opponent && s.isDestroyed()) ) s.draw(g, offsetx, offsety);
		}
		GraphicLoader gloader = GraphicLoader.getInstance();
		
		//drawing separator
		
		g.setColor(Color.black);
		g.drawLine(6*offsetx + bgimage.getWidth() - 5, offsety+clustersize, 6*offsetx + bgimage.getWidth() - 5, offsety+11*clustersize);
		
		//drawing additional images for player
		
		if(!opponent){
			g.drawImage(gloader.getImage(GraphicLoader.WATERIMAGE), 2*offsetx + bgimage.getWidth() , offsety+clustersize, null);
			g.drawImage(gloader.getImage(GraphicLoader.GRASSIMAGE), 6*offsetx + bgimage.getWidth() , offsety+clustersize, null);
		}
		g.drawImage(gloader.getImage(GraphicLoader.SMALLAIRSHIPIMAGE), 2*offsetx + bgimage.getWidth() , offsety+3*clustersize, null);
		g.drawImage(gloader.getImage(GraphicLoader.SMALLLANDSHIPTWOSEGMENTIMAGE), 6*offsetx + bgimage.getWidth() , offsety+3*clustersize, null);
		g.drawImage(gloader.getImage(GraphicLoader.SMALLLANDSHIPTHREESEGMENTIMAGE), 2*offsetx + bgimage.getWidth() , offsety+5*clustersize, null);
		g.drawImage(gloader.getImage(GraphicLoader.SMALLLANDSHIPFOURSEGMENTIMAGE), 6*offsetx + bgimage.getWidth() , offsety+5*clustersize, null);
		g.drawImage(gloader.getImage(GraphicLoader.SMALLSEASHIPONESEGMENTIMAGE), 2*offsetx + bgimage.getWidth() , offsety+7*clustersize, null);
		g.drawImage(gloader.getImage(GraphicLoader.SMALLSEASHIPTWOSEGMENTIMAGE), 6*offsetx + bgimage.getWidth() , offsety+7*clustersize, null);
		g.drawImage(gloader.getImage(GraphicLoader.SMALLSEASHIPTHREESEGMENTIMAGE), 2*offsetx + bgimage.getWidth() , offsety+9*clustersize, null);
		g.drawImage(gloader.getImage(GraphicLoader.SMALLSEASHIPFOURSEGMENTIMAGE), 6*offsetx + bgimage.getWidth() , offsety+9*clustersize, null);
		
		//drawing ship count numbers

		g.setFont(new Font("Arial", Font.PLAIN, 20));
		
		//airplanes
		g.drawString((editable && !opponent)?(getShipToAddCount(ShipType.AirShip)+""):(getShipCount(ShipType.AirShip)+""), 5*offsetx + bgimage.getWidth(), offsety+4*clustersize);
		
		//two segment land ship
		g.drawString((editable && !opponent)?(getShipToAddCount(ShipType.LandShipTwoSegment)+""):(getShipCount(ShipType.LandShipTwoSegment)+""), 9*offsetx + bgimage.getWidth(), offsety+4*clustersize);
		
		//three segment land ship
		g.drawString((editable && !opponent)?(getShipToAddCount(ShipType.LandShipThreeSegment)+""):(getShipCount(ShipType.LandShipThreeSegment)+""), 5*offsetx + bgimage.getWidth(), offsety+6*clustersize);
				
		//four segment land ship
		g.drawString((editable && !opponent)?(getShipToAddCount(ShipType.LandShipFourSegment)+""):(getShipCount(ShipType.LandShipFourSegment)+""), 9*offsetx + bgimage.getWidth(), offsety+6*clustersize);
		
		//one segment sea ship
		g.drawString((editable && !opponent)?(getShipToAddCount(ShipType.SeaShipOneSegment)+""):(getShipCount(ShipType.SeaShipOneSegment)+""), 5*offsetx + bgimage.getWidth(), offsety+8*clustersize);
				
		//two segment sea ship
		g.drawString((editable && !opponent)?(getShipToAddCount(ShipType.SeaShipTwoSegment)+""):(getShipCount(ShipType.SeaShipTwoSegment)+""), 9*offsetx + bgimage.getWidth(), offsety+8*clustersize);
				
		//three segment sea ship
		g.drawString((editable && !opponent)?(getShipToAddCount(ShipType.SeaShipThreeSegment)+""):(getShipCount(ShipType.SeaShipThreeSegment)+""), 5*offsetx + bgimage.getWidth(), offsety+10*clustersize);
						
		//four segment sea ship
		g.drawString((editable && !opponent)?(getShipToAddCount(ShipType.SeaShipFourSegment)+""):(getShipCount(ShipType.SeaShipFourSegment)+""), 9*offsetx + bgimage.getWidth(), offsety+10*clustersize);
		
		//draw shoot board
		for(int j = 0, size = width*height; j < size; j++){
			switch( shoot[j/height][j%height] ){
				case MISS:{
					g.setColor(Color.green);
					g.fillRect( (j/height)*clustersize+offsetx+clustersize/4, (j%height)*clustersize+offsety+clustersize/4, clustersize/2, clustersize/2);
					break;
				}
				case HIT:{
					g.setColor(Color.red);
					g.fillRect( (j/height)*clustersize+offsetx+clustersize/4, (j%height)*clustersize+offsety+clustersize/4, clustersize/2, clustersize/2);
					break;
				}
				case FLAG:{
					g.setColor(Color.blue);
					g.fillRect( (j/height)*clustersize+offsetx+clustersize/4, (j%height)*clustersize+offsety+clustersize/4, clustersize/2, clustersize/2);
					break;
				}
			}
			g.setColor(Color.black);
			g.drawRect( (j/height)*clustersize+offsetx, (j%height)*clustersize+offsety, clustersize, clustersize);
		}
		
		//dragged
		if(draggedbg != 0){
			g.drawImage( (draggedbg == 1)?(GraphicLoader.getInstance().getImage(GraphicLoader.WATERIMAGE)):(GraphicLoader.getInstance().getImage(GraphicLoader.GRASSIMAGE)) , mousex-clustersize/2, mousey-clustersize/2, null);
		}
		
		if(draggedship != null){
			draggedship.draw(g, mousex, mousey);
		}
		
	}

	@Override
	public BufferedImage prepareBackgroung() {
		//Prepare background
		BufferedImage img = new BufferedImage(width*clustersize, height*clustersize, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		Image water = GraphicLoader.getInstance().getImage(GraphicLoader.WATERIMAGE);
		Image grass = GraphicLoader.getInstance().getImage(GraphicLoader.GRASSIMAGE);
		for(int i = 0, size = width*height; i < size; i++){
			switch( background[i/height][i%height] ){
				case WATER:{
					g.drawImage(water, (i/height)*clustersize, (i%height)*clustersize, null);
					break;
				}
				case GRASS:{
					g.drawImage(grass, (i/height)*clustersize, (i%height)*clustersize, null);
					break;
				}
				default :{
					g.setColor(Color.black);
					g.drawRect( (i/height)*clustersize, (i%height)*clustersize, clustersize, clustersize);
				}
			}
		}
		return img;
	}

	@Override
	public byte[][] getShootView() {
		return shoot.clone();
	}

	@Override
	public int[] getAliveShipCount() {
		
		int[] ret = new int[ComputerPlayerViewAdapter.ARRAYLENGTH];
		
		Iterator<Ship> i = ship.iterator();
		Ship s;
		while(i.hasNext()){
			s = i.next();
			if( !s.isDestroyed() ){
				
				ret[ComputerPlayerViewAdapter.AMOUNTINDEX]++;
				
				if( s instanceof AirShip )
					ret[ComputerPlayerViewAdapter.AIRSHIPINDEX]++;
				else if( s instanceof LandShipTwoSegment )
					ret[ComputerPlayerViewAdapter.LANDSHIPTWOSEGMENTINDEX]++;
				else if( s instanceof LandShipThreeSegment )
					ret[ComputerPlayerViewAdapter.LANDSHIPTHREESEGMENTINDEX]++;
				else if( s instanceof LandShipFourSegment )
					ret[ComputerPlayerViewAdapter.LANDSHIPFOURSEGMENTINDEX]++;
				else if( s instanceof SeaShipOneSegment )
					ret[ComputerPlayerViewAdapter.SEASHIPONESEGMENTINDEX]++;
				else if( s instanceof SeaShipTwoSegment )
					ret[ComputerPlayerViewAdapter.SEASHIPTWOSEGMENTINDEX]++;
				else if( s instanceof SeaShipThreeSegment )
					ret[ComputerPlayerViewAdapter.SEASHIPTHREESEGMENTINDEX]++;
				else if( s instanceof SeaShipFourSegment )
					ret[ComputerPlayerViewAdapter.SEASHIPFOURSEGMENTINDEX]++;
				
			}
		}
		
		return ret;

	}

	@Override
	public byte[][] getBackgroundView() {
		return background.clone();
	}
	
}
