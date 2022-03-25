/*
 *  Copyright (C) 2005  Jack Park,
 * 	mail : jackpark@gmail.com
 *
 *  Apache 2 License
 */
package org.nex.tinytsc;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

import org.nex.tinytsc.engine.Environment;
import org.nex.tinytsc.engine.Task;
import org.nex.tinytsc.api.IConstants;
import org.nex.tinytsc.api.Identifiable;
import org.nex.tinytsc.engine.Concept;
/**
 * <p>Title: TinyTSC</p>
 * <p>Description: Small "The Scholar's Companion(r)"</p>
 * <p>Copyright: Copyright (c) 2005, Jack Park</p>
 * <p>Company: NexistGroup</p>
 * @author Jack Park
 * @version 1.0
 */

public class TaskEditorPanel extends JPanel {
  private Environment environment;
  private Task myTask;
  private boolean isDirty = false;
  BorderLayout borderLayout1 = new BorderLayout();
  JSplitPane jSplitPane1 = new JSplitPane();
  TreePanel treePanel;
  JPanel jPanel1 = new JPanel();
  Border border1;
  TitledBorder titledBorder1;
  BorderLayout borderLayout2 = new BorderLayout();
  JPanel jPanel2 = new JPanel();
  FlowLayout flowLayout1 = new FlowLayout();
  JComboBox taskComboBox = new JComboBox();
  DefaultComboBoxModel taskModel = new DefaultComboBoxModel();
  JLabel jLabel1 = new JLabel();
  JPanel jPanel3 = new JPanel();
  BorderLayout borderLayout3 = new BorderLayout();
  JPanel jPanel4 = new JPanel();
  GridLayout gridLayout1 = new GridLayout();
  JLabel jLabel2 = new JLabel();
  JTextField taskField = new JTextField();
  JPanel jPanel5 = new JPanel();
  BorderLayout borderLayout4 = new BorderLayout();
  JPanel jPanel6 = new JPanel();
  GridLayout gridLayout2 = new GridLayout();
  JLabel jLabel3 = new JLabel();
  JTextField conceptField = new JTextField();
  JLabel jLabel4 = new JLabel();
  JTextField idField = new JTextField();
  JButton selectButton = new JButton();

  public TaskEditorPanel(Environment env) {
	  environment = env;
    try {
    	treePanel = new TreePanel(environment);
      jbInit();
      initializeTasks();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void setListener(TreeListener e) {
    treePanel.setListener(e);
  }

  public void setRootConcept(Concept c) {
	  try {
    treePanel.setRootConcept(c);
	  } catch (Exception e) {
		  throw new RuntimeException(e);
	  }

  }

  private void jbInit() throws Exception {
    border1 = BorderFactory.createLineBorder(SystemColor.controlText,1);
    titledBorder1 = new TitledBorder(border1,"Concepts");
    this.setLayout(borderLayout1);
    treePanel.setBorder(titledBorder1);
    jPanel1.setLayout(borderLayout2);
    jPanel2.setLayout(flowLayout1);
    jLabel1.setText("Tasks");
    taskComboBox.setPreferredSize(new Dimension(120, 21));
    taskComboBox.addActionListener(new TaskEditorPanel_taskComboBox_actionAdapter(this));
    jPanel3.setLayout(borderLayout3);
    jPanel4.setLayout(gridLayout1);
    gridLayout1.setColumns(2);
    gridLayout1.setHgap(5);
    gridLayout1.setVgap(5);
    jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel2.setText("Selected Task");
    taskField.setEditable(false);
    taskField.setText("");
    jPanel5.setLayout(borderLayout4);
    jPanel6.setLayout(gridLayout2);
    gridLayout2.setColumns(2);
    gridLayout2.setHgap(5);
    gridLayout2.setVgap(5);
    jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel3.setText("Selected Concept");
    conceptField.setEditable(false);
    conceptField.setText("");
    flowLayout1.setAlignment(FlowLayout.LEFT);
    jLabel4.setText("ID: ");
    idField.setPreferredSize(new Dimension(200, 21));
    idField.setText("");
    selectButton.setFont(new java.awt.Font("Dialog", 1, 12));
    selectButton.setToolTipText("Select Model");
    selectButton.setText("S");
    selectButton.addActionListener(new TaskEditorPanel_selectButton_actionAdapter(this));
    this.add(jSplitPane1, BorderLayout.CENTER);
    jSplitPane1.add(treePanel, JSplitPane.LEFT);
    jSplitPane1.add(jPanel1, JSplitPane.RIGHT);
    jPanel1.add(jPanel2, BorderLayout.NORTH);
    jPanel2.add(jLabel1, null);
    jPanel2.add(taskComboBox, null);
    jPanel2.add(jLabel4, null);
    jPanel2.add(idField, null);
    jPanel1.add(jPanel3, BorderLayout.CENTER);
    jPanel3.add(jPanel4, BorderLayout.NORTH);
    jPanel4.add(jLabel2, null);
    jPanel4.add(taskField, null);
    jPanel3.add(jPanel5, BorderLayout.CENTER);
    JPanel panelX = new JPanel(new BorderLayout());
    panelX.add(jPanel6, BorderLayout.CENTER);
    jPanel5.add(panelX,  BorderLayout.NORTH);
    jPanel6.add(jLabel3, null);
    jPanel6.add(conceptField, null);
    panelX.add(selectButton,  BorderLayout.EAST);
  }

  void initializeTasks() {
    taskModel.addElement(IConstants.FILLIN_NEXT_EPISODE);
    taskModel.addElement(IConstants.PUBLISH_EPISODE);
    this.taskComboBox.setModel(taskModel);
  }

  public void setTaskId(String id) {
    idField.setEditable(false);
    idField.setText(id);
    myTask = environment.getTask(id);
    String tt = myTask.getTaskType();
    if (tt.equals(IConstants.FILLIN_NEXT_EPISODE))
        taskComboBox.setSelectedItem(IConstants.FILLIN_NEXT_EPISODE);
    else if (tt.equals(IConstants.PUBLISH_EPISODE))
      taskComboBox.setSelectedItem(IConstants.PUBLISH_EPISODE);
    this.taskField.setText(tt);
    this.conceptField.setText(myTask.getObject().getId());
  }

  public void newTask(String parentId) {
    idField.setEditable(true);
    idField.setText("");
    taskField.setText("");
    conceptField.setText("");
    myTask = new Task();
    myTask.setInstanceOf(parentId);
    isDirty = true;
  }

  public Task getTask() {
    myTask.setId(idField.getText());
    myTask.setTaskType(taskField.getText());
    Identifiable ix = environment.getIdentifiable(conceptField.getText());
    myTask.setObject(ix);
    return myTask;
  }

  public boolean getIsDirty() {
    return isDirty;
  }
  void taskComboBox_actionPerformed(ActionEvent e) {
    taskField.setText((String)taskComboBox.getSelectedItem());
    isDirty = true;
  }

  void selectButton_actionPerformed(ActionEvent e) {
    conceptField.setText(treePanel.getSelectedNodeId());
    isDirty = true;
  }




}

class TaskEditorPanel_taskComboBox_actionAdapter implements java.awt.event.ActionListener {
  TaskEditorPanel adaptee;

  TaskEditorPanel_taskComboBox_actionAdapter(TaskEditorPanel adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.taskComboBox_actionPerformed(e);
  }
}

class TaskEditorPanel_selectButton_actionAdapter implements java.awt.event.ActionListener {
  TaskEditorPanel adaptee;

  TaskEditorPanel_selectButton_actionAdapter(TaskEditorPanel adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.selectButton_actionPerformed(e);
  }
}