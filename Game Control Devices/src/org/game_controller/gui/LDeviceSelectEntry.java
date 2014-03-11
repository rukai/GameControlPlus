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
public class LDeviceSelectEntry implements Comparable<LDeviceSelectEntry> {

	public final PApplet app;
	public final ControlIO controlIO;
	public final ControlDevice device;
	// GUI stuff
	public final MLabel displayName;
	public final MButton btnGoConfig;
	public LDeviceConfigWindow winCofig = null;

	public LDeviceSelectEntry(MWindow window, ControlIO controlIO, ControlDevice dev){
		this.app = window.papplet;
		this.controlIO = controlIO;
		this.device = dev;
		displayName = new MLabel(app, 36, 20, app.width-36, 20);
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
			winCofig = new LDeviceConfigWindow(app, this);
			source.setVisible(false);
		}
	}

	@Override
	public int compareTo(LDeviceSelectEntry entry) {
		return device.compareTo(entry.device);
	}
}
