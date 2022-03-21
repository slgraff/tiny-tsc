/*
 *  Copyright (C) 2005  Jack Park,
 * 	mail : jackpark@gmail.com
 *
 *  Apache 2 License
 */
package org.nex.tinytsc;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import org.nex.tinytsc.engine.Environment;
import org.nex.tinytsc.engine.Concept;
import org.nex.tinytsc.DatastoreException;

/**
 * <p>Title: TinyTSC</p>
 * <p>Description: Small "The Scholar's Companion(r)"</p>
 * <p>Copyright: Copyright (c) 2005, Jack Park</p>
 * <p>Company: NexistGroup</p>
 * @author Jack Park
 * @version 1.0
 */

public class ConceptEditorDialog extends JDialog {
  ConceptEditorPanel conceptEditorPanel = new ConceptEditorPanel();
  Environment environment;
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  FlowLayout flowLayout1 = new FlowLayout();
  JButton cancelButton = new JButton();
  JButton okButton = new JButton();

  public ConceptEditorDialog() throws HeadlessException {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void setListener(TreeListener e) {
    conceptEditorPanel.setListener(e);
    environment = e.getEnvironment();
  }

  public void setRootConcept(Concept c) {
    conceptEditorPanel.setRootConcept(c);
  }

  private void jbInit() throws Exception {
    this.setTitle("Concept Editor");
    this.getContentPane().setLayout(borderLayout1);
    this.setSize(600,600);
    jPanel1.setLayout(flowLayout1);
    flowLayout1.setHgap(20);
    cancelButton.setText("Cancel");
    cancelButton.addActionListener(new ConceptEditorDialog_cancelButton_actionAdapter(this));
    okButton.setText("OK");
    okButton.addActionListener(new ConceptEditorDialog_okButton_actionAdapter(this));
    this.getContentPane().add(conceptEditorPanel, BorderLayout.CENTER);
    this.getContentPane().add(jPanel1,  BorderLayout.SOUTH);
    jPanel1.add(okButton, null);
    jPanel1.add(cancelButton, null);
    //Center the window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
  }

  public void showSelf(String conceptId) {
    conceptEditorPanel.setConceptId(conceptId);
    setVisible(true);
  }

  public void showNewInstanceOf(String parentId) {
    conceptEditorPanel.clearConceptIdField();
    conceptEditorPanel.newConcept();
    conceptEditorPanel.addInstanceOf(parentId);
    this.setTitle("Concept Editor: New InstanceOf "+parentId);
    setVisible(true);
  }

  void putConcept(Concept c) {
    try {
      environment.putConcept(c);
    } catch (DatastoreException e) {
      e.printStackTrace();
    }
  }

  public void showNewSubOf(String parentId) {
    conceptEditorPanel.clearConceptIdField();
    conceptEditorPanel.newConcept();
    conceptEditorPanel.addSubOf(parentId);
    this.setTitle("Concept Editor: New SubOf "+parentId);
    setVisible(true);
  }

  void okButton_actionPerformed(ActionEvent e) {
  	System.out.println("ConceptEditor OK: "+conceptEditorPanel.getIsDirty());
  	if (conceptEditorPanel.getIsDirty()) {
	    Concept con = conceptEditorPanel.getConcept();
	    if (con != null) {
              putConcept(con);
                updateSubsInstances(con);
	     } else
	      System.out.println("ConceptEditorDialog closing on null Concept");
  	}
    setVisible(false);
  }

  /**
   * Make sure all supers know about this puppy
   * @param con
   */
  void updateSubsInstances(Concept con) {
    java.util.List l = con.getProperty("subOf");
    Concept c;
    String id = con.getId();
    String n;
    int len = 0;
    if (l != null) {
      len = l.size();
      for (int i=0;i<len;i++) {
        n = (String)l.get(i);
        c = environment.getConcept(n);
        if (c != null) {
          c.addProperty("hasSubs", id);
          putConcept(c);
        }
        //technically, if c == null, sumpin's wrong
      }
    }
    l = con.getProperty("instanceOf");
    if (l != null) {
      len = l.size();
      for (int i=0;i<len;i++) {
        n = (String)l.get(i);
        c = environment.getConcept(n);
        if (c != null) {
          c.addProperty("hasInstances", id);
          putConcept(c);
        }
        //technically, if c == null, sumpin's wrong
      }
    }
  }
  void cancelButton_actionPerformed(ActionEvent e) {
    setVisible(false);
  }


}

class ConceptEditorDialog_okButton_actionAdapter implements java.awt.event.ActionListener {
  ConceptEditorDialog adaptee;

  ConceptEditorDialog_okButton_actionAdapter(ConceptEditorDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.okButton_actionPerformed(e);
  }
}

class ConceptEditorDialog_cancelButton_actionAdapter implements java.awt.event.ActionListener {
  ConceptEditorDialog adaptee;

  ConceptEditorDialog_cancelButton_actionAdapter(ConceptEditorDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.cancelButton_actionPerformed(e);
  }
}
