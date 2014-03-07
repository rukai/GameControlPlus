package org.game_controller;

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
	
	public final String usage;
	public final InputConfig[] gameInputs;
	
	private Configuration(PApplet app, String[] inputs){
		List<InputConfig> inputConfigs = new ArrayList<InputConfig>();
		usage = inputs[0];
		for(int i = 1; i < inputs.length; i++){
			if(inputs[i].length() > 0)
				inputConfigs.add(new InputConfig(inputs[i]));
		}
		gameInputs = inputConfigs.toArray(new InputConfig[inputConfigs.size()]);
	}
	
	public int nbrInputs(){
		return gameInputs.length;
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
