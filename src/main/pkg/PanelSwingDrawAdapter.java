package main.pkg;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public interface PanelSwingDrawAdapter {
	
	public void draw(Graphics g, int offsetx, int offsety, int panelwidth, int panelheight);
	
	public BufferedImage prepareBackgroung();
	
}
