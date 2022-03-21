/*
 *  Copyright (C) 2005  Jack Park,
 * 	mail : jackpark@gmail.com
 *
 *  Apache 2 LicenseA
 */
package org.nex.tinytsc;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import org.nex.tinytsc.engine.Environment;
import org.nex.tinytsc.engine.Task;
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

public class TaskEditorDialog extends JDialog {
  private Environment environment;
  private String isInstanceOf;
  BorderLayout borderLayout1 = new BorderLayout();
  TaskEditorPanel taskEditorPanel1 = new TaskEditorPanel();
  JPanel jPanel1 = new JPanel();
  FlowLayout flowLayout1 = new FlowLayout();
  JButton cancelButton = new JButton();
  JButton okButton = new JButton();

  public TaskEditorDialog() throws HeadlessException {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  public void setListener(TreeListener e) {
    taskEditorPanel1.setListener(e);
    environment = e.getEnvironment();
  }

  public void setRootConcept(Concept c) {
    taskEditorPanel1.setRootConcept(c);
  }

  private void jbInit() throws Exception {
    this.setSize(600,600);
    okButton.setText("OK");
    okButton.addActionListener(new TaskEditorDialog_okButton_actionAdapter(this));
    cancelButton.setText("Cancel");
    cancelButton.addActionListener(new TaskEditorDialog_cancelButton_actionAdapter(this));
    flowLayout1.setHgap(20);
    jPanel1.setLayout(flowLayout1);
    this.setTitle("Task Editor");
    this.getContentPane().setLayout(borderLayout1);
    this.getContentPane().add(taskEditorPanel1, BorderLayout.CENTER);
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


  public void showNewInstance(String parentId) {
    taskEditorPanel1.newTask(parentId);
    isInstanceOf = parentId;
    setVisible(true);
  }

  public void showSelf(String taskId) {
    taskEditorPanel1.setTaskId(taskId);
    setVisible(true);
  }
  void cancelButton_actionPerformed(ActionEvent e) {
    setVisible(false);
  }

  void okButton_actionPerformed(ActionEvent e) {
    if (taskEditorPanel1.getIsDirty()) {
      Task t = taskEditorPanel1.getTask();
      putTask(t);
      Concept c = environment.getConcept(isInstanceOf);
      if (c == null)
      	throw new RuntimeException("Task cannot get parent: "+isInstanceOf);
      c.addProperty("hasInstances", t.getId());
      putConcept(c);
    }
    setVisible(false);
  }
  void putTask(Task t) {
    try {
      environment.putTask(t);
    } catch (DatastoreException e) {
      e.printStackTrace();
    }
  }
  void putConcept(Concept c) {
    try {
      environment.putConcept(c);
    } catch (DatastoreException e) {
      e.printStackTrace();
    }
  }
}

class TaskEditorDialog_cancelButton_actionAdapter implements java.awt.event.ActionListener {
  TaskEditorDialog adaptee;

  TaskEditorDialog_cancelButton_actionAdapter(TaskEditorDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.cancelButton_actionPerformed(e);
  }
}

class TaskEditorDialog_okButton_actionAdapter implements java.awt.event.ActionListener {
  TaskEditorDialog adaptee;

  TaskEditorDialog_okButton_actionAdapter(TaskEditorDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.okButton_actionPerformed(e);
  }
}
