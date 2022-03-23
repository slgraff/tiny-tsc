/*
 *  Copyright (C) 2005  Jack Park,
 * 	mail : jackpark@gmail.com
 *
 *  Apache 2 License
 */
package org.nex.tinytsc;

import java.awt.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.Hashtable;

import org.nex.tinytsc.engine.Environment;
import org.nex.tinytsc.engine.Model;
import org.nex.tinytsc.api.IExporterListener;
import org.nex.tinytsc.api.IPluggable;
import org.nex.tinytsc.api.IPluggableHost;
import org.nex.tinytsc.api.Identifiable;
import org.nex.tinytsc.engine.Concept;
import org.nex.tinytsc.engine.Episode;
import org.nex.tinytsc.xml.ConceptFileExporter;
import org.nex.tinytsc.xml.ConceptPullParser;

import org.nex.persist.text.TextFileHandler;

import fr.dyade.koala.util.VerticalLayout;
/**
 * <p>Title: TinyTSC</p>
 * <p>Description: Small "The Scholar's Companion(r)"</p>
 * <p>Copyright: Copyright (c) 2005, Jack Park</p>
 * <p>Company: NexistGroup</p>
 * @author Jack Park
 * @version 1.0
 */

public class MainFrame extends JFrame
    implements IPluggableHost, IExporterListener {
  private Environment environment;
  private TreeListener treeListener = new TreeListener();
  private ConsoleTab _consoleTab = new ConsoleTab();
  JPanel contentPane;
  JMenuBar jMenuBar1 = new JMenuBar();
  JMenu jMenuFile = new JMenu();
  JMenuItem jMenuFileExit = new JMenuItem();
  JMenu jMenuHelp = new JMenu();
  JMenuItem jMenuHelpAbout = new JMenuItem();
  JLabel statusBar = new JLabel();
  BorderLayout borderLayout1 = new BorderLayout();
  JTabbedPane tabbedPane = new JTabbedPane();
//  JPanel consoleTab = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JScrollPane jScrollPane1 = new JScrollPane();
//  JTextArea consoleArea = new JTextArea();
//  JPanel dashPanel = new JPanel();
//  VerticalLayout verticalFlowLayout1 = new VerticalLayout();
  TitledBorder titledBorder1;
  JPanel jPanel1 = new JPanel();
  FlowLayout flowLayout1 = new FlowLayout();
  JPanel jPanel2 = new JPanel();
  FlowLayout flowLayout2 = new FlowLayout();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  TitledBorder titledBorder2;
//  JTextField publishCountField = new JTextField();
//  JTextField fillinCountField = new JTextField();
//  JTextField publishWorkField = new JTextField();
//  JTextField fillinWorkField = new JTextField();
  JPanel jPanel3 = new JPanel();
  FlowLayout flowLayout3 = new FlowLayout();
  JLabel jLabel3 = new JLabel();
  JTextField modelCountField = new JTextField();
  JTextField modelWorkField = new JTextField();
  TreePanel modelTreeTab = new TreePanel();
  JMenuItem importLegacyItem = new JMenuItem();
  TreePanel ontologyTreeTab = new TreePanel();
  TaskSelectorDialog taskDialog = new TaskSelectorDialog();
  JMenu tscMenu = new JMenu();
  JMenuItem runTaskItem = new JMenuItem();
  EnvisionmentPanel envisionmentPanel = new EnvisionmentPanel();
  JMenuItem waitItem = new JMenuItem();
  JMenu kbMenu = new JMenu();
  JMenuItem exportXmlItem = new JMenuItem();
  JMenuItem importXmlItem = new JMenuItem();
  JMenuItem displayItem = new JMenuItem();
  TouchgraphTab touchgraphTab = new TouchgraphTab();
//  JPanel statsPanel = new JPanel();
  GridLayout gridLayout1 = new GridLayout();
  TitledBorder titledBorder3 = new TitledBorder("");
  Border border1 = BorderFactory.createLineBorder(Color.white, 2);
  Border border2 = new TitledBorder(border1, "Statistics");
//  JTextField episodesCountField = new JTextField();
//  JTextField modelsCountField = new JTextField();
//  JTextField tasksCountField = new JTextField();
//  JTextField rulesCountField = new JTextField();
//  JTextField conceptCountField = new JTextField();
  TitledBorder titledBorder4 = new TitledBorder("");
  Border border3 = BorderFactory.createEtchedBorder(Color.white,
      new Color(178, 178, 178));
  Border border4 = new TitledBorder(border3, "Episodes Count");
  TitledBorder titledBorder5 = new TitledBorder("");
  Border border5 = BorderFactory.createEtchedBorder(Color.white,
      new Color(178, 178, 178));
  Border border6 = new TitledBorder(border5, "Models Count");
  TitledBorder titledBorder6 = new TitledBorder("");
  Border border7 = BorderFactory.createEtchedBorder(Color.white,
      new Color(178, 178, 178));
  Border border8 = new TitledBorder(border7, "Tasks Count");
  TitledBorder titledBorder7 = new TitledBorder("");
  Border border9 = BorderFactory.createEtchedBorder(Color.white,
      new Color(178, 178, 178));
  Border border10 = new TitledBorder(border9, "Rules Count");
  TitledBorder titledBorder8 = new TitledBorder("");
  Border border11 = BorderFactory.createEtchedBorder(Color.white,
      new Color(178, 178, 178));
  Border border12 = new TitledBorder(border11, "Concepts Count");
  Border border13 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
      Color.white, Color.white, new Color(115, 114, 105),
      new Color(165, 163, 151));
  Border border14 = new TitledBorder(border13, "Statistics");
  Border border15 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
      Color.white, Color.white, new Color(115, 114, 105),
      new Color(165, 163, 151));
  Border border16 = new TitledBorder(border15, "Console");
  Border border17 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
      Color.white, Color.white, new Color(115, 114, 105),
      new Color(165, 163, 151));
  Border border18 = new TitledBorder(border17, "Console");
  Border border19 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
      Color.white, Color.white, new Color(115, 114, 105),
      new Color(165, 163, 151));
  Border border20 = new TitledBorder(border19, "Dashboard");

  //Construct the frame
  public MainFrame(Hashtable properties) {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
      environment = new Environment(properties, this);
      _consoleTab.setEnvironment(environment);
      treeListener.setEnvironment(environment);
      modelTreeTab.setListener(treeListener);
      ontologyTreeTab.setListener(treeListener);
      ontologyTreeTab.setRootConcept(treeListener.getRootConcept());
      taskDialog.setHost(this);
      envisionmentPanel.setEnvironment(environment);
      updateStatistics();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  //Component initialization
  private void jbInit() throws Exception  {
    contentPane = (JPanel) this.getContentPane();
    titledBorder1 = new TitledBorder(BorderFactory.createLineBorder(Color.white,1),"Dashboard");
    titledBorder2 = new TitledBorder(BorderFactory.createLineBorder(Color.white,1),"Console");
    contentPane.setLayout(borderLayout1);
    this.setSize(new Dimension(800, 600));
    this.setTitle("Tiny TSC");
    statusBar.setText(" ");
    jMenuFile.setText("File");
    jMenuFileExit.setText("Exit");
    jMenuFileExit.addActionListener(new MainFrame_jMenuFileExit_ActionAdapter(this));
    jMenuHelp.setText("Help");
    jMenuHelpAbout.setText("About");
    jMenuHelpAbout.addActionListener(new MainFrame_jMenuHelpAbout_ActionAdapter(this));
//    consoleTab.setLayout(borderLayout2);
//    consoleArea.setEditable(false);
//    dashPanel.setLayout(verticalFlowLayout1);
    jPanel1.setLayout(flowLayout1);
    jPanel2.setLayout(flowLayout2);
    jPanel2.setBackground(Color.orange);
    jPanel1.setBackground(Color.red);
    jLabel1.setFont(new java.awt.Font("SansSerif", 1, 14));
    jLabel1.setText("FillinNextEpisode");
    jLabel2.setFont(new java.awt.Font("SansSerif", 1, 14));
    jLabel2.setText("PublishEpisode");
    jScrollPane1.setBorder(titledBorder2);
 //   publishCountField.setPreferredSize(new Dimension(100, 21));
 //   publishCountField.setToolTipText("Number of Episodes in queue to publish...");
 //   publishCountField.setEditable(false);
 //   publishCountField.setText("");
 //   publishCountField.setHorizontalAlignment(SwingConstants.RIGHT);
 //   fillinCountField.setPreferredSize(new Dimension(100, 21));
 //   fillinCountField.setToolTipText("Number of Episodes in queue to fillin...");
 //   fillinCountField.setEditable(false);
 //   fillinCountField.setText("");
 //   fillinCountField.setHorizontalAlignment(SwingConstants.RIGHT);
 //   publishWorkField.setPreferredSize(new Dimension(100, 21));
 //   publishWorkField.setToolTipText("PublishEpisode working on...");
 //   publishWorkField.setEditable(false);
 //   publishWorkField.setText("");
 //   fillinWorkField.setPreferredSize(new Dimension(100, 21));
 //   fillinWorkField.setToolTipText("FillinNextEpisode working on...");
 //   fillinWorkField.setEditable(false);
 //   fillinWorkField.setText("");
    jPanel3.setLayout(flowLayout3);
    jPanel3.setBackground(Color.green);
    jLabel3.setFont(new java.awt.Font("SansSerif", 1, 14));
    jLabel3.setText("Model");
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
    importLegacyItem.setText("Import Legacy...");
    importLegacyItem.addActionListener(new MainFrame_importLegacyItem_actionAdapter(this));
    tscMenu.setText("TSC");
    runTaskItem.setText("Run Task...");
    runTaskItem.addActionListener(new MainFrame_runTaskItem_actionAdapter(this));
    waitItem.setText("Wait");
    waitItem.addActionListener(new MainFrame_waitItem_actionAdapter(this));
    kbMenu.setText("KnowledgeBase");
    exportXmlItem.setText("Export XML...");
    exportXmlItem.addActionListener(new MainFrame_exportXmlItem_actionAdapter(this));
    importXmlItem.setText("Import XML...");
    importXmlItem.addActionListener(new MainFrame_importXmlItem_actionAdapter(this));
    displayItem.setText("Display Concept...");
    displayItem.addActionListener(new MainFrame_displayItem_actionAdapter(this));
 //   statsPanel.setLayout(gridLayout1);
    gridLayout1.setColumns(1);
    gridLayout1.setRows(5);
//    episodesCountField.setBorder(border4);
//    episodesCountField.setText("");
//    modelsCountField.setBorder(border6);
//    modelsCountField.setText("");
//    tasksCountField.setBorder(border8);
//    tasksCountField.setText("");
//    rulesCountField.setBorder(border10);
//    rulesCountField.setText("");
//    conceptCountField.setBorder(border12);
//    conceptCountField.setText("");
//    statsPanel.setBorder(border14);
 //   dashPanel.setBorder(titledBorder1);
    jPanel3.add(jLabel3, null);
//    dashPanel.add(modelWorkField, null);
//    dashPanel.add(modelCountField, null);
//    dashPanel.add(jPanel1, null);
//    dashPanel.add(fillinWorkField, null);
//    dashPanel.add(fillinCountField, null);
//    dashPanel.add(jPanel2, null);
//    dashPanel.add(publishWorkField, null);
//    dashPanel.add(publishCountField, null);
//    dashPanel.add(statsPanel);
//    statsPanel.add(conceptCountField);
//    statsPanel.add(rulesCountField);
//    statsPanel.add(tasksCountField);
//    statsPanel.add(modelsCountField);
//    statsPanel.add(episodesCountField);
    jPanel1.add(jLabel1, null);
    jPanel2.add(jLabel2, null);
    jMenuFile.add(importLegacyItem);
    jMenuFile.addSeparator();
    jMenuFile.add(jMenuFileExit);
    jMenuHelp.add(jMenuHelpAbout);
    jMenuBar1.add(jMenuFile);
    jMenuBar1.add(tscMenu);
    jMenuBar1.add(kbMenu);
    jMenuBar1.add(jMenuHelp);
    this.setJMenuBar(jMenuBar1);
    contentPane.add(statusBar, BorderLayout.SOUTH);
    contentPane.add(tabbedPane,  BorderLayout.CENTER);
//    consoleTab.add(jScrollPane1, BorderLayout.CENTER);
//    consoleTab.add(dashPanel,  BorderLayout.EAST);
//    jScrollPane1.getViewport().add(consoleArea, null);
    tabbedPane.add(_consoleTab,  "Console");
    tabbedPane.add(modelTreeTab,  "Model Browser");
    Model m = new Model("Empty Model");
    modelTreeTab.setModel(m);
    tabbedPane.add(ontologyTreeTab,   "Ontology Browser");
    tabbedPane.add(envisionmentPanel,  "Envisionment");
    tscMenu.add(runTaskItem);
    tscMenu.add(waitItem);
    kbMenu.add(exportXmlItem);
    kbMenu.add(importXmlItem);
    kbMenu.addSeparator();
    kbMenu.add(displayItem);
    tabbedPane.add(touchgraphTab, "Graph Browser");
    Concept c = new Concept("root");
//    ontologyTreeTab.setRootConcept(c);
  }

  /**
   * Callback from <code>Environment</code>
   * @param msg
   */
  public void say(String msg) {
    _consoleTab.say(msg+"\n");
  }

  /**
   * Callback from <code>Environment</code>
   * @param root
   */
  public void displayConceptRoot(Concept root, Model model) {
	  try {
    this.ontologyTreeTab.setRootConcept(root);
    this.treeListener.setRootConcept(root);
    if (model != null)
    	this.modelTreeTab.setModel(model);
	  } catch (Exception e) {
		  environment.logError(e.getMessage(), e);
	  }
  }

  public void updateStatistics() {
    _consoleTab.updateStatistics();
  }
  /**
   * Callback from <code>TaskSelectorDialog</code>
   * @param id
   */
  public void acceptTaskId(String id) {
    say("Starting task: "+id);
    //TODO
  }

  /**
   * Callbaci from <code>Environment</code>
   * @param e
   */
  public void showEpisode(Episode e) {
    this.envisionmentPanel.addText(e.toXML());
  }
  /**
   * Callback from <code>Environment</code>
   * @param modelId
   * @param numEpisodes
   * @param fillinEpId
   * @param fillinQueue
   * @param publishEpId
   * @param publishQueue
   */
  public void refreshDashboard(String modelId,
                               int numEpisodes,
                               String fillinEpId,
                               int fillinQueue,
                               String publishEpId,
                               int publishQueue) {
    _consoleTab.refreshDashboard(modelId,numEpisodes,fillinEpId,fillinQueue,publishEpId,publishQueue);
  }
  //File | Exit action performed
  public void jMenuFileExit_actionPerformed(ActionEvent e) {
    environment.close();
    System.exit(0);
  }
  //Help | About action performed
  public void jMenuHelpAbout_actionPerformed(ActionEvent e) {
    MainFrame_AboutBox dlg = new MainFrame_AboutBox(this);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(true);
    dlg.pack();
    dlg.setVisible(true);
  }
  //Overridden so we can exit when window is closed
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      jMenuFileExit_actionPerformed(null);
    }
  }

  /**
   * Essentially deprecated. This will only be used until
   * all legacy TSC files have been imported.
   * @param e
   */
  void importLegacyItem_actionPerformed(ActionEvent e) {
    new LegacyImporter(environment).startImport();
  }

  void runTaskItem_actionPerformed(ActionEvent e) {
    say("Starting tasks");
    environment.startTasks();
//    this.taskDialog.showSelf(environment.listTasks());
  }

  void waitItem_actionPerformed(ActionEvent e) {
    say("Idling tasks");
    environment.waitTasks();
  }

  void exportXmlItem_actionPerformed(ActionEvent e) {
    ExportDialog d = new ExportDialog();
    d.setHost(this);
    d.showSelf();
    //wait for processExport
  }

  public void processExport() {
    ConceptFileExporter xp = new ConceptFileExporter(environment, this);
    System.out.println("MainFrame.processExport");
    xp.exportDatabase();
  }
  /**
   * Callback from {@link ConceptFileExporter}
   */
  public void exportDone() {
    //TODO do nothing
  }
  void importXmlItem_actionPerformed(ActionEvent e) {
    TextFileHandler h = new TextFileHandler();
    File f = h.openFile("XML Import File");
    if (f != null) {
      ConceptPullParser p = new ConceptPullParser(environment);
      p.parse(f);
    }

  }

  void displayItem_actionPerformed(ActionEvent e) {
    String con = JOptionPane.showInputDialog(this,"Object ID");
    System.out.println("MAINFRAME "+environment);
    if (con != null && !con.equals("")) {
      Identifiable cx = environment.getIdentifiable(con.trim());
      if (cx != null)
        say(cx.toXML());
      else
        say(con+" not found");
    }
  }

  //////////////////////////////
  // IPluggableHost API
  //////////////////////////////
	/**
	 * An <code>IPluggable</code> is created with
	 * <code>Class.forName(...)</code> and installed.
	 * If the <code>IPluggable</code> needs GUI support,
	 * this interface applies.
	 * @param tabTitle
	 * @param agent
	 */
	public void addPluggable(String tabTitle, IPluggable agent) {
		this.tabbedPane.add((Component)agent, tabTitle);
	}

	/**
	 * Returns a copy of <code>Environment</code> for the agent to use.
	 * @return
	 */
	public Environment getEnvironment() {
		return this.environment;
	}

}

class MainFrame_jMenuFileExit_ActionAdapter implements ActionListener {
  MainFrame adaptee;

  MainFrame_jMenuFileExit_ActionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuFileExit_actionPerformed(e);
  }
}

class MainFrame_jMenuHelpAbout_ActionAdapter implements ActionListener {
  MainFrame adaptee;

  MainFrame_jMenuHelpAbout_ActionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuHelpAbout_actionPerformed(e);
  }
}

class MainFrame_importLegacyItem_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_importLegacyItem_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.importLegacyItem_actionPerformed(e);
  }
}

class MainFrame_runTaskItem_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_runTaskItem_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.runTaskItem_actionPerformed(e);
  }
}

class MainFrame_waitItem_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_waitItem_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.waitItem_actionPerformed(e);
  }
}

class MainFrame_exportXmlItem_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_exportXmlItem_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.exportXmlItem_actionPerformed(e);
  }
}

class MainFrame_importXmlItem_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_importXmlItem_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.importXmlItem_actionPerformed(e);
  }
}

class MainFrame_displayItem_actionAdapter implements java.awt.event.ActionListener {
  MainFrame adaptee;

  MainFrame_displayItem_actionAdapter(MainFrame adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.displayItem_actionPerformed(e);
  }
}
