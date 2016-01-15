package main.pkg;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GraphicLoader {
	
	public static final int WATERIMAGE = 0;
	public static final int GRASSIMAGE = 1;
	
	public static final int AIRSHIPIMAGE = 2;
	public static final int LANDSHIPTWOSEGMENTIMAGE = 3;
	public static final int LANDSHIPTHREESEGMENTIMAGE = 4;
	public static final int LANDSHIPFOURSEGMENTIMAGE = 5;
	public static final int SEASHIPONESEGMENTIMAGE = 6;
	public static final int SEASHIPTWOSEGMENTIMAGE = 7;
	public static final int SEASHIPTHREESEGMENTIMAGE = 8;
	public static final int SEASHIPFOURSEGMENTIMAGE = 9;
	
	public static final int AIRSHIPIMAGE90 = 10;
	public static final int LANDSHIPTWOSEGMENTIMAGE90 = 11;
	public static final int LANDSHIPTHREESEGMENTIMAGE90 = 12;
	public static final int LANDSHIPFOURSEGMENTIMAGE90 = 13;
	public static final int SEASHIPONESEGMENTIMAGE90 = 14;
	public static final int SEASHIPTWOSEGMENTIMAGE90 = 15;
	public static final int SEASHIPTHREESEGMENTIMAGE90 = 16;
	public static final int SEASHIPFOURSEGMENTIMAGE90 = 17;
	
	public static final int AIRSHIPIMAGE180 = 18;
	public static final int LANDSHIPTWOSEGMENTIMAGE180 = 19;
	public static final int LANDSHIPTHREESEGMENTIMAGE180 = 20;
	public static final int LANDSHIPFOURSEGMENTIMAGE180 = 21;
	public static final int SEASHIPONESEGMENTIMAGE180 = 22;
	public static final int SEASHIPTWOSEGMENTIMAGE180 = 23;
	public static final int SEASHIPTHREESEGMENTIMAGE180 = 24;
	public static final int SEASHIPFOURSEGMENTIMAGE180 = 25;
	
	public static final int AIRSHIPIMAGE270 = 26;
	public static final int LANDSHIPTWOSEGMENTIMAGE270 = 27;
	public static final int LANDSHIPTHREESEGMENTIMAGE270 = 28;
	public static final int LANDSHIPFOURSEGMENTIMAGE270 = 29;
	public static final int SEASHIPONESEGMENTIMAGE270 = 30;
	public static final int SEASHIPTWOSEGMENTIMAGE270 = 31;
	public static final int SEASHIPTHREESEGMENTIMAGE270 = 32;
	public static final int SEASHIPFOURSEGMENTIMAGE270 = 33;
	
	public static final int SMALLAIRSHIPIMAGE = 34;
	public static final int SMALLLANDSHIPTWOSEGMENTIMAGE = 35;
	public static final int SMALLLANDSHIPTHREESEGMENTIMAGE = 36;
	public static final int SMALLLANDSHIPFOURSEGMENTIMAGE = 37;
	public static final int SMALLSEASHIPONESEGMENTIMAGE = 38;
	public static final int SMALLSEASHIPTWOSEGMENTIMAGE = 39;
	public static final int SMALLSEASHIPTHREESEGMENTIMAGE = 40;
	public static final int SMALLSEASHIPFOURSEGMENTIMAGE = 41;
	
	private static GraphicLoader instance;
	
	private Image[] image;
	
	private GraphicLoader(){
		image = new Image[42];
	}
	
	public void load(){
		try {
			image[WATERIMAGE] = ImageIO.read(new File("image/water.png"));
			image[GRASSIMAGE] = ImageIO.read(new File("image/grass.png"));
			
			image[AIRSHIPIMAGE] = ImageIO.read(new File("image/airship.png"));
			image[LANDSHIPTWOSEGMENTIMAGE] = ImageIO.read(new File("image/landship2.png"));
			image[LANDSHIPTHREESEGMENTIMAGE] = ImageIO.read(new File("image/landship3.png"));
			image[LANDSHIPFOURSEGMENTIMAGE] = ImageIO.read(new File("image/landship4.png"));
			image[SEASHIPONESEGMENTIMAGE] = ImageIO.read(new File("image/seaship1.png"));
			image[SEASHIPTWOSEGMENTIMAGE] = ImageIO.read(new File("image/seaship2.png"));
			image[SEASHIPTHREESEGMENTIMAGE] = ImageIO.read(new File("image/seaship3.png"));
			image[SEASHIPFOURSEGMENTIMAGE] = ImageIO.read(new File("image/seaship4.png"));
			image[SMALLAIRSHIPIMAGE] = ImageIO.read(new File("image/small_airship.png"));
			
			image[AIRSHIPIMAGE90] = ImageIO.read(new File("image/airship_90.png"));
			image[LANDSHIPTWOSEGMENTIMAGE90] = ImageIO.read(new File("image/landship2_90.png"));
			image[LANDSHIPTHREESEGMENTIMAGE90] = ImageIO.read(new File("image/landship3_90.png"));
			image[LANDSHIPFOURSEGMENTIMAGE90] = ImageIO.read(new File("image/landship4_90.png"));
			image[SEASHIPONESEGMENTIMAGE90] = ImageIO.read(new File("image/seaship1_90.png"));
			image[SEASHIPTWOSEGMENTIMAGE90] = ImageIO.read(new File("image/seaship2_90.png"));
			image[SEASHIPTHREESEGMENTIMAGE90] = ImageIO.read(new File("image/seaship3_90.png"));
			image[SEASHIPFOURSEGMENTIMAGE90] = ImageIO.read(new File("image/seaship4_90.png"));
			
			image[AIRSHIPIMAGE180] = ImageIO.read(new File("image/airship_180.png"));
			image[LANDSHIPTWOSEGMENTIMAGE180] = ImageIO.read(new File("image/landship2_180.png"));
			image[LANDSHIPTHREESEGMENTIMAGE180] = ImageIO.read(new File("image/landship3_180.png"));
			image[LANDSHIPFOURSEGMENTIMAGE180] = ImageIO.read(new File("image/landship4_180.png"));
			image[SEASHIPONESEGMENTIMAGE180] = ImageIO.read(new File("image/seaship1_180.png"));
			image[SEASHIPTWOSEGMENTIMAGE180] = ImageIO.read(new File("image/seaship2_180.png"));
			image[SEASHIPTHREESEGMENTIMAGE180] = ImageIO.read(new File("image/seaship3_180.png"));
			image[SEASHIPFOURSEGMENTIMAGE180] = ImageIO.read(new File("image/seaship4_180.png"));

			image[AIRSHIPIMAGE270] = ImageIO.read(new File("image/airship_270.png"));
			image[LANDSHIPTWOSEGMENTIMAGE270] = ImageIO.read(new File("image/landship2_270.png"));
			image[LANDSHIPTHREESEGMENTIMAGE270] = ImageIO.read(new File("image/landship3_270.png"));
			image[LANDSHIPFOURSEGMENTIMAGE270] = ImageIO.read(new File("image/landship4_270.png"));
			image[SEASHIPONESEGMENTIMAGE270] = ImageIO.read(new File("image/seaship1_270.png"));
			image[SEASHIPTWOSEGMENTIMAGE270] = ImageIO.read(new File("image/seaship2_270.png"));
			image[SEASHIPTHREESEGMENTIMAGE270] = ImageIO.read(new File("image/seaship3_270.png"));
			image[SEASHIPFOURSEGMENTIMAGE270] = ImageIO.read(new File("image/seaship4_270.png"));
			
			image[SMALLAIRSHIPIMAGE] = ImageIO.read(new File("image/small_airship.png"));
			image[SMALLLANDSHIPTWOSEGMENTIMAGE] = ImageIO.read(new File("image/small_landship2.png"));
			image[SMALLLANDSHIPTHREESEGMENTIMAGE] = ImageIO.read(new File("image/small_landship3.png"));
			image[SMALLLANDSHIPFOURSEGMENTIMAGE] = ImageIO.read(new File("image/small_landship4.png"));
			image[SMALLSEASHIPONESEGMENTIMAGE] = ImageIO.read(new File("image/small_seaship1.png"));
			image[SMALLSEASHIPTWOSEGMENTIMAGE] = ImageIO.read(new File("image/small_seaship2.png"));
			image[SMALLSEASHIPTHREESEGMENTIMAGE] = ImageIO.read(new File("image/small_seaship3.png"));
			image[SMALLSEASHIPFOURSEGMENTIMAGE] = ImageIO.read(new File("image/small_seaship4.png"));
		} catch (IOException e) {
			//error message
		}
	}
	
	public Image getImage(int index){
		if(index < 0 || index >= image.length) return null;
		return image[index];
	}
	
	public static GraphicLoader getInstance(){
		if(instance == null) instance = new GraphicLoader();
		return instance;
	}
	
}
