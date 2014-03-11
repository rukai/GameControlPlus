package org.game_controller;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public class Configuration implements GCConstants {

	public static Configuration makeConfiguration(PApplet app, String filename){
		String[] configLines = app.loadStrings(filename);
		if(configLines == null){
			System.out.println("Unable to find configuration file " + filename);
			return null;
		}
		return new Configuration(app, configLines, filename);
	}
	
	public final String filename;
	public final String usage;
	public final InputConfig[] gameInputs;
	
	private Configuration(PApplet app, String[] lines, String filename){
		List<InputConfig> inputConfigs = new ArrayList<InputConfig>();
		this.filename = filename;
		usage = lines[0];
		for(int i = 1; i < lines.length; i++){
			if(lines[i].length() > 0)
				inputConfigs.add(new InputConfig(lines[i]));
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
		public int inputConNo;
		public float multiplier;
		public float tolerance;
		
		public InputConfig(String line){
			String[] part = PApplet.split(line, SEPARATOR);
			key = part[0];
			description = part[1];
			type = Integer.parseInt(part[2]);
			typeName = part[3];
			deviceInputName = part[4];
			inputConNo = Integer.parseInt(part[5]);
			multiplier = Float.parseFloat(part[6]);
			tolerance = Float.parseFloat(part[7]);
		}
		
		public String toString(){
			StringBuilder sb = new StringBuilder(key + SEPARATOR);
			sb.append(description + SEPARATOR);
			sb.append(type + SEPARATOR);
			sb.append(typeName + SEPARATOR);
			sb.append(deviceInputName + SEPARATOR);
			sb.append(inputConNo + SEPARATOR);
			sb.append(multiplier + SEPARATOR);
			sb.append(tolerance);
			return sb.toString();
		}
	}
}
