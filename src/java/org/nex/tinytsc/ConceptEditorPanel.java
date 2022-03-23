/*
 *  Copyright (C) 2005  Jack Park,
 * 	mail : jackpark@gmail.com
 *
 *  Apache 2 License
 */
package org.nex.tinytsc;

import java.awt.*;
//import java.util.*;
import javax.swing.*;
//import javax.swing.border.*;
//import java.awt.event.*;

//import org.nex.tinytsc.engine.Environment;
import org.nex.tinytsc.engine.Concept;
//import org.nex.tinytsc.engine.Sentence;

/**
 * <p>Title: TinyTSC</p>
 * <p>Description: Small "The Scholar's Companion(r)"</p>
 * <p>Copyright: Copyright (c) 2005, Jack Park</p>
 * <p>Company: NexistGroup</p>
 * @author Jack Park
 * @version 1.0
 */

/**
 * <p>Title: TinyTSC</p>
 * <p>Description: Small "The Scholar's Companion(r)"</p>
 * <p>Copyright: Copyright (c) 2005, Jack Park</p>
 * <p>Company: NexistGroup</p>
 * @author Jack Park
 * @version 1.0
 */

public class ConceptEditorPanel extends JPanel {
  BorderLayout borderLayout1 = new BorderLayout();
  JSplitPane jSplitPane1 = new JSplitPane();
  TreePanel treePanel = new TreePanel();
  ConceptEditor conceptEditor = new ConceptEditor();

  public ConceptEditorPanel() {
    try {
      jbInit();
      treePanel.setIsSlaveTree(true);
      conceptEditor.setTreePanel(treePanel);
    }
    catch(Exception e) {
      e.printStackTrace();
    }
   }

  public void setListener(TreeListener e) {
  	treePanel.setListener(e);
        conceptEditor.setListener(e);
  }

  public void setRootConcept(Concept c) {
	  try {
    treePanel.setRootConcept(c);
    conceptEditor.setRootConcept(c);
	  } catch (Exception e) {
		  throw new RuntimeException(e);
	  }

  }

  public void setConceptId(String id) {
    conceptEditor.setConceptId(id);
  }

  /**
   * Allow for creating instance and sub identity
   */
  public void clearConceptIdField() {
    conceptEditor.clearConceptIdField();
  }

  public void addInstanceOf(String id) {
    conceptEditor.newInstanceOf(id);
  }

  public void addSubOf(String id) {
    conceptEditor.newSubOf(id);
  }

  public void newConcept() {
    conceptEditor.newConcept();
  }

  public Concept getConcept() {
    return conceptEditor.getConcept();
  }

  public boolean getIsDirty() {
    return conceptEditor.getIsDirty();
  }
  
/**
  void addSlot(String slot) {
    //TODO
  }
  void addRule(String ruleId) {
    //TODO
  }
  void addEpisode(String episodeId) {
    //TODO
  }
*/
  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    this.add(jSplitPane1, BorderLayout.CENTER);
    jSplitPane1.add(treePanel, JSplitPane.LEFT);
    jSplitPane1.add(conceptEditor, JSplitPane.RIGHT);
  }

}

