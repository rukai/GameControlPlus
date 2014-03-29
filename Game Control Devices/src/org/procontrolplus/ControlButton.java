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

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

import net.java.games.input.Component;

/**
 * This class represents a button of a device. You can use the pressed() 
 * method to see if a button is pressed or use the plug method to 
 * handle events.
 * @example procontrol
 * @usage application
 * @related ControllSlider
 * @related ControllStick
 * @related ControllDevice
 */
public class ControlButton extends ControlInput{
	
	private boolean pressed = false;
	private boolean oldPressed = false;
	
	/**
	 * Instance to the PApplet where procontrol is running
	 */
	private final PApplet parent;
		
	/**
	 * Initializes a new Slider.
	 * @param i_component
	 */
	ControlButton(final Component i_component, final PApplet i_parent){
		super(i_component);
		parent = i_parent;
		inputType = BUTTON_TYPE;
	}
	
	/**
	 * This method is called before each frame to update the button state.
	 */
	void update(){
		actualValue = component.getPollData()*8;
		pressed = actualValue>0f;
		
		if(pressed && oldPressed){
			callPlugs(whilePressPlugs);
		}else if(pressed && !oldPressed){
			callPlugs(onPressPlugs);
		}else if(!pressed && oldPressed){
			callPlugs(onReleasePlugs);
		}	
		oldPressed = pressed;
	}
	
	/**
	 * This method returns true if the button was pressed. 
	 * @return boolean, true if the button was pressed
	 * @usage application
	 * @related ControllButton
	 */
	public boolean pressed(){
		return pressed;
	}
	
	protected final List<Plug> onPressPlugs = new ArrayList<Plug>();
	protected final List<Plug> onReleasePlugs = new ArrayList<Plug>();
	protected final List<Plug> whilePressPlugs = new ArrayList<Plug>();
	
	/**
	 * Plug is a handy method to handle incoming button events. To create a plug
	 * you have to implement a method that reacts on the events. To plug a method you
	 * need to give a button the method name and the event type you want to react on.
	 * If your method is inside a class you have to give the plug a reference to it.
	 * @param i_object Object: the object with the method to plug
	 * @param i_methodName String: the name of the method that has to be plugged
	 * @param i_eventType constant: can be ControllIO.ON_PRESS, ControllIO.ON_RELEASE or ControllIO.WHILE_PRESS
	 * @shortdesc Plugs a method to handle incoming button events.
	 */
	public void plug(
		final Object i_object, 
		final String i_methodName,
		final int i_eventType
	){
		List<Plug> plugList;
		Plug plug = new Plug(i_object, i_methodName);
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
	
	public void plug(
		final String i_methodName,
		final int i_eventType
	){
		plug(parent, i_methodName, i_eventType);
	}
	
	protected void callPlugs(final List<Plug> i_plugList){
		for(int i = 0; i < i_plugList.size();i++){
			Plug plug = i_plugList.get(i);
			plug.call();
		}
	}
	
	public String toText(String tab){
		String name = (actualName.length() > 20 ) ?
				actualName.substring(0, 17) + "..." : (actualName + "                    ").substring(0,  20);
		return tab + "button    " + name + "-";
	}
}
