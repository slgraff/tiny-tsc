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

/**
 * <p>Title: TinyTSC</p>
 * <p>Description: Small "The Scholar's Companion(r)"</p>
 * <p>Copyright: Copyright (c) 2005, Jack Park</p>
 * <p>Company: NexistGroup</p>
 * @author Jack Park
 * @version 1.0
 */

public class TaskSelectorDialog extends JDialog {
  MainFrame host;
  BorderLayout borderLayout1 = new BorderLayout();
  JScrollPane jScrollPane1 = new JScrollPane();
  JPanel jPanel1 = new JPanel();
  FlowLayout flowLayout1 = new FlowLayout();
  JButton selectButton = new JButton();
  JButton cancelButton = new JButton();
  JList taskList = new JList();
  DefaultListModel model = new DefaultListModel();

  public TaskSelectorDialog() throws HeadlessException {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void setHost(MainFrame h) {
    host = h;
  }
  private void jbInit() throws Exception {
    this.setTitle("Task Selector");
    this.getContentPane().setLayout(borderLayout1);
    this.setSize(400,400);
    jPanel1.setLayout(flowLayout1);
    selectButton.setEnabled(false);
    selectButton.setText("Select");
    selectButton.addActionListener(new TaskSelectorDialog_selectButton_actionAdapter(this));
    cancelButton.setText("Cancel");
    cancelButton.addActionListener(new TaskSelectorDialog_cancelButton_actionAdapter(this));
    taskList.addMouseListener(new TaskSelectorDialog_taskList_mouseAdapter(this));
    this.getContentPane().add(jScrollPane1, BorderLayout.CENTER);
    this.getContentPane().add(jPanel1,  BorderLayout.SOUTH);
    jPanel1.add(selectButton, null);
    jPanel1.add(cancelButton, null);
    jScrollPane1.getViewport().add(taskList, null);
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

  public void showSelf(java.util.List tasks) {
    this.model.clear();
    int len = tasks.size();
    for (int i=0;i<len;i++)
      model.addElement(tasks.get(i));
    taskList.setModel(model);
    this.selectButton.setEnabled(false);
    this.setVisible(true);
  }
  void cancelButton_actionPerformed(ActionEvent e) {
    this.setVisible(false);
  }

  void selectButton_actionPerformed(ActionEvent e) {
    this.setVisible(false);
    host.acceptTaskId((String)taskList.getSelectedValue());
  }

  void taskList_mouseClicked(MouseEvent e) {
    this.selectButton.setEnabled(true);
  }



}

class TaskSelectorDialog_cancelButton_actionAdapter implements java.awt.event.ActionListener {
  TaskSelectorDialog adaptee;

  TaskSelectorDialog_cancelButton_actionAdapter(TaskSelectorDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.cancelButton_actionPerformed(e);
  }
}

class TaskSelectorDialog_selectButton_actionAdapter implements java.awt.event.ActionListener {
  TaskSelectorDialog adaptee;

  TaskSelectorDialog_selectButton_actionAdapter(TaskSelectorDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.selectButton_actionPerformed(e);
  }
}

class TaskSelectorDialog_taskList_mouseAdapter extends java.awt.event.MouseAdapter {
  TaskSelectorDialog adaptee;

  TaskSelectorDialog_taskList_mouseAdapter(TaskSelectorDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.taskList_mouseClicked(e);
  }
}