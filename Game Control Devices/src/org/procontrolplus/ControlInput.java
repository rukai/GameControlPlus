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
	 * Included for all inputs for convenience but only used
	 * by the ControlSlider. <br>
	 * Tolerance is minimum under which the input is set to
	 * zero.
	 */
	protected float tolerance = 0f;
	
	/**
	 * Included for all inputs for convenience but only used
	 * by the ControlSlider and ControlCoolieHat classes. <br>
	 * The value of a slider is a relative value between
	 * -1.0f und 1.0f with the multiplier you can increase 
	 * and decrease this range.
	 */
	protected float multiplier = 1f;
		

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
	 * This value has no significance except for the ControlSlider class.
	 */
	public float getTolerance(){
		return tolerance;
	}
	
	/**
	 * Tolerance is only used by the ControlSlider class but is placed 
	 * here for convenience of use. <br>
	 * If you not want a slider to react upto a certain value you can set 
	 * a tolerance value. Use this method to set the tolerance.
	 * By default this value is set to 0.
	 * @param i_tolerance float, the new tolerance for the slider
	 */
	public void setTolerance(final float i_tolerance){
		tolerance = i_tolerance;
	}
	
	/**
	 * This value has no significance except for the ControlSlider and
	 * ControlCoolieHat classes.
	 */
	public float getMultiplier(){
		return multiplier;
	}
	
	/**
	 * Multiplier is only used by the ControlSlider and ControlCooloieHat
	 * classes but is placed here for convenience of use. <br>
	 * The value of a slider, and the X and Y values of a coolie-hat normally 
	 * have a value in the range -1.0f and 1.0f. <br>
	 * You can change this range by setting a multiplier with a value not 
	 * equal to 1. <br>
	 * 
	 * By default the multiplier is 1.0.
	 * @param i_multiplier float, the new multiplier for a Slider or CoolieHat
	 */
	public void setMultiplier(final float i_multiplier){
		multiplier = i_multiplier;
	}

	
	/**
	 * This method is called before each frame to update the slider values.
	 */
	abstract void update();
	
}
