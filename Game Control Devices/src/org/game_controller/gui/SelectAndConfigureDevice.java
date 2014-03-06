package org.game_controller.gui;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.game_controller.ControlDevice;
import org.game_controller.ControlIO;

import processing.core.PApplet;

public class SelectAndConfigureDevice extends PApplet {

	ControlIO controlIO;
	MLabel lblControls; 

	List<UControlDeviceEntry> deviceEntries =  new ArrayList<UControlDeviceEntry>();

	public void setup(){
		size(500, 400, JAVA2D);
		M4P.messagesEnabled(false);                                                                             
		M4P.setGlobalColorScheme(MCScheme.RED_SCHEME);
		if(frame != null)
			frame.setTitle("Select Game Device");
		registerMethod("dispose", this);
		controlIO = ControlIO.getInstance(this);
		createSelectionInterface();
		List<ControlDevice> devices = controlIO.getDevices();
		// Add entries for devices added
		for(ControlDevice d : devices){
			if(d.available && !d.getTypeName().equalsIgnoreCase("keyboard"))
				deviceEntries.add(new UControlDeviceEntry(this, controlIO, d));
		}
		// Sort entries and reposition on screen
		Collections.sort(deviceEntries);
		for(int i = 0; i < deviceEntries.size(); i++)
			deviceEntries.get(i).setIndex(i);
	}

	public void createSelectionInterface(){
		lblControls = new MLabel(this, 0, 0, width, 20);
		lblControls.setText("USB Controls");
		lblControls.setOpaque(true);
		lblControls.setTextBold();
	}

	public void dispose(){
		System.out.println("Disposing");
		for(UControlDeviceEntry entry : deviceEntries){
			if(entry.winCofig != null){
				entry.winCofig.close();
				entry.winCofig = null;
			}
		}
	}

	public void draw(){
		background(255, 255, 220);
		stroke(230, 230, 200);
		fill(240, 240, 210);
		int y = 40;
		while(y < height){
			rect(0,y,width,20);
			y += 40;
		}
	}


}
