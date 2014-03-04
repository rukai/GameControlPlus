package org.game_controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public class Configuration implements GCConstants {

	public static Configuration makeConfiguration(PApplet app, String filename){
		String[] gameInputs = app.loadStrings(filename);
		if(gameInputs == null){
			System.out.println("Unable to find configuration file " + filename);
			return null;
		}
		return new Configuration(app, gameInputs);
	}
	
	public final InputConfig[] gameInputs;
	public int nbrMatched = 0;
	
	private Configuration(PApplet app, String[] inputs){
		List<InputConfig> inputConfigs = new ArrayList<InputConfig>();
		for(String s : inputs){
			if(s.length() > 0)
				inputConfigs.add(new InputConfig(s));
		}
		gameInputs = inputConfigs.toArray(new InputConfig[inputConfigs.size()]);
	}
	
	public class InputConfig {
		public String key;
		public String description;
		public int type;
		public String typeName;
		public String deviceInputName;
		public float multiplier;
		public float tolerance;
		
		public InputConfig(String line){
			String[] part = PApplet.split(line, SEPARATOR);
			key = part[0];
			description = part[1];
			type = Integer.parseInt(part[2]);
			typeName = part[3];
			deviceInputName = part[4];
			multiplier = Float.parseFloat(part[5]);
			tolerance = Float.parseFloat(part[6]);
		}
	}
	
	
}
