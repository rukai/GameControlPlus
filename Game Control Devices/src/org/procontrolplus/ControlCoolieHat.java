/*
Part of the procontrol lib - http://texone.org/procontrol

Copyright (c) 2005 Christian Riekoff

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General
Public License along with this library; if not, write to the
Free Software Foundation, Inc., 59 Temple Place, Suite 330,
Boston, MA  02111-1307  USA
*/

package org.procontrolplus;

import java.util.List;

import processing.core.PApplet;
import processing.core.PConstants;

import net.java.games.input.Component;

/**
 * A cooliehat is a special button, that can be found on joypads
 * for example. It is not only on or of but does also have a 
 * direction.
 * @example procontrol_cooliehat
 * @usage application
 * @related ControllSlider
 * @related ControllStick
 * @related ControllDevice
 */
public class ControlCoolieHat extends ControlButton {
	
	/**
    * Standard value for center HAT position
    */
    private static final int OFF = 0;
   /**
    * Standard value for up-left HAT position
    */
    private static final int UP_LEFT = 1;
   /**
    * Standard value for up HAT position
    */
    private static final int UP = 2;
   /**
    * Standard value for up-right HAT position
    */
    private static final int UP_RIGHT = 3;
    /**
    * Standard value for right HAT position
    */
    private static final int RIGHT = 4;
   /**
    * Standard value for down-right HAT position
    */
    private static final int DOWN_RIGHT = 5;
    /**
    * Standard value for down HAT position
    */
    private static final int DOWN = 6;
   /**
    * Standard value for down-left HAT position
    */
    private static final int DOWN_LEFT = 7;
    /**
    * Standard value for left HAT position
    */
    private static final int LEFT = 8;
	
	private float x = 0;
	private float y = 0;
	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	
	private static float DIAGONAL_FACTOR = (float)Math.sin(PConstants.HALF_PI/2);
	private float change = 1;
	private float diagonalChange = DIAGONAL_FACTOR;
		
	/**
	 * Initializes a new ControllCrossButton.
	 * @param i_component
	 */
	ControlCoolieHat(final Component i_component, final PApplet i_parent){
		super(i_component,i_parent);
		inputType = HAT_TYPE;
	}
	
	/**
	 * This method is called before each frame to update the button state.
	 */
	void update(){
		super.update();
		up = down = left = right = false;
		switch((int)actualValue){
			case DOWN:
				down = true;
				y = change;
				break;
			case DOWN_LEFT:
				down = true;
				left = true;
				x = -diagonalChange;
				y = diagonalChange;
				break;
			case LEFT:
				left = true;
				x = -change;
				break;
			case UP_LEFT:
				up = true;
				left = true;
				x = -diagonalChange;
				y = -diagonalChange;
				break;
			case UP:
				up = true;
				y = -change;
				break;
			case UP_RIGHT:
				up = true;
				right = true;
				x = +diagonalChange;
				y = -diagonalChange;
				break;
			case RIGHT:
				right = true;
				x = change;
				break;
			case DOWN_RIGHT:
				down = true;
				right = true;
				x = diagonalChange;
				y = diagonalChange;
				break;
			case OFF:
				x = 0;
				y = 0;
		}
	}
	
	/**
	 * Returns the name of the cooliehat .
	 * @return String, the name of the input element
	 * @usage application
	 * @related ControllCoolieHat
	 */
	public String getName(){
		return "cooliehat: " + super.getName();
	}
	
	public boolean up(){
		return up;
	}
	
	public boolean down(){
		return down;
	}
	
	public boolean left(){
		return left;
	}
	
	public boolean right(){
		return right;
	}
	
	/**
	 * The current x value of the cooliehat.
	 * @return float, the x value of the cooliehat
	 * @example procontrol_cooliehat
	 * @related ControllCoolieHat
	 * @related getY ( )
	 * @usage application
	 */
	public float getX(){
		return x;
	}
	
	/**
	 * The current y value of the cooliehat.
	 * @return float, the y value of the cooliehat
	 * @example procontrol_cooliehat
	 * @related ControllCoolieHat
	 * @related getY ( )
	 * @usage application
	 */
	public float getY(){
		return y;
	}
	
	private float multiplier = 1;
	
	/**
	 * Pressing a cooliehat causes a change by 1 in the according direction.
	 * With the multiplier you can increase and decrease this value. Use this 
	 * method to get the actual multiplier. By default this value is 1.0.
	 * @return float, the actual multiplier for the cooliehat
	 * @example procontrol_cooliehat
	 * @usage application
	 * @shortdesc Use this method to get the actual multiplier.
	 * @related ControllCoolieHat
	 * @related setMultiplier ( )
	 */
	public float getMultiplier(){
		return multiplier;
	}
	
	/**
	 * Pressing a cooliehat causes a change by 1 in the according direction.
	 * With the multiplier you can increase and decrese this range. Use this 
	 * method to set the actual multiplier. By default this value is 1.0.
	 * @param i_multiplier float, the new multiplier for a CrossButton
	 * @example procontrol_cooliehat
	 * @usage application
	 * @shortdesc Use this method to set the actual multiplier.
	 * @related ControllCoolieHat
	 * @related getMultiplier ( )
	 */
	public void setMultiplier(final float i_multiplier){
		multiplier = i_multiplier;
		change = multiplier;
		diagonalChange = DIAGONAL_FACTOR * i_multiplier;
	}
	
	/**
	 * <p>
	 * Plug is a handy method to handle incoming button events. To create a plug
	 * you have to implement a method that reacts on the events. To plug a method you
	 * need to give a button the method name and the event type you want to react on.
	 *  If your method is inside a class you have to give the plug a reference to it.
	 *  </p>
	 *  <p>
	 *  If you want to handle the events of a simple button, you only have to implement a
	 * method without parameters. To react on the events of a cooliehat you method needs to
	 * receive two float values, so that procontrol can send you the x and y values of the
	 * cooliehat.
	 *  </p>
	 * @param i_object Object: the object with the method to plug
	 * @param i_methodName String: the name of the method that has to be plugged
	 * @param i_eventType constant: can be ControllIO.ON_PRESS, ControllIO.ON_RELEASE or ControllIO.WHILE_PRESS
	 * @shortdesc Plugs a method to handle incoming button events.
	 * @related ControllCoolieHat
	 */
	public void plug(
		final Object i_object, 
		final String i_methodName,
		final int i_eventType
	){
		List plugList;
		Plug plug = new Plug(i_object,i_methodName,true);
		switch(i_eventType){
			case ControlIO.ON_PRESS:
				plugList = onPressPlugs;
				break;
			case ControlIO.ON_RELEASE:
				plugList = onReleasePlugs;
				break;	
			case ControlIO.WHILE_PRESS:
				plugList = whilePressPlugs;
				break;
			default:
				throw new RuntimeException("Error on plug "+i_methodName+" check the given event type");
		}		
		plugList.add(plug);
	}
	
	protected void callPlugs(final List i_plugList){
		for(int i = 0; i < i_plugList.size();i++){
			Plug plug = (Plug)i_plugList.get(i);
			plug.call(x,y);
		}
	}
}
