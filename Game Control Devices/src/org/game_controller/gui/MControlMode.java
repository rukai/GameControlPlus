package org.game_controller.gui;

import processing.core.PApplet;
import processing.core.PConstants;

public enum MControlMode implements PConstants{

	CORNER 		( "X Y W H coordinates", 		"CORNER",	PApplet.CORNER	),
	CORNERS 	( "X0 Y0 X1 Y1 coordinates", 	"CORNERS",	PApplet.CORNERS	),
	CENTER		( "X Y W H coordinates",		"CENTER",	PApplet.CENTER	);

	
	public final String description;
	public final String ps_name;
	public final int mode;
	
	private MControlMode(String desc, String name, int ctrl_mode ){
		description = desc;
		ps_name = name;
		mode = ctrl_mode;
	}
	
	public String toString(){
		return description;
	}
}
