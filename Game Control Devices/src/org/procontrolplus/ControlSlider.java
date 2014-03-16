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
import processing.core.PApplet;

/**
 * The slider class is for analog input elements having a value
 * range. Normally this range goes from -1 to 1. You can set
 * a multiplier to increase this range, this is usefull so
 * that you do not have to change the values in your application.
 * You can get the actual value and the total value of a slider.
 * The actual value gives you the current state of the controller.
 * For the total value the actual values for each frame are add.
 * If you not want a slider to react upto a certain value you can set 
 * a tolerance value.
 * @example procontrol
 * @usage application
 * @related ControllDevice
 * @related ControllButton
 */
public class ControlSlider extends ControlInput{

	
	/**
	 * The total Value of the slider
	 */
	protected float totalValue = 0f;

	/**
	 * Initializes a new Slider.
	 * @param i_component
	 */
	ControlSlider(final Component i_component){
		super(i_component);
		inputType = SLIDER_TYPE;
	}
	
	/**
	 * For the total value the values for each frame are add.
	 * Use this method to get the total value of a slider.
	 * @return float, the total value of a slider
	 * @example procontrol_getTotalValue
	 * @usage application
	 * @shortdesc Use this method to get the total value of a slider.
	 * @related ControllSlider
	 * @related getValue ( )
	 * @related reset ( )
	 */
	public float getTotalValue(){
		return totalValue;
	}
	
	/**
	 * For the total value the actual values for each frame are add.
	 * Use this method to set the totalvalue to 0.
	 * @example procontrol_getTotalValue
	 * @usage application
	 * @shortdesc Use this method to set the totalvalue to 0.
	 * @related ControllSlider
	 * @related getTotalValue ( )
	 */
	public void reset(){
		totalValue = 0;
	}
	
	/**
	 * Use this method to see if a slider is relative. A relative sliders
	 * value represents always the change between the current state and the last state.
	 * @return boolean, true if the slider is relative
	 */
	public boolean isRelative(){
		return component.isRelative();
	}
	
	/**
	 * This method is called before each frame to update the slider values.
	 */
	void update(){
		actualValue = component.getPollData();
		if(PApplet.abs(actualValue) < component.getDeadZone()+tolerance){
			actualValue = 0f;
		}else{
			actualValue = component.getPollData()*multiplier;
		}
		totalValue += actualValue;
	}
	
	void updateRelative(){
	}
}
