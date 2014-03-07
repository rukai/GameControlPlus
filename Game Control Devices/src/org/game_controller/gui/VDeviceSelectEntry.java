package org.game_controller.gui;

import org.game_controller.Configuration;
import org.game_controller.ControlDevice;
import org.game_controller.ControlIO;

import processing.core.PApplet;

/**
 * This class represents a single entry in the device selection screen.
 * @author peter
 *
 */
public class VDeviceSelectEntry implements Comparable<VDeviceSelectEntry> {
	
	public final PApplet app;
	public final ControlIO controlIO;
	public final ControlDevice device;
	public final Configuration config;
	// GUI stuff
	public final MLabel displayName;
	public final MButton btnGoConfig;
	public VControlConfigWindow winCofig = null;

	public VDeviceSelectEntry(PApplet papp, ControlIO controlIO, ControlDevice dev, Configuration config){
		this.app = papp;
		this.controlIO = controlIO;
		this.device = dev;
		this.config = config;
		displayName = new MLabel(papp, 36, 20, app.width-36, 20);
		displayName.setText(device.getName() + "  [" + device.getTypeName() + "]");
		displayName.setTextAlign(MAlign.LEFT, null);
		btnGoConfig = new MButton(app, 4, 24, 24, 14);
		btnGoConfig.addEventHandler(this, "configClick");				
	}

	public void setIndex(int index){
		displayName.moveTo(36, 20 + index * 20);
		if(btnGoConfig != null) btnGoConfig.moveTo(4, 24 + index * 20);
	}

	public void configClick(MButton source, MEvent event) {
		if(winCofig == null) {
			winCofig = new VControlConfigWindow(app, this);
			source.setVisible(false);
		}
	}

	@Override
	public int compareTo(VDeviceSelectEntry entry) {
		return device.compareTo(entry.device);
	}
}
