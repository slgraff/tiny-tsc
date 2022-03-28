/*
 *  Copyright (C) 2005  Jack Park,
 * 	mail : jackpark@gmail.com
 *
 *  Apache 2 License
 */
package org.nex.tinytsc;

import javax.swing.UIManager;
import java.awt.*;
import java.util.Hashtable;

import org.nex.config.ConfigPullParser;
import org.nex.tinytsc.engine.Environment;
import org.nex.tinytsc.ui.MainFrame;
//log4j.jar
import org.apache.log4j.PropertyConfigurator;

/**
 * <p>Title: TinyTSC</p>
 * <p>Description: Small "The Scholar's Companion(r)"</p>
 * <p>Copyright: Copyright (c) 2005, Jack Park</p>
 * <p>Company: NexistGroup</p>
 * @author Jack Park
 * @version 1.0
 */

public class Main {
  boolean packFrame = false;

  //Construct the application
  public Main() {
  	// configure logger
    PropertyConfigurator.configure("logger.properties");
    ConfigPullParser parser = new ConfigPullParser("tsc-props.xml");
    Hashtable properties = parser.getProperties();
    Environment environment = null;
    try {
    	environment = new Environment(properties);
    } catch (Exception e) {
    	throw new RuntimeException(e);
    }
    System.out.println("STARTING "+environment);
    MainFrame frame = new MainFrame(environment);
    //Validate frames that have preset sizes
    //Pack frames that have useful preferred size info, e.g. from their layout
    if (packFrame) {
      frame.pack();
    }
    else {
      frame.validate();
    }
    //Center the window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = frame.getSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    frame.setVisible(true);
    environment.logDebug("Booting "+frame);
    environment.setHost(frame);
  }
  //Main method
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    new Main();
  }
}