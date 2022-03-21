/*
 *  Copyright (C) 2005  Jack Park,
 * 	mail : jackpark@gmail.com
 *
 *  Apache 2 License
 */
package org.nex.tinytsc;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

import org.nex.tinytsc.engine.Environment;
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

public class ConceptEditor extends JPanel {
  Concept myConcept;
  boolean isDirty = false;
  Environment environment;
  TreePanel treePanel;
  SlotEditorDialog slotEditor = new SlotEditorDialog();
  GridLayout gridLayout1 = new GridLayout();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  GridLayout gridLayout2 = new GridLayout();
  JPanel jPanel3 = new JPanel();
  JPanel jPanel4 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel5 = new JPanel();
  FlowLayout flowLayout1 = new FlowLayout();
  JLabel jLabel1 = new JLabel();
  BorderLayout borderLayout2 = new BorderLayout();
  JPanel jPanel6 = new JPanel();
  FlowLayout flowLayout2 = new FlowLayout();
  JTextField idField = new JTextField();
  JPanel jPanel7 = new JPanel();
  BorderLayout borderLayout3 = new BorderLayout();
  Border border1;
  TitledBorder titledBorder1;
  JPanel jPanel8 = new JPanel();
  BorderLayout borderLayout4 = new BorderLayout();
  Border border2;
  TitledBorder titledBorder2;
  JPanel jPanel9 = new JPanel();
  FlowLayout flowLayout3 = new FlowLayout();
  JButton delInstanceOfButton = new JButton();
  JButton newInstanceOfButton = new JButton();
  JScrollPane jScrollPane1 = new JScrollPane();
  JList instanceOfList = new JList();
  JPanel jPanel10 = new JPanel();
  JPanel jPanel12 = new JPanel();
  FlowLayout flowLayout4 = new FlowLayout();
  JButton delSubOfButton = new JButton();
  JButton newSubOfButton = new JButton();
  JScrollPane jScrollPane2 = new JScrollPane();
  JList subOfList = new JList();
  BorderLayout borderLayout5 = new BorderLayout();
  JPanel jPanel11 = new JPanel();
  Border border3;
  TitledBorder titledBorder3;
  FlowLayout flowLayout5 = new FlowLayout();
  JButton editSlotButton = new JButton();
  JButton deleteSlotButton = new JButton();
  JButton newSlotButton = new JButton();
  JScrollPane jScrollPane3 = new JScrollPane();
  JList slotList = new JList();
  DefaultListModel slotModel = new DefaultListModel();
  DefaultListModel subOfModel = new DefaultListModel();
  DefaultListModel instanceOfModel = new DefaultListModel();
  GridLayout gridLayout3 = new GridLayout();
  JPanel jPanel13 = new JPanel();
  GridLayout gridLayout4 = new GridLayout();
  JPanel rulePanel = new JPanel();
  BorderLayout borderLayout6 = new BorderLayout();
  JPanel epPanel = new JPanel();
  BorderLayout borderLayout7 = new BorderLayout();
  Border border4;
  TitledBorder titledBorder4;
  Border border5;
  TitledBorder titledBorder5;
  JScrollPane jScrollPane4 = new JScrollPane();
  JScrollPane jScrollPane5 = new JScrollPane();
  JList ruleList = new JList();
  DefaultListModel ruleModel = new DefaultListModel();
  JList episodeList = new JList();
  DefaultListModel episodeModel = new DefaultListModel();

  public ConceptEditor() {
    try {
      jbInit();
      slotList.setModel(slotModel);
      subOfList.setModel(subOfModel);
      instanceOfList.setModel(instanceOfModel);
      ruleList.setModel(ruleModel);
      episodeList.setModel(episodeModel);
      this.slotEditor.setHost(this);
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void setTreePanel(TreePanel tree) {
    treePanel = tree;
  }

  public void setListener(TreeListener e) {
    slotEditor.setListener(e);
    environment = e.getEnvironment();
  }

  public void setRootConcept(Concept c) {
    slotEditor.setRootConcept(c);
  }

  public void setConceptId(String id) {
    this.idField.setText(id);
    this.idField.setEditable(false);
    fillConcept(id);
  }

  public void newConcept() {
    myConcept = new Concept();
  }

  public Concept getConcept() {
    myConcept.setId(this.idField.getText());
    if (myConcept.getId().equals("")) {
        JOptionPane.showMessageDialog(null,"Concept missing Identity");
        return null;
    }
    return myConcept;
  }

  public boolean getIsDirty() {
    return isDirty;
  }
  /**
   * Allow for creating instance and sub identity
   */
  public void clearConceptIdField() {
    this.idField.setText("");
    this.idField.setEditable(true);
    isDirty = false;
  }
  private void jbInit() throws Exception {
    border1 = BorderFactory.createLineBorder(SystemColor.controlText,1);
    titledBorder1 = new TitledBorder(border1,"SubOf");
    border2 = BorderFactory.createLineBorder(SystemColor.controlText,1);
    titledBorder2 = new TitledBorder(border2,"InstanceOf");
    border3 = BorderFactory.createLineBorder(SystemColor.controlText,1);
    titledBorder3 = new TitledBorder(border3,"Slots");
    border4 = BorderFactory.createLineBorder(SystemColor.controlText,1);
    titledBorder4 = new TitledBorder(border4,"Episodes");
    border5 = BorderFactory.createLineBorder(SystemColor.controlText,1);
    titledBorder5 = new TitledBorder(border5,"Rules");
    gridLayout1.setColumns(1);
    gridLayout1.setHgap(2);
    gridLayout1.setRows(2);
    gridLayout1.setVgap(2);
    this.setLayout(gridLayout1);
    jPanel1.setLayout(gridLayout2);
    gridLayout2.setColumns(1);
    gridLayout2.setHgap(2);
    jPanel3.setLayout(borderLayout1);
    jPanel5.setLayout(flowLayout1);
    flowLayout1.setAlignment(FlowLayout.RIGHT);
    jLabel1.setPreferredSize(new Dimension(83, 21));
    jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel1.setHorizontalTextPosition(SwingConstants.RIGHT);
    jLabel1.setText("Concept Identity: ");
    jPanel4.setLayout(borderLayout2);
    jPanel6.setLayout(flowLayout2);
    flowLayout2.setAlignment(FlowLayout.LEFT);
    idField.setPreferredSize(new Dimension(200, 21));
    idField.setText("");
    idField.addKeyListener(new ConceptEditor_idField_keyAdapter(this));
    jPanel7.setLayout(borderLayout3);
    jPanel7.setBorder(titledBorder1);
    jPanel8.setLayout(borderLayout4);
    jPanel8.setBorder(titledBorder2);
    jPanel9.setLayout(flowLayout3);
    flowLayout3.setAlignment(FlowLayout.LEFT);
    flowLayout3.setHgap(10);
    delInstanceOfButton.setEnabled(false);
    delInstanceOfButton.setBorder(BorderFactory.createRaisedBevelBorder());
    delInstanceOfButton.setText("Delete");
    delInstanceOfButton.addActionListener(new ConceptEditor_delInstanceOfButton_actionAdapter(this));
    newInstanceOfButton.setBorder(BorderFactory.createRaisedBevelBorder());
    newInstanceOfButton.setToolTipText("New InstanceOf");
    newInstanceOfButton.setText("  New  ");
    newInstanceOfButton.addActionListener(new ConceptEditor_newInstanceOfButton_actionAdapter(this));
    instanceOfList.addMouseListener(new ConceptEditor_instanceOfList_mouseAdapter(this));
    jPanel10.setLayout(flowLayout4);
    flowLayout4.setAlignment(FlowLayout.LEFT);
    flowLayout4.setHgap(10);
    delSubOfButton.setEnabled(false);
    delSubOfButton.setBorder(BorderFactory.createRaisedBevelBorder());
    delSubOfButton.setToolTipText("");
    delSubOfButton.setText("Delete");
    delSubOfButton.addActionListener(new ConceptEditor_delSubOfButton_actionAdapter(this));
    newSubOfButton.setBorder(BorderFactory.createRaisedBevelBorder());
    newSubOfButton.setToolTipText("New SubOf");
    newSubOfButton.setText("  New  ");
    newSubOfButton.addActionListener(new ConceptEditor_newSubOfButton_actionAdapter(this));
    subOfList.addMouseListener(new ConceptEditor_subOfList_mouseAdapter(this));
    jPanel2.setLayout(borderLayout5);
    jPanel2.setBorder(titledBorder3);
    jPanel11.setLayout(flowLayout5);
    flowLayout5.setAlignment(FlowLayout.LEFT);
    flowLayout5.setHgap(25);
    editSlotButton.setEnabled(false);
    editSlotButton.setBorder(BorderFactory.createRaisedBevelBorder());
    editSlotButton.setToolTipText("Edit selected Slot");
    editSlotButton.setText(" Edit ");
    editSlotButton.addActionListener(new ConceptEditor_editSlotButton_actionAdapter(this));
    deleteSlotButton.setEnabled(false);
    deleteSlotButton.setBorder(BorderFactory.createRaisedBevelBorder());
    deleteSlotButton.setToolTipText("Delete selected Slot");
    deleteSlotButton.setText("Delete");
    deleteSlotButton.addActionListener(new ConceptEditor_deleteSlotButton_actionAdapter(this));
    newSlotButton.setBorder(BorderFactory.createRaisedBevelBorder());
    newSlotButton.setToolTipText("New Slot");
    newSlotButton.setText("  New  ");
    newSlotButton.addActionListener(new ConceptEditor_newSlotButton_actionAdapter(this));
    slotList.addMouseListener(new ConceptEditor_slotList_mouseAdapter(this));
    jPanel12.setLayout(gridLayout3);
    gridLayout3.setColumns(2);
    jPanel13.setLayout(gridLayout4);
    gridLayout4.setColumns(1);
    gridLayout4.setRows(2);
    rulePanel.setLayout(borderLayout6);
    epPanel.setLayout(borderLayout7);
    epPanel.setBorder(titledBorder4);
    rulePanel.setBorder(titledBorder5);
    ruleList.addMouseListener(new ConceptEditor_ruleList_mouseAdapter(this));
    episodeList.addMouseListener(new ConceptEditor_episodeList_mouseAdapter(this));
    jPanel11.add(newSlotButton, null);
    this.add(jPanel1, null);
    this.add(jPanel12, null);
    jPanel12.add(jPanel2,null);
    jPanel1.add(jPanel3, null);
    jPanel3.add(jPanel5, BorderLayout.NORTH);
    jPanel5.add(jLabel1, null);
    jPanel1.add(jPanel4, null);
    jPanel4.add(jPanel6, BorderLayout.NORTH);
    jPanel6.add(idField, null);
    jPanel4.add(jPanel7,  BorderLayout.CENTER);
    jPanel3.add(jPanel8, BorderLayout.CENTER);
    jPanel8.add(jPanel9, BorderLayout.NORTH);
    jPanel9.add(newInstanceOfButton, null);
    jPanel9.add(delInstanceOfButton, null);
    jPanel8.add(jScrollPane1, BorderLayout.CENTER);
    jScrollPane1.getViewport().add(instanceOfList, null);
    jPanel7.add(jPanel10, BorderLayout.NORTH);
    jPanel10.add(newSubOfButton, null);
    jPanel10.add(delSubOfButton, null);
    jPanel7.add(jScrollPane2, BorderLayout.CENTER);
    jScrollPane2.getViewport().add(subOfList, null);
    jPanel2.add(jPanel11, BorderLayout.NORTH);
    jPanel11.add(deleteSlotButton, null);
    jPanel11.add(editSlotButton, null);
    jPanel2.add(jScrollPane3, BorderLayout.CENTER);
    jPanel12.add(jPanel13, null);
    jPanel13.add(rulePanel, null);
    jScrollPane3.getViewport().add(slotList, null);
    jPanel13.add(epPanel, null);
    rulePanel.add(jScrollPane4,  BorderLayout.CENTER);
    jScrollPane4.getViewport().add(ruleList, null);
    epPanel.add(jScrollPane5, BorderLayout.CENTER);
    jScrollPane5.getViewport().add(episodeList, null);
  }
  /**
   * Fetch <code>Concept</code> from <code>Environment</code>
   * and populate this dialog
   * @param conceptId
   */
  void fillConcept(String conceptId) {
    myConcept = environment.getConcept(conceptId);
    if (myConcept != null) {
      isDirty = false;
      System.out.println("Filling Concept Editor: " + conceptId);
      refreshConcept();
    }
  }

  void refreshConcept() {
    subOfModel.clear();
    instanceOfModel.clear();
    ruleModel.clear();
    slotModel.clear();
    episodeModel.clear();
    java.util.List list = myConcept.getEpisodes();
    int len = 0;
    if (list != null) {
      len = list.size();
      for (int i=0;i<len;i++)
        addEpisode((String)list.get(i));
    }
    list = myConcept.getRules();
    if (list != null) {
     len = list.size();
     for (int i=0;i<len;i++)
       addRule((String)list.get(i));
   }
   Iterator itr = myConcept.listPropertyNames();
   String n;
   while(itr.hasNext()) {
     n = (String)itr.next();
     System.out.println("Slot "+n);
     if (n.equals("subOf"))
       fillSubInstOf(true,myConcept);
     else if (n.equals("instanceOf"))
       fillSubInstOf(false,myConcept);
     else
       addSlot(n, null, false);
   }
}

  void fillSubInstOf(boolean isSub, Concept c) {
    String slotType = isSub ? "subOf" : "instanceOf";
    java.util.List l = c.getProperty(slotType);
    System.out.println("FillSubInstOf "+isSub+" "+slotType);
    if (l != null) {
      int len = l.size();
      for (int i=0;i<len;i++) {
        if (isSub)
          addSubOf( (String) l.get(i), false);
        else
          addInstanceOf( (String) l.get(i), false);
      }
    }
  }

  /**
   * Callback from <code>SlotEditorDialog</code>
   * @param slotType
   * @param values
   */
  public void acceptSlot(String slotType, Enumeration values) {
    System.out.println("Accepting Slot "+slotType+" "+values);
    if (myConcept == null)
      throw new RuntimeException("Null Concept in ConceptEditor");
    if (values != null) {
      while(values.hasMoreElements())
        myConcept.addProperty(slotType,values.nextElement());
    }
    isDirty = true;
    refreshConcept();
  }

  public void newInstanceOf(String id) {
    myConcept.addProperty("instanceOf",id);
    refreshConcept();
  }

  public void newSubOf(String id) {
    myConcept.addProperty("subOf",id);
    refreshConcept();
  }

  void addInstanceOf(String id, boolean isNew) {
    instanceOfModel.addElement(id);
    if(isNew)
    	myConcept.addProperty("instanceOf",id);
  }

  void addSubOf(String id, boolean isNew) {
    subOfModel.addElement(id);
    if (isNew)
    	myConcept.addProperty("subOf",id);
  }

  void addSlot(String slot, java.util.List values, boolean isNew) {
    slotModel.addElement(slot);
    if (isNew)
    	myConcept.putProperty(slot,values);
  }
  void addRule(String ruleId) {
    ruleModel.addElement(ruleId);
  }
  void addEpisode(String episodeId) {
    episodeModel.addElement(episodeId);
  }

  void newInstanceOfButton_actionPerformed(ActionEvent e) {
    String id = treePanel.getSelectedNodeId();
    if (id != null) {
      addInstanceOf(id, true);
      isDirty = true;
    }
  }

  void instanceOfList_mouseClicked(MouseEvent e) {
    //CANNOT display a Concept inside a Concept
  }
  void delInstanceOfButton_actionPerformed(ActionEvent e) {
  	//TODO
  }

  void newSubOfButton_actionPerformed(ActionEvent e) {
    String id = treePanel.getSelectedNodeId();
    if (id != null) {
      addSubOf(id, true);
      isDirty = true;
    }
  }

  void subOfList_mouseClicked(MouseEvent e) {
    //Cannot display a Concept inside a Concept
  }
  void delSubOfButton_actionPerformed(ActionEvent e) {
    //TODO
  }


  void slotList_mouseClicked(MouseEvent e) {
    String s = (String)slotList.getSelectedValue();
    if (e.getClickCount() > 1) {
      slotEditor.showSelf(s, myConcept.getProperty(s));
    }

  }

  void newSlotButton_actionPerformed(ActionEvent e) {
    slotEditor.showSelf();
  }

  void deleteSlotButton_actionPerformed(ActionEvent e) {
    //TODO
  }

  void editSlotButton_actionPerformed(ActionEvent e) {
    String slo = (String)slotList.getSelectedValue();
    if (slo != null) {
    	slotEditor.showSelf(slo, myConcept.getProperty(slo));
    }
  }

  void ruleList_mouseClicked(MouseEvent e) {
    //TODO
  }

  void episodeList_mouseClicked(MouseEvent e) {
    //TODO
  }

  void idField_keyTyped(KeyEvent e) {
    isDirty = true;
  }











}

class ConceptEditor_delInstanceOfButton_actionAdapter implements java.awt.event.ActionListener {
  ConceptEditor adaptee;

  ConceptEditor_delInstanceOfButton_actionAdapter(ConceptEditor adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.delInstanceOfButton_actionPerformed(e);
  }
}

class ConceptEditor_newInstanceOfButton_actionAdapter implements java.awt.event.ActionListener {
  ConceptEditor adaptee;

  ConceptEditor_newInstanceOfButton_actionAdapter(ConceptEditor adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.newInstanceOfButton_actionPerformed(e);
  }
}

class ConceptEditor_instanceOfList_mouseAdapter extends java.awt.event.MouseAdapter {
  ConceptEditor adaptee;

  ConceptEditor_instanceOfList_mouseAdapter(ConceptEditor adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.instanceOfList_mouseClicked(e);
  }
}

class ConceptEditor_delSubOfButton_actionAdapter implements java.awt.event.ActionListener {
  ConceptEditor adaptee;

  ConceptEditor_delSubOfButton_actionAdapter(ConceptEditor adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.delSubOfButton_actionPerformed(e);
  }
}

class ConceptEditor_newSubOfButton_actionAdapter implements java.awt.event.ActionListener {
  ConceptEditor adaptee;

  ConceptEditor_newSubOfButton_actionAdapter(ConceptEditor adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.newSubOfButton_actionPerformed(e);
  }
}

class ConceptEditor_subOfList_mouseAdapter extends java.awt.event.MouseAdapter {
  ConceptEditor adaptee;

  ConceptEditor_subOfList_mouseAdapter(ConceptEditor adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.subOfList_mouseClicked(e);
  }
}

class ConceptEditor_slotList_mouseAdapter extends java.awt.event.MouseAdapter {
  ConceptEditor adaptee;

  ConceptEditor_slotList_mouseAdapter(ConceptEditor adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.slotList_mouseClicked(e);
  }
}

class ConceptEditor_newSlotButton_actionAdapter implements java.awt.event.ActionListener {
  ConceptEditor adaptee;

  ConceptEditor_newSlotButton_actionAdapter(ConceptEditor adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.newSlotButton_actionPerformed(e);
  }
}

class ConceptEditor_deleteSlotButton_actionAdapter implements java.awt.event.ActionListener {
  ConceptEditor adaptee;

  ConceptEditor_deleteSlotButton_actionAdapter(ConceptEditor adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.deleteSlotButton_actionPerformed(e);
  }
}

class ConceptEditor_editSlotButton_actionAdapter implements java.awt.event.ActionListener {
  ConceptEditor adaptee;

  ConceptEditor_editSlotButton_actionAdapter(ConceptEditor adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.editSlotButton_actionPerformed(e);
  }
}

class ConceptEditor_ruleList_mouseAdapter extends java.awt.event.MouseAdapter {
  ConceptEditor adaptee;

  ConceptEditor_ruleList_mouseAdapter(ConceptEditor adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.ruleList_mouseClicked(e);
  }
}

class ConceptEditor_episodeList_mouseAdapter extends java.awt.event.MouseAdapter {
  ConceptEditor adaptee;

  ConceptEditor_episodeList_mouseAdapter(ConceptEditor adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.episodeList_mouseClicked(e);
  }
}

class ConceptEditor_idField_keyAdapter extends java.awt.event.KeyAdapter {
  ConceptEditor adaptee;

  ConceptEditor_idField_keyAdapter(ConceptEditor adaptee) {
    this.adaptee = adaptee;
  }
  public void keyTyped(KeyEvent e) {
    adaptee.idField_keyTyped(e);
  }
}