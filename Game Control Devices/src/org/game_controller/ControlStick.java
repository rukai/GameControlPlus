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

package org.game_controller;

/**
 * A Stick combines two Sliders to a joy stick with x and y values. This class is for simpler
 * handling of sliders belonging together. For detailed controll like different
 * multipliers for the different axes of the stick work with the Sliders instead.
 * <br>
 * Note that this class is quiet experimental as it is 
 * only tested with my joypad and correct function extremely
 * depends on the order of the sliders for a device. 
 * If the getStick ( ) function of ControllDevice gives you
 * wrong sticks, you can initialize your own by giving it your
 * own sliders.
 * @example procontrol_stick
 * @related ControllDevice
 * @related ControllSlider
 * @usage application
 */
public class ControlStick {
	
	/**
	 * The Slider for the x movement of the stick 
	 */
	private final ControlSlider xSlider;
	
	/**
	 * The Slider for the y movement of the stick 
	 */	
	private final ControlSlider ySlider;
	
	/**
	 * Initializes a new Stick by two given Sliders
	 * @param i_xSlider ControllSlider, the slider for the x axis
	 * @param i_ySlider ControllSlider, the slider for the y axis
	 */
	public ControlStick(final ControlSlider i_xSlider, final ControlSlider i_ySlider){
		xSlider = i_xSlider;
		ySlider = i_ySlider;
	}
	
	/**
	 * Returns the name of the stick. The sticks name is the combination
	 * of the names of its sliders.
	 * @return String, the name of the stick
	 * @related ControllStick
	 * @usage application
	 */
	public String getName(){
		return xSlider.getName() + " " + ySlider.getName();
	}
	
	/**
	 * The current x value of the stick.
	 * @return float, the x value of the stick
	 * @example procontrol_stick
	 * @related ControllStick
	 * @related getY ( )
	 * @usage application
	 */
	public float getX(){
		return xSlider.getValue();
	}
	
	/**
	 * The current y value of the stick.
	 * @return float, the y value of the stick
	 * @example procontrol_stick
	 * @related ControllStick
	 * @related getX ( )
	 * @usage application
	 */
	public float getY(){
		return ySlider.getValue();
	}
	
	/**
	 * For the total value the actual values for each frame are add.
	 * Use this method to get the total x value of a stick.
	 * @return float, the total x value of stick
	 * @shortdesc Use this method to get the total x value of a stick.
	 * @example procontrol_getTotalValue_stick
	 * @related ControllStick
	 * @related getTotalY ( )
	 * @related reset ( )
	 * @usage application
	 */
	public float getTotalX(){
		return xSlider.getTotalValue();
	}
	
	/**
	 * For the total value the actual values for each frame are add.
	 * Use this method to get the total y value of a stick.
	 * @return float, the total y value of stick
	 * @shortdesc Use this method to get the total y value of a stick.
	 * @example procontrol_getTotalValue_stick
	 * @related ControllStick
	 * @related getTotalX ( )
	 * @related reset ( )
	 * @usage application
	 */
	public float getTotalY(){
		return ySlider.getTotalValue();
	}
	
	/**
	 * For the total value the values for each frame are add.
	 * Use this method to set the totalvalue to 0.
	 * @shortdesc Use this method to set the totalvalue to 0.
	 * @example procontrol_getTotalValue_stick
	 * @related ControllStick
	 * @related getTotalX ( )
	 * @related getTotalY ( )
	 * @usage application
	 */
	public void reset(){
		xSlider.reset();
		ySlider.reset();
	}
	
	/**
	 * If you not want a stick to react upto a certain value you can set 
	 * a tolerance value. Use this method to retrieve the set tolerance.
	 * By default this value is set to 0.
	 * @return float, the tolerance value for a stick
	 * @shortdesc Use this method to get the tolerance value.
	 * @example procontrol_multiplier_stick
	 * @related ControllStick
	 * @related setTolerance ( )
	 * @usage application
	 */
	public float getTolerance(){
		return xSlider.getTolerance();
	}
	
	/**
	 * If you not want a stick to react upto a certain value you can set 
	 * a tolerance value. Use this method to set the tolerance.
	 * By default this value is set to 0.
	 * @param i_tolerance float
	 * @shortdesc Use this method to set the tolerance.
	 * @example procontrol_getTotalValue_stick
	 * @related ControllStick
	 * @usage application
	 */
	public void setTolerance(final float i_tolerance){
		xSlider.setTolerance(i_tolerance);
		ySlider.setTolerance(i_tolerance);
	}
	
	/**
	 * The value of a slider is a relative value between
	 * -1.0f und 1.0f with the multiplier you can increase 
	 * and decrese this range. Use this method to get the
	 * actual multiplier. By default this value is 1.0.
	 * @return float, the multiplier for the stick
	 * @shortdesc Use this method to get the actual multiplier.
	 * @example procontrol_multiplier_stick
	 * @related ControllStick
	 * @related setMultiplier ( )
	 * @usage application
	 */
	public float getMultiplier(){
		return xSlider.getMultiplier();
	}
	
	/**
	 * The value of a stick is a relative value between
	 * -1.0f and 1.0f with the multiplier you can increase 
	 * and decrease this range. Use this method to set the
	 * actual multiplier. By default this value is 1.0.
	 * @shortdesc Use this method to set the actual multiplier.
	 * @example procontrol_multiplier_stick
	 * @related ControllStick
	 * @related getMultiplier ( )
	 * @usage application
	 */
	public void setMultiplier(final float i_multiplier){
		xSlider.setMultiplier(i_multiplier);
		ySlider.setMultiplier(i_multiplier);
	}
}
