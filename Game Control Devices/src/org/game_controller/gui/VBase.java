package org.game_controller.gui;

import processing.core.PApplet;
import processing.core.PConstants;

public abstract class VBase implements  PConstants, VConstants {

	protected VControlConfigWindow ccw;
	protected final PApplet app;

	// DESC or INPUT
	int uiType;
	
	float UI_HEIGHT; // = ccw.desc_UI_height;
	
	float fontBaseLine;
	final float px, py;
	
	int backCol;

	final VConnector[] connectors;
	
	boolean isOver = false;
	
	VBase(VControlConfigWindow ccw, float x, float y, int nbr_connects){
		this.ccw = ccw;
		app = ccw.window.papplet;
		UI_HEIGHT = ccw.desc_UI_height;
		px = x;
		py = y;
		connectors = new VConnector[nbr_connects];
	}
	
	protected abstract void drawConnectors();

	public void update(){ }
	
	public void overWhat(float mx, float my){
		for(int cn = 0; cn < connectors.length; cn++){
			isOver = connectors[cn].isOver(ccw, mx , my);
			if(isOver) {
				break;
			}
		}
	}
	
	public abstract void draw();

}
