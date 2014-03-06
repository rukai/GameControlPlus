package org.game_controller.gui;

//import g4p_controls.GAlign;
//import g4p_controls.GButton;
//import g4p_controls.GEvent;
//import g4p_controls.GLabel;

import org.game_controller.ControlDevice;
import org.game_controller.ControlIO;

import processing.core.PApplet;

public class UControlDeviceEntry implements Comparable<UControlDeviceEntry> {
	public final PApplet app;
	public final ControlIO controlIO;
	public final ControlDevice device;
	public final MLabel displayName;
	public final MButton btnGoConfig;
	public UControlConfigWindow winCofig = null;

	public UControlDeviceEntry(PApplet papp, ControlIO controlIO, ControlDevice dev){
		this.app = papp;
		this.controlIO = controlIO;
		this.device = dev;
		displayName = new MLabel(papp, 36, 20, app.width-36, 20);
		displayName.setText(device.getName() + "  [" + device.getTypeName() + "]");
		displayName.setTextAlign(MAlign.LEFT, null);
		if(!device.getTypeName().equalsIgnoreCase("Keyboard")) {
			btnGoConfig = new MButton(app, 4, 24, 24, 14);
			btnGoConfig.addEventHandler(this, "configClick");				
		}
		else
			btnGoConfig = null;
	}

	public void setIndex(int index){
		displayName.moveTo(36, 20 + index * 20);
		if(btnGoConfig != null) btnGoConfig.moveTo(4, 24 + index * 20);
	}

	public void configClick(MButton source, MEvent event) {
		if(winCofig == null) {
			winCofig = new UControlConfigWindow(app, this);
			source.setVisible(false);
		}
	}

	@Override
	public int compareTo(UControlDeviceEntry entry) {
		return device.compareTo(entry.device);
	}
}
