/*
 *  Copyright (C) 2005  Jack Park,
 * 	mail : jackpark@gmail.com
 *
 *  Apache 2 License
 */
package org.nex.tinytsc.ui;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.*;
import javax.swing.border.TitledBorder;
import javax.swing.border.Border;
import fr.dyade.koala.util.VerticalLayout;

import org.nex.tinytsc.DatastoreException;
import org.nex.tinytsc.engine.Environment;
import javax.swing.border.BevelBorder;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class ConsoleTab
    extends JPanel {
  private Environment environment;
  JTextField modelCountField = new JTextField();
  JTextField modelWorkField = new JTextField();

  JTextField publishCountField = new JTextField();
  JTextField fillinCountField = new JTextField();
  JTextField publishWorkField = new JTextField();
  JTextField fillinWorkField = new JTextField();
  JTextField episodesCountField = new JTextField();
  JTextField modelsCountField = new JTextField();
  JTextField tasksCountField = new JTextField();
  JTextField rulesCountField = new JTextField();
  JTextField conceptCountField = new JTextField();
  TitledBorder titledBorder4 = new TitledBorder("");
  JPanel statsPanel = new JPanel();
  GridLayout gridLayout1 = new GridLayout();
  Border border5 = BorderFactory.createEtchedBorder(Color.white,
      new Color(178, 178, 178));
  Border border6 = new TitledBorder(border5, "Models Count");
  Border border7 = BorderFactory.createEtchedBorder(Color.white,
      new Color(178, 178, 178));
  Border border8 = new TitledBorder(border7, "Tasks Count");
  Border border9 = BorderFactory.createEtchedBorder(Color.white,
      new Color(178, 178, 178));
  Border border10 = new TitledBorder(border9, "Rules Count");
  Border border11 = BorderFactory.createEtchedBorder(Color.white,
      new Color(178, 178, 178));
  Border border12 = new TitledBorder(border11, "Concepts Count");
  JPanel jPanel1 = new JPanel();
  FlowLayout flowLayout1 = new FlowLayout();
  JPanel jPanel2 = new JPanel();
  FlowLayout flowLayout2 = new FlowLayout();
  FlowLayout flowLayout3 = new FlowLayout();
  JPanel jPanel3 = new JPanel();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  Border border13 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
      Color.white, Color.white, new Color(115, 114, 105),
      new Color(165, 163, 151));
  Border border14 = new TitledBorder(border13, "Statistics");
  Border border98 = BorderFactory.createEtchedBorder(Color.white,
      new Color(178, 178, 178));
  Border border99 = new TitledBorder(border98, "Episodes Count");
  TitledBorder titledBorder2;
  TitledBorder titledBorder1;
  public ConsoleTab() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void setEnvironment(Environment e) {
    this.environment = e;
  }
  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    titledBorder1 = new TitledBorder(BorderFactory.createLineBorder(Color.lightGray,1),"Dashboard");
    titledBorder2 = new TitledBorder(BorderFactory.createLineBorder(Color.lightGray,1),"Console");
    jScrollPane1.setBorder(titledBorder2);
    consoleArea.setEditable(false);
    consoleArea.setText("");
    consoleArea.setLineWrap(true);
    dashPanel.setBorder(titledBorder1);
    this.add(jScrollPane1, java.awt.BorderLayout.CENTER);
    jScrollPane1.getViewport().add(consoleArea);
    this.add(dashPanel, java.awt.BorderLayout.EAST);
    dashPanel.setLayout(verticalFlowLayout1);
    episodesCountField.setBorder(border99);
    episodesCountField.setText("");
    gridLayout1.setColumns(1);
    gridLayout1.setRows(5);
    jPanel1.setLayout(flowLayout1);
    jPanel2.setLayout(flowLayout2);
    jPanel2.setBackground(Color.orange);
    jPanel1.setBackground(Color.red);
    jLabel1.setFont(new java.awt.Font("SansSerif", 1, 14));
    jLabel1.setText("FillinNextEpisode");
    jLabel2.setFont(new java.awt.Font("SansSerif", 1, 14));
    jLabel2.setText("PublishEpisode");
    jPanel3.setLayout(flowLayout3);
    jPanel3.setBackground(Color.green);
    jLabel3.setFont(new java.awt.Font("SansSerif", 1, 14));
    jLabel3.setText("Model");
    jPanel3.add(jLabel3, null);
    jPanel1.add(jLabel1, null);
    jPanel2.add(jLabel2, null);
    dashPanel.add(jPanel3, null);
    modelCountField.setPreferredSize(new Dimension(100, 21));
    modelCountField.setToolTipText("Number of Episodes in Model...");
    modelCountField.setEditable(false);
    modelCountField.setText("");
    modelCountField.setHorizontalAlignment(SwingConstants.RIGHT);
    modelWorkField.setBackground(new Color(236, 233, 216));
    modelWorkField.setEnabled(true);
    modelWorkField.setPreferredSize(new Dimension(100, 21));
    modelWorkField.setToolTipText("Model being built...");
    modelWorkField.setEditable(false);
    modelWorkField.setText("");

    conceptCountField.setBorder(border12);
    conceptCountField.setText("");
    rulesCountField.setBorder(border10);
    rulesCountField.setText("");
    tasksCountField.setBorder(border8);
    tasksCountField.setText("");
    modelsCountField.setBorder(border6);
    modelsCountField.setText("");
    publishCountField.setPreferredSize(new Dimension(100, 21));
    publishCountField.setToolTipText("Number of Episodes in queue to publish...");
    publishCountField.setEditable(false);
    publishCountField.setText("");
    publishCountField.setHorizontalAlignment(SwingConstants.RIGHT);
    fillinCountField.setPreferredSize(new Dimension(100, 21));
    fillinCountField.setToolTipText("Number of Episodes in queue to fillin...");
    fillinCountField.setEditable(false);
    fillinCountField.setText("");
    fillinCountField.setHorizontalAlignment(SwingConstants.RIGHT);
    publishWorkField.setPreferredSize(new Dimension(100, 21));
    publishWorkField.setToolTipText("PublishEpisode working on...");
    publishWorkField.setEditable(false);
    publishWorkField.setText("");
    fillinWorkField.setPreferredSize(new Dimension(100, 21));
    fillinWorkField.setToolTipText("FillinNextEpisode working on...");
    fillinWorkField.setEditable(false);
    fillinWorkField.setText("");

    dashPanel.add(modelWorkField, null);
    dashPanel.add(modelCountField, null);
    dashPanel.add(jPanel1, null);
    dashPanel.add(fillinWorkField, null);
    dashPanel.add(fillinCountField, null);
    dashPanel.add(jPanel2, null);
    dashPanel.add(publishWorkField, null);
    dashPanel.add(publishCountField, null);

    dashPanel.add(statsPanel);
    statsPanel.setLayout(gridLayout1);
    statsPanel.add(conceptCountField);
    statsPanel.add(rulesCountField);
    statsPanel.add(tasksCountField);
    statsPanel.add(modelsCountField);
    statsPanel.add(episodesCountField);
    statsPanel.setBorder(border14);
  }
  public void updateStatistics() {
    try {
      this.conceptCountField.setText(Integer.toString(environment.getConceptCount()));
      this.rulesCountField.setText(Integer.toString(environment.getRuleCount()));
      this.tasksCountField.setText(Integer.toString(environment.getTaskCount()));
      this.modelsCountField.setText(Integer.toString(environment.getModelCount()));
      this.episodesCountField.setText(Integer.toString(environment.getEpisodeCount()));
    } catch (DatastoreException e) {
      e.printStackTrace();
    }
  }

  public void refreshDashboard(String modelId,
                               int numEpisodes,
                               String fillinEpId,
                               int fillinQueue,
                               String publishEpId,
                               int publishQueue) {
    setModelWorkField(modelId);
    setModelCountField(Integer.toString(numEpisodes));
    setFillinWorkField(fillinEpId);
    setFillinCountField(Integer.toString(fillinQueue));
    setPublishWorkField(publishEpId);
    setPublishCountField(Integer.toString(publishQueue));
  }

  public void setModelWorkField(String txt) {
    this.modelWorkField.setText(txt);
  }
  public void setModelCountField(String txt) {
    this.modelCountField.setText(txt);
  }
  public void setFillinWorkField(String txt) {
    this.fillinWorkField.setText(txt);
  }
  public void setFillinCountField(String txt) {
    this.fillinCountField.setText(txt);
  }
  public void setPublishWorkField(String txt) {
    this.publishWorkField.setText(txt);
  }
  public void setPublishCountField(String txt) {
    this.publishCountField.setText(txt);
  }

  public void say(String msg) {
	  environment.logDebug("Say\n"+msg);
    consoleArea.append(msg);
  }
  BorderLayout borderLayout1 = new BorderLayout();
  JScrollPane jScrollPane1 = new JScrollPane();
//  TitledBorder titledBorder1 = new TitledBorder("");
//  Border border1 = BorderFactory.createEtchedBorder(Color.white,
//      new Color(165, 163, 151));
 // Border border2 = new TitledBorder(border1, "Console");
  JTextArea consoleArea = new JTextArea();
  JPanel dashPanel = new JPanel();
//  TitledBorder titledBorder2 = new TitledBorder("");
  Border border3 = BorderFactory.createEtchedBorder(Color.white,
      new Color(165, 163, 151));
  Border border4 = new TitledBorder(border3, "Dashboard");
  VerticalLayout verticalFlowLayout1 = new VerticalLayout();
  Border border1 = BorderFactory.createLineBorder(Color.white, 2);
  Border border2 = new TitledBorder(border1, "Console");
}
