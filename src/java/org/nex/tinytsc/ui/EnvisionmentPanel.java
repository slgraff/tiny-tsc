/*
 *  Copyright (C) 2005  Jack Park,
 * 	mail : jackpark@gmail.com
 *
 *  Apache 2 License
 */
package org.nex.tinytsc.ui;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

import org.nex.tinytsc.engine.Environment;
import org.nex.tinytsc.engine.Model;
import org.nex.tinytsc.engine.Episode;
/**
 * <p>Title: TinyTSC</p>
 * <p>Description: Small "The Scholar's Companion(r)"</p>
 * <p>Copyright: Copyright (c) 2005, Jack Park</p>
 * <p>Company: NexistGroup</p>
 * @author Jack Park
 * @version 1.0
 */

public class EnvisionmentPanel extends JPanel {
  Environment environment;
  Model model;
  Episode episode;
  BorderLayout borderLayout1 = new BorderLayout();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTextArea textArea = new JTextArea();
  JPanel jPanel1 = new JPanel();
  FlowLayout flowLayout1 = new FlowLayout();
  JButton refreshButton = new JButton();
  JButton clearButton = new JButton();
  Map looper = new HashMap();

  public EnvisionmentPanel() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void setEnvironment(Environment e) {
    environment = e;
  }
  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    textArea.setEditable(false);
    jPanel1.setLayout(flowLayout1);
    flowLayout1.setAlignment(FlowLayout.RIGHT);
    refreshButton.setBorder(BorderFactory.createRaisedBevelBorder());
    refreshButton.setText(" Refresh ");
    refreshButton.addActionListener(new EnvisionmentPanel_refreshButton_actionAdapter(this));
    clearButton.setBorder(BorderFactory.createRaisedBevelBorder());
    clearButton.setText(" Clear ");
    clearButton.addActionListener(new EnvisionmentPanel_clearButton_actionAdapter(this));
    this.add(jScrollPane1, BorderLayout.CENTER);
    this.add(jPanel1, BorderLayout.NORTH);
    jScrollPane1.getViewport().add(textArea, null);
    jPanel1.add(clearButton, null);
    jPanel1.add(refreshButton, null);
  }

  public void addText(String text) {
    this.textArea.append(text + "\n");
  }

  void refreshButton_actionPerformed(ActionEvent e) {
    Model m;
    Episode ep;
    java.util.List modelIds = environment.listModelIds();
    int len = modelIds.size(), len2;
    for (int i=0;i<len;i++) {
      m = environment.getModel((String)modelIds.get(i));
      this.textArea.append(m.toXML()+"\n");
      showNextEpisodes(m);
    }
  }

  /**
   * Recursive descent into the tree
   * @param episode
   */
  void showNextEpisodes(Episode episode) {
    if (!looper.containsKey(episode.getId()))
      looper.put(episode.getId(),episode);
    else
      return;  // been there, done that
    Iterator itr = episode.listNextEpisodeIds();
    Episode next;
    String n;
    while (itr.hasNext()) {
      n = (String)itr.next();
      next = environment.getEpisode(n);
      if (n == null)
        throw new RuntimeException("Missing Episode: "+n);
      this.textArea.append(next.toXML()+"\n");
      showNextEpisodes(next);
    }
  }

  void clearButton_actionPerformed(ActionEvent e) {
    this.textArea.setText("");
  }
}

class EnvisionmentPanel_refreshButton_actionAdapter implements java.awt.event.ActionListener {
  EnvisionmentPanel adaptee;

  EnvisionmentPanel_refreshButton_actionAdapter(EnvisionmentPanel adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.refreshButton_actionPerformed(e);
  }
}

class EnvisionmentPanel_clearButton_actionAdapter implements java.awt.event.ActionListener {
  EnvisionmentPanel adaptee;

  EnvisionmentPanel_clearButton_actionAdapter(EnvisionmentPanel adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.clearButton_actionPerformed(e);
  }
}