/**
 Basic demonstration of using a joystick.
 
 When this sketch runs it will try and find
 a game device that matches the configuration
 file 'joystick' if it can't match this device
 then it will present you with a list of devices
 you might try and use.
 
 The chosen device requires 2 sliders and 1 button.
 */

import org.procontrolplus.gui.*;
import org.procontrolplus.*;
import net.java.games.input.*;


ControlIO control;
ControlDevice stick;

ArrayList<PVector>  shadows = new ArrayList<PVector>();

public void setup() {
  size(400, 400);
  // Initialise the ControlIO
  control = ControlIO.getInstance(this);
  // Find a device that matches the configuration file
  stick = control.getMatchedDevice("joystick");
  if (stick == null) {
    println("No suitable device configured");
    System.exit(-1); // End the program NOW!
  }
  // Setup a function to trap events for this button
  stick.getButton("FIRE").plug(this, "dropShadow", ControlIO.ON_RELEASE);
}

public void dropShadow() {
  float x = map(stick.getSlider("X").getValue(), -1, 1, 0, width);
  float y = map(stick.getSlider("Y").getValue(), -1, 1, 0, height);
  shadows.add(new PVector(x, y, 40));
}

public void draw() {
  background(255, 255, 240);

  fill(0, 0, 0, 32);
  noStroke();
  for (PVector shadow : shadows)
    ellipse(shadow.x, shadow.y, shadow.z, shadow.z);

  fill(255, 64, 64, 64);
  float x = map(stick.getSlider("X").getValue(), -1, 1, 0, width);
  float y = map(stick.getSlider("Y").getValue(), -1, 1, 0, height);
  ellipse(x, y, 40, 40);
}

