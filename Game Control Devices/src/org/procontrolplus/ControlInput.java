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

import net.java.games.input.Component;

/**
 * Base class for input elements of a controller.
 * @invisible
 */
public abstract class ControlInput implements GCConstants {
	/**
	 * The current state of the input
	 */
	protected float actualValue = 0f;
	
	/**
	 * JInput Component representing this Slider
	 */
	final Component component;
	
	/**
	 * The name of the Input component provided by JInput. This 
	 * maybe different for different OS.
	 */
	private final String actualName;
	
	/**
	 * One of BUTTON_TYPE, HAT_TYPE or SLIDER_TYPE
	 */
	protected int inputType;
	
	/**
	 * Initializes a new Slider.
	 * @param i_component
	 */
	ControlInput(final Component i_component){
		component = i_component;
		actualName = component.getName();
	}
	
	/**
	 * Returns the name of the input.
	 * @return String, the name of the input element
	 * @usage application
	 */
	public String getName(){
		return actualName;
	}

	/**
	 * Gives you the current value of an input.
	 * @return float, the actual value of the slider
	 * @example procontrol
	 * @usage application
	 * @related ControllSlider
	 * @related getTotalValue ( )
	 */
	public float getValue(){
		return actualValue;
	}
	
	/**
	 * This method is called before each frame to update the slider values.
	 */
	abstract void update();
	
}
