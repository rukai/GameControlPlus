/**
 Basic demonstration of using a gamepad.
 
 When this sketch runs it will try and find
 a game device that matches the configuration
 file 'gamepad' if it can't match this device
 then it will present you with a list of devices
 you might try and use.
 
 The chosen device requires 6 sliders and 2 button.
 */

import org.procontrolplus.gui.*;
import org.procontrolplus.*;
import net.java.games.input.*;

ControlIO control;
Configuration config;
ControlDevice gpad;

float pupilPosX, pupilPosY, pupilSize;
float eyeRad = 80, eyeSize = eyeRad * 2;
float irisRad = 42, irisSize = irisRad * 2;
float lidPos, minLid = PI * 0.3f, maxLid = PI * 0.92f;

public void setup() {
  size(400, 200);
  // Initialise the ControlIO
  control = ControlIO.getInstance(this);
  control.printDevices();
  // Find a device that matches the configuration file
  gpad = control.getMatchedDevice("gamepad");
  if (gpad == null) {
    println("No suitable device configured");
    System.exit(-1); // End the program NOW!
  }
}

public void draw() {
  background(200, 255, 200);
  pupilSize = gpad.getButton("LEFTB").pressed() ? irisSize * 0.6f : irisSize * 0.45f; 
  pupilPosX =  0.9f * map(gpad.getSlider("LEFTX").getValue(), -1, 1, -(eyeRad - irisRad), eyeRad - irisRad);
  pupilPosY =  0.9f * map(gpad.getSlider("LEFTY").getValue(), -1, 1, -(eyeRad - irisRad), eyeRad - irisRad);
  lidPos = map(gpad.getSlider("LEFTLID").getValue(), -1, 1, minLid, maxLid);
  drawEye(100, 100);
  pupilSize = gpad.getButton("RIGHTB").pressed() ? irisSize * 0.6f : irisSize * 0.45f; 
  pupilPosX =  0.9f * map(gpad.getSlider("RIGHTX").getValue(), -1, 1, -(eyeRad - irisRad), eyeRad - irisRad);
  pupilPosY =  0.9f * map(gpad.getSlider("RIGHTY").getValue(), -1, 1, -(eyeRad - irisRad), eyeRad - irisRad);
  lidPos = map(gpad.getSlider("RIGHTLID").getValue(), -1, 1, minLid, maxLid);
  drawEye(300, 100);
}

public void drawEye(int x, int y) {
  pushMatrix();
  translate(x, y);
  // draw white of eye
  stroke(0, 96, 0);
  strokeWeight(3);
  fill(255);
  ellipse(0, 0, eyeSize, eyeSize);
  // draw iris
  noStroke();
  fill(120, 100, 220);
  ellipse(pupilPosX, pupilPosY, irisSize, irisSize);
  // draw pupil
  fill(32);
  ellipse(pupilPosX, pupilPosY, pupilSize, pupilSize);
  // draw eye lid
  stroke(0, 96, 0);
  strokeWeight(4);
  fill(180, 240, 180);
  arc(0, 0, eyeSize, eyeSize, 1.5f*PI-lidPos, 1.5f*PI+lidPos, CHORD);
  popMatrix();
}

