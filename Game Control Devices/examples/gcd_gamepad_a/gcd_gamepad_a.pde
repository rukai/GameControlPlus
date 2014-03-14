import org.game_controller.*;
import net.java.games.input.*;
import org.game_controller.gui.*;


ControlIO control;
ControlDevice gamepad;
DummyHandler dummy = null;

public void setup() {
  size(300, 500);
  control = ControlIO.getInstance(this);

  gamepad = control.getDevice("Controller");
  printDevice(0, gamepad);

  dummy = new DummyHandler();

  ControlButton b = gamepad.getButton(4);
  if (dummy == null) {
    b.plug("handlePress", ControlIO.ON_PRESS);
    b.plug("handleWhilePress", ControlIO.WHILE_PRESS);
    b.plug("handleRelease", ControlIO.ON_RELEASE);
  }
  else {
    b.plug(dummy, "handlePress", ControlIO.ON_PRESS);
    b.plug(dummy, "handleWhilePress", ControlIO.WHILE_PRESS);
    b.plug(dummy, "handleRelease", ControlIO.ON_RELEASE);
  }
}

public void handlePress() {
  System.out.println("# Pressed   @ " + System.currentTimeMillis());
}

public void handleWhilePress() {
  System.out.println("# Held down @ " + System.currentTimeMillis());
}

public void handleRelease() {
  System.out.println("# Released  @ " + System.currentTimeMillis());
}

public void draw() {
  background(255);
  for (int i = 0; i < gamepad.getNumberOfButtons(); i ++) {
    ControlButton b = gamepad.getButton(i);
    stroke(0);
    strokeWeight(1.5f);
    fill(255);
    if (b.pressed())
      fill(255, 100, 100);
    ellipse(20, 20 + i * 20, 8, 8);
    fill(0);
    text(b.getName() + "    " + b.getClass().getSimpleName(), 30, 24 + i *20);
  }
  int p = gamepad.getNumberOfButtons()*20+30;

  for (int i = 0; i < gamepad.getNumberOfSliders(); i ++) {
    ControlSlider sl = gamepad.getSlider(i);
    sl.setTolerance(0.2f);
    fill(0, 128, 0);
    text(""+ sl.getValue(), 20, p + i *20);
    fill(0);
    text(""+sl.getName() + "  " + sl.getTolerance(), 120, p + i *20);
  }
}

public void printDevice(int id, ControlDevice device) {
  System.out.println("========================================================================");
  System.out.println("Device number  " + id + " is called '" + device.getName() + "' and has");
  System.out.println("\t" + device.getNumberOfButtons() + " buttons");
  System.out.println("\t" + device.getNumberOfSliders() + " sliders");
  System.out.println("\t" + device.getNumberOfSticks() + " sticks");
  System.out.println("\t" + device.getNumberOfRumblers() + " rumblers");
  device.printButtons();
  device.printSliders();
  device.printSticks();
  System.out.println("------------------------------------------------------------------------\n\n");
}

public class DummyHandler {
  public void handlePress() {
    System.out.println("$ Pressed   @ " + System.currentTimeMillis());
  }

  public void handleWhilePress() {
    System.out.println("$ Held down @ " + System.currentTimeMillis());
  }

  public void handleRelease() {
    System.out.println("$ Released  @ " + System.currentTimeMillis());
  }
}
