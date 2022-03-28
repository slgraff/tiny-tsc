/*
 *  Copyright (C) 2005, 2006  Jack Park,
 * 	mail : jackpark@gmail.com
 *
 * Apache 2 License
*/
package org.nex.tinytsc.ui;
import com.touchgraph.graphlayout.GLPanel;
import javax.swing.*;
import java.awt.*;

/**
 * <p>Title: TopicSpaces JackRabbit</p>
 * <p>Description: TopicSpaces with JSR-170 backside</p>
 * <p>Copyright: Copyright (c) 2006, Jack Park</p>
 * <p>Company: NexistGroup</p>
 * @author Park
 */
public class TouchgraphTab extends JPanel {
	private GLPanel tgPanel;

	public TouchgraphTab() {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

	void init() throws Exception {
		this.setLayout(new BorderLayout());
		tgPanel = new GLPanel();
		this.add(tgPanel,BorderLayout.CENTER);
	}

  private void jbInit() throws Exception {
  }

  //TODO
	//Map Painting API

}
