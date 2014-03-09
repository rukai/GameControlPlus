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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.game_controller.Configuration.InputConfig;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.Rumbler;
import processing.core.PApplet;

/**
 * <p>
 * The device class is for the communication with your input devices. A device
 * consists of buttons and sliders, sliders can be grouped to sticks.
 * </p>
 * <p>
 * To react on button events you can plug methods, that are called when
 * a button is pressed, released or while a button is pressed.
 * </p>
 * @example procontrol
 * @related ControllIO
 * @related ControllSlider
 * @related ControllButton
 * @related ControllStick
 */
public class ControlDevice implements Comparable<ControlDevice>, GCConstants {

	/**
	 * The JInput controller instance for this device
	 */
	private final Controller controller;

	/**
	 * Instance to the PApplet where procontrol is running
	 */
	private final PApplet parent;

	/**
	 * This is used to show the device is available for selection by matching with
	 * a configurations file.
	 */
	public boolean available = true;
	
	/**
	 * list containing the sliders on the device
	 */
	private final List<ControlSlider> sliders = new ArrayList<ControlSlider>();

	/**
	 * list containing the buttons on the device
	 */
	private final List<ControlButton> buttons = new ArrayList<ControlButton>();

	/**
	 * list containing the rumblers on the device
	 */
	private Rumbler[] rumblers = new Rumbler[0];

	/**
	 * to map the device generated input names and Controller inputs
	 */
	private final Map<String, ControlInput> inputNameMap = 
			new HashMap<String, ControlInput>();

	/**
	 * A List with the buttons and sliders available by the device
	 */
	private final List<ControlInput> inputs = new ArrayList<ControlInput>();

	/**
	 * true if the device has been opened. Only opened devices are updated before
	 * a frame.
	 */
	private boolean open = false;

	/**
	 * The name of the device.
	 */
	private final String name;
	
	/**
	 * Initializes a new device by the given Controller
	 * 
	 * @param i_controller
	 */
	ControlDevice(final Controller i_controller, final PApplet i_parent){
		controller = i_controller;
		parent = i_parent;
		name = i_controller.getName();
		setupDevice();
	}

	/**
	 * Loads the available Sliders, Sticks and Buttons for a device
	 */
	private void setupDevice(){
		final Component[] components = controller.getComponents();
		// Now identify all buttons and sliders
		ControlInput input = null;
		for (int i = 0; i < components.length; i++){
			input = null;
			if(components[i].isAnalog()){
				if(components[i].isRelative()){
					input = new ControlRelativeSlider(components[i]);
				}else{
					input = new ControlSlider(components[i]);
				}
				sliders.add((ControlSlider)input);
			} else {
				if(components[i].getIdentifier() == Component.Identifier.Axis.POV){
					input = new ControlCoolieHat(components[i],parent);
				}else{
					input = new ControlButton(components[i],parent);
				}
				buttons.add((ControlButton)input);
			} 
			if(input != null){
				inputNameMap.put(input.getName(), input);
			}
		}  
		inputs.addAll(sliders);
		inputs.addAll(buttons);
		rumblers = controller.getRumblers();
	}

	/**
	 * If available will attempt to match this device to the required configuration.
	 * If a match occurs then <br>
	 * <ol>
	 * <li>the user defined keys are added to the device </li>
	 * <li>the config count is incremented </li>
	 * <li>the device is marked as unavailable for future matches </li>
	 * <li> the device is opened </li>
	 * </ol>
	 * @param config
	 * @return
	 */
	public boolean matches(Configuration config){
		Map<String, ControlInput> matchedInputs = 
				new HashMap<String, ControlInput>();
		for(InputConfig ic : config.gameInputs){
			// Get the input with the given device name
			ControlInput input = inputNameMap.get(ic.deviceInputName);
			// If it is the same type remember it
			if(input != null && input.inputType == ic.type)
				matchedInputs.put(ic.key, input);
			else 
				return false;
		}
		// Match found
		inputNameMap.putAll(matchedInputs);
		available = false;
		open();
		return true;
	}
	
	/**
	 * Returns the name of the device.
	 * @return String, the name of a device
	 * @example procontrol_printDevices
	 * @related ControllDevice
	 */
	public String getName(){
		return name;
	}

	/**
	 * Get the name for the type of device e.g. Gamepad, Mouse
	 * @return
	 */
	public String getTypeName(){
		return controller.getType().toString();
	}

	/**
	 * Get the name of the connection type e.g. USB, network etc.
	 */
	public String getPortTypeName(){
		return controller.getPortType().toString();
	}

	/**
	 * Returns the String representation of a device
	 * @return String, the String representation of a device
	 * @invisible
	 */
	public String toString(){
		return controller.getName();
	}

	/**
	 * This method is called on every thread loop 
	 */
	public void update(){
		if(open){
			controller.poll();
			for (int i = 0; i < inputs.size(); i++)
				inputs.get(i).update();
		}
	}

	/**
	 * This method is called before each frame to update the controller values
	 */
	protected void updateRelative(){
		if(open){
			for (int i = 0; i < sliders.size(); i++)
				sliders.get(i).updateRelative();
		}
	}

	/**
	 * Lists the available slider of a device in the console window. This method
	 * is useful at startup to get the name of the different sliders.
	 * @shortdesc Lists the available sliders of a device.
	 * @example procontrol_printDevices
	 * @related ControllDevice
	 * @related ControllSlider
	 * @related printButtons ( )
	 * @related printSticks ( )
	 */
	public void printSliders(){
		if(sliders.size() > 0){
			System.out.println("\n\t<<< Available sliders for "+ name + " >>>\n");
			for (int i = 0; i < sliders.size(); i++){
				ControlSlider slider = sliders.get(i);
				System.out.print("\t" + i + ":\t");
				System.out.print("'" + slider.getName() + "'");
				if(slider.isRelative()){
					System.out.println(" (relative)");
				}else{
					System.out.println(" (absolute)");
				}
			}
			System.out.println();
		}
	}

	/**
	 * Lists the available button of a device in the console window. This method
	 * is usefull at startup to get the name of the different buttons.
	 * @shortdesc Lists the available buttons of a device.
	 * @example procontrol_printDevices
	 * @related ControllDevice
	 * @related ControllButton
	 * @related printSliders ( )
	 * @related printSticks ( )
	 */
	public void printButtons(){
		if(buttons.size() > 0){
			System.out.println("\n\t<<< Available buttons for "+ name + " >>>\n");
			for (int i = 0; i < buttons.size(); i++){
				System.out.print("\t" + i + ":\t");
				System.out.println("'" + buttons.get(i).getName() + "'");
			}
			System.out.println();
		}
	}
	
	public List<ControlInput> getInputs(){
		return inputs;
	}

	/**
	 * Returns the number of sliders of the device.
	 * @return int, the number of sliders available for a device
	 * @example procontrol_printDevices
	 * @related ControllDevice
	 * @related ControllSlider
	 * @related getNumberOfButtons ( )
	 * @related getNumberOfSticks ( )
	 * @related getSlider ( )
	 */
	public int getNumberOfSliders(){
		return sliders.size();
	}

	/**
	 * Use this method to get a Slider. You can get a slider by its name or its
	 * number. Use printSliders to see what sliders are available for a device.
	 * @param i_sliderNumb int, the number of the slider to return
	 * @return ControllSlider, the Slider coresponding to the given number or String
	 * @example procontrol
	 * @shortdesc Use this method to get a Slider.
	 * @related ControllDevice
	 * @related ControllSlider
	 * @related getNumberOfSliders ( )
	 * @related getButton ( )
	 * @related getStick ( )
	 */ 
	public ControlSlider getSlider(final int i_sliderNumb){ 
		return (ControlSlider)sliders.get(i_sliderNumb); 
	} 

	/**
	 * @param i_sliderName String, name of the slider to return
	 */
	public ControlSlider getSlider(final String i_sliderName){
		try{
			return(ControlSlider)inputNameMap.get(i_sliderName);
		}catch (ClassCastException e){
		}

		throw new RuntimeException("There is no slider with the name " + i_sliderName + ".");
	}

	/**
	 * Tolerance is minimum under which the input is set to zero.
	 * Use this method to set the tolerance for all sliders of the device.
	 * @param i_tolerance float, the new tolerance for the device
	 * @example procontrol
	 * @shortdesc Use this method to set the tolerance for all sliders of the device.
	 * @related ControllDevice
	 * @related ControllSlider
	 */
	public void setTolerance(final float i_tolerance){
		for (int i = 0; i < sliders.size(); i++)
			((ControlSlider) sliders.get(i)).setTolerance(i_tolerance);
		;
	}

	/**
	 * Returns the number of buttons of the device.
	 * @return int, the number of buttons available for a device
	 * @example procontrol_printDevices
	 * @related ControllDevice
	 * @related ControllButton
	 * @related getNumberOfSliders ( )
	 * @related getNumberOfSticks ( )
	 * @related getButton ( )
	 */
	public int getNumberOfButtons(){
		return buttons.size();
	}

	/**
	 * Use this method to get a Button. You can get a Button by its name or its
	 * number. Use printButtons to see what buttons are available for a device.
	 * @param i_buttonNumb int, the number of the button to return
	 * @return ControllButton, the button coresponding to the given number or name
	 * @example procontrol
	 * @shortdesc Use this method to get a Button.
	 * @related ControllDevice
	 * @related ControllButton
	 * @related getSlider ( )
	 * @related getStick ( )
	 * @related getCoolieHat ( )
	 */ 
	public ControlButton getButton(final int i_buttonNumb){ 
		return buttons.get(i_buttonNumb); 
	} 

	/**
	 *  @param i_sliderName String, name of the button to return
	 */
	public ControlButton getButton(final String i_buttonName){
		try{
			return (ControlButton)inputNameMap.get(i_buttonName);
		}catch (ClassCastException e){
		}
		throw new RuntimeException("There is no button with the name " + i_buttonName + ".");
	}

	/**
	 * Use this method to get a cooliehat. You can get a cooliehat by its name or its
	 * number. Use printButtons to see what buttons are a cooliehat.
	 * @param i_coolieHatNumb int, the number of the cooliehat to return
	 * @return ControllCoolieHat, the cooliehat coresponding to the given number or name
	 * @example procontrol_cooliehat
	 * @shortdesc Use this method to get a Button.
	 * @related ControllDevice
	 * @related ControllCoolieHat
	 * @related getSlider ( )
	 * @related getStick ( )
	 * @related getButton ( )
	 */ 
	public ControlCoolieHat getCoolieHat(final int i_coolieHatNumb){ 
		return (ControlCoolieHat)buttons.get(i_coolieHatNumb); 
	} 

	/**
	 *  @param i_buttonName String, name of the button to return
	 */
	public ControlCoolieHat getCoolieHat(final String i_buttonName){
		try{
			return(ControlCoolieHat)inputNameMap.get(i_buttonName);
		}catch (ClassCastException e){
		}
		throw new RuntimeException("There is no button with the name " + i_buttonName + ".");
	}

	/**
	 * Use this method to open a device. A device is automatically opened by
	 * default, so you only need to call this when you have closed it with the
	 * close method.
	 * @shortdesc Use this method to open a device.
	 * @related ControllDevice
	 */
	public void open(){
		open = true;
	}

	/**
	 * Use this method to close a device. A closed device does not to be updated
	 * to get values.
	 * @shortdesc Use this method to close a device.
	 * @related ControllDevice
	 */
	public void close(){
		open = false;
	}

	public int getNumberOfRumblers(){
		return rumblers.length;
	}

	public void rumble(final float i_intensity, final int i_id){
		if(i_id >= rumblers.length) return;
		else rumblers[i_id].rumble(i_intensity);
	}

	public void rumble(final float i_intensity){
		rumble(i_intensity,0);
	}

	/**
	 * Plug is a handy method to handle incoming button events. To create a plug
	 * you have to implement a method that reacts on the events. To plug a method you
	 * need to give a device the method name, the event type you want to react on and
	 * the button. If your method is inside a class you have to give the plug
	 * a reference to it.
	 * @param i_object Object: the object with the method to plug
	 * @param i_methodName String: the name of the method that has to be plugged
	 * @param i_eventType constant: can be ControllIO.ON_PRESS, ControllIO.ON_RELEASE or ControllIO.WHILE_PRESS
	 * @param i_input int: the number of the button that triggers the plug
	 * @shortdesc Plugs a method to handle incoming button events.
	 */
	public void plug(
			final Object i_object, 
			final String i_methodName, 
			final int i_eventType,
			final int i_input
			){
		open();
		getButton(i_input).plug(i_object,i_methodName,i_eventType);
	}

	public void plug(
			final String i_methodName, 
			final int i_eventType,
			final int i_input
			){
		open();
		getButton(i_input).plug(parent,i_methodName,i_eventType);
	}

	/**
	 * 
	 * i_intputDevice String: the name of the device that triggers the plug
	 */
	public void plug(
			final Object i_object, 
			final String i_methodName, 
			final int i_eventType,
			final String i_input
			){
		open();
		getButton(i_input).plug(i_object,i_methodName,i_eventType);
	}

	public void plug(
			final String i_methodName, 
			final int i_eventType,
			final String i_input
			){
		open();
		getButton(i_input).plug(parent,i_methodName,i_eventType);
	}

	public boolean equals(Object d){
		boolean result = compareTo((ControlDevice)d) == 0;
		return result;
	}
	
	@Override
	public int compareTo(ControlDevice d) {
		String s = name + ((Integer)buttons.size()).toString() + ((Integer)sliders.size()).toString() + ((Integer)rumblers.length).toString(); 
		String ds = d.name + ((Integer)d.buttons.size()).toString() + ((Integer)d.sliders.size()).toString() + ((Integer)d.rumblers.length).toString(); 
		int result = s.compareToIgnoreCase(ds);
        return result;
	}
}
