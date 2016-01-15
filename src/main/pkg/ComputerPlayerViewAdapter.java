package main.pkg;

public interface ComputerPlayerViewAdapter {
	
	public static final int LANDSHIPTWOSEGMENTINDEX = 0;
	public static final int LANDSHIPTHREESEGMENTINDEX = 1;
	public static final int LANDSHIPFOURSEGMENTINDEX = 2;
	public static final int SEASHIPONESEGMENTINDEX = 3;
	public static final int SEASHIPTWOSEGMENTINDEX = 4;
	public static final int SEASHIPTHREESEGMENTINDEX = 5;
	public static final int SEASHIPFOURSEGMENTINDEX = 6;
	public static final int AIRSHIPINDEX = 7;
	public static final int AMOUNTINDEX = 8;
	public static final int ARRAYLENGTH = 9;
	
	public byte[][] getBackgroundView();
	
	public byte[][] getShootView();
	
	public int[] getAliveShipCount();
	
}
