import org.game_controller.*;
import net.java.games.input.*;
import org.game_controller.gui.*;

ControlIO control;

public void setup() {
  size(300, 300);
  control = ControlIO.getInstance(this);
  control.printDevices();

  for (int i = 0; i < control.getNumberOfDevices();i++)
    printDevice(i, control.getDevice(i));
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
